/*
Create a singleton connection to mongodb
Replace the old factory with lambda handlers from spark
DAO’s api should not change, but now they will interface with mongodb
Handlers can now instantiate Processors directly
All endpoints are exact same as last time
Port your processors and dto’s over to the new project and add them into appropriate spark handlers
All input/output should be exactly the same
 */

import com.google.gson.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Main {

  public static void main(String[] args) throws IOException {
    ServerSocket ding;
    Socket dong = null;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();
    String json = "";

    try {
      ding = new ServerSocket(1299);
      System.out.println("Opened socket " + 1299);
      while (true) {
        // keeps listening for new clients, one at a time
        try {
          dong = ding.accept(); // waits for client here
        } catch (IOException e) {
          System.out.println("Error opening socket");
          System.exit(1);
        }

        InputStream stream = dong.getInputStream();
        BufferedReader in = new BufferedReader(new InputStreamReader(stream));
        try {
          // read the first line to get the request method, URI and HTTP version
          String line = in.readLine();
          System.out.println("----------REQUEST START---------");
          System.out.println(line);

          // Send to Factory
          Factory factory = new Factory();
          json = gson.toJson(factory.process(line));

          // read only headers
          line = in.readLine();
          while (line != null && line.trim().length() > 0) {
            int index = line.indexOf(": ");
            if (index > 0) {
              System.out.println(line);
            } else {
              break;
            }
            line = in.readLine();
          }
          System.out.println("----------REQUEST END---------\n\n");
        } catch (IOException e) {
          System.out.println("Error reading");
          System.exit(1);
        }

        BufferedOutputStream out = new BufferedOutputStream(dong.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);  // char output to the client

        // every response will always have the status-line, date, and server name
        writer.println("HTTP/1.1 200 OK");
        writer.println("Server: TEST");
        writer.println("Connection: close");
        writer.println("Content-type: text/html");
        writer.println("");

        // Body of our response
        if (json.equals("null")) {
          writer.println("<h2>Welcome to CSC 413 HW1</h2>");
        } else {
          writer.println("<p>" + json + "/<p>"); // to browser
          System.out.println(json); // to console

          // listItems
          if (json.contains("listItems")) {
            List<ItemsDTO> itemsList = ItemsDAO.getItemsList();
            for (ItemsDTO itemsDTO : itemsList) {
              writer.println(gson.toJson(itemsDTO));
              System.out.println(gson.toJson(itemsDTO));
            }
          }

          // getAllPaymentMethods
          if (json.contains("getAllPaymentMethods")) {
            List<PaymentDTO> PaymentList = PaymentDAO.getPaymentList();
            for (PaymentDTO paymentDTO : PaymentList) {
              writer.println(gson.toJson(paymentDTO));
              // System.out.println(gson.toJson(paymentDTO));
            }
          }

          // listTransactions
          if (json.contains("listTransactions")) {
            List<TransactionDTO> transactionList = TransactionDAO.listTransaction();
            for (TransactionDTO transactionDTO : transactionList) {
              writer.println(gson.toJson(transactionDTO));
              System.out.println(gson.toJson(transactionDTO));
            }
          }

        }

        dong.close();
      }
    } catch (IOException e) {
      System.out.println("Error opening socket");
      System.exit(1);
    }
  }
}
