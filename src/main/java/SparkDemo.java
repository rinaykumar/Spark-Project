import static spark.Spark.*;
import java.util.*;
import com.google.gson.*;
import spark.Request;
import spark.Response;

public class SparkDemo {

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
    get("/addItem", (req, res) -> {
      ItemsProcessor itemsProcessor = new ItemsProcessor();
      return gson.toJson(itemsProcessor.addItems(SparkDemo.processRoute(req, res)));
    });

    // listItems endpoint
    get("/listItems", (req, res) -> {
      ItemsProcessor itemsProcessor = new ItemsProcessor();
      return gson.toJson(itemsProcessor.listItems(SparkDemo.processRoute(req, res))) + "</br>" + ItemsDAO.getItemsList();
    });

    // TODO: remaining endpoints and accompanying port over
    // addPaymentMethod endpoint

    // getAllPaymentMethods endpoint

    // createTransaction endpoint

    // listTransactions endpoint

  }
}