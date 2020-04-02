/*
Create a singleton connection to mongodb
Replace the old factory with lambda handlers from spark
DAO’s api should not change, but now they will interface with mongodb
Handlers can now instantiate Processors directly
All endpoints are exact same as last time
Port your processors and dto’s over to the new project and add them into appropriate spark handlers
All input/output should be exactly the same
 */
import static spark.Spark.*;
import java.util.*;

import DAO.PaymentDAO;
import Processor.PaymentMethods;
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

    // addItem endpoint
    get("/addPaymentMethod", (req, res) -> {
      PaymentMethods PayProcessor = new PaymentMethods();
      return gson.toJson(PayProcessor.addPay(Main.processRoute(req, res)));
    });

    // listItems endpoint
    get("/getAllPaymentMethods", (req, res) -> {
      PaymentMethods PayProcessor = new PaymentMethods();
      return gson.toJson(PayProcessor.getPay(Main.processRoute(req, res))) + "</br>" + PaymentDAO.getPaymentList();
    });


  }
}