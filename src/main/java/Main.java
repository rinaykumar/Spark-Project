import static spark.Spark.*;
import java.util.*;

import DAO.PaymentDAO;
import DAO.TransactionDAO;
import Processor.PaymentMethods;
import Processor.TransactionProcessor;
import com.google.gson.*;
import spark.Request;
import spark.Response;


public class Main {

    public static Map<String, String> processRoute(Request req, Response res) {
        // Variables
        Set<String> params = req.queryParams();
        Map<String, String> args = new HashMap<String, String>();

        // Print out params and put into map
        for (String param : params) {
            System.out.println(param + " : " + req.queryParamsValues(param)[0]);
            args.put(param, req.queryParamsValues(param)[0]);
        }

        return args;
    }

    public static void main(String[] args) {
        // Variables
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        // Listen at port 1234
        port(1234);

        // root endpoint
        get("/", (req, res) -> "<h2>Welcome to CSC 413 HW2</h2>");

        // listTransactions endpoint
        get("/listTransactions", (req, res) -> {
            TransactionProcessor transact = new TransactionProcessor();
            return gson.toJson(transact.listTransactions(Main.processRoute(req, res))) + "</br>" + TransactionDAO.transactionList;
        });

        // createTransaction endpoint
        get("/createTransaction", (req, res) -> {
            TransactionProcessor transact = new TransactionProcessor();
            return gson.toJson(transact.createTransaction(Main.processRoute(req, res)));
        });
    }
}