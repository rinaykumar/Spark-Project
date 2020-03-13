import static spark.Spark.*;

import java.util.*;

import com.google.gson.*;
import spark.Request;
import spark.Response;


public class SparkDemo {

  public static Map<String, String> processRoute(Request req, Response res) {
    Set<String> params = req.queryParams();
    Map<String, String> args = new HashMap<String, String>();

    //System.out.println(req.pathInfo());

    for (String param : params) {
      // possible for query param to be an array
      System.out.println(param + " : " + req.queryParamsValues(param)[0]);
      args.put(param, req.queryParamsValues(param)[0]);
    }
    // do stuff with a mapped version http://javadoc.io/doc/com.sparkjava/spark-core/2.8.0
    // http://sparkjava.com/documentation#query-maps
    // print the id query value
    //System.out.println(req.queryMap().get("id").value());
    return args;
  }

  public static void main(String[] args) {
    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    String json = "";

    port(1234);

    // Welcome at root endpoint
    get("/", (req, res) -> "<h2>Welcome to CSC 413 HW2</h2>");

    // addItem endpoint
    get("/addItem", (req, res) -> {
      ItemsProcessor itemsProcessor = new ItemsProcessor();
      return gson.toJson(itemsProcessor.addItems(SparkDemo.processRoute(req, res)));
    });

    // listItems endpoint
    get("/listItems", (req, res) -> {
      ItemsProcessor itemsProcessor = new ItemsProcessor();
      return gson.toJson(itemsProcessor.listItems(SparkDemo.processRoute(req, res))) + " " + gson.toJson(ItemsDAO.getItemsList());
    });

    // Slightly more advanced routing
    path("/api", () -> {
      get("/users", (req, res) -> {
        return "This one has a block body";
      });
      get("/posts", SparkDemo::processRoute);
      get("/lambda", (req, res) -> SparkDemo.processRoute(req, res));
    });
  }
}
