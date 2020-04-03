package DAO;

import DTO.PaymentDTO;
import Database.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;
import java.util.stream.Collectors;

public class PaymentDAO {
    private static PaymentDAO Instance;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public static PaymentDAO getInstance() {
        if (Instance == null) {
            Instance = new PaymentDAO();
        }
        return Instance;
    }

    public void addPayment(String name, String machineCode) {
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> PayCollection = db.getCollection("Payments");

        // Create new DTO and convert to JSON
        PaymentDTO payment = new PaymentDTO(name, machineCode);
        String paymentJson = gson.toJson(payment);

        // Create new mongo Document from JSON
        Document newPayment = Document.parse(paymentJson);

        // Add Document to Collection
        PayCollection.insertOne(newPayment);
    }

    public static List<String>getPaymentList(){
        // Connect to mongo
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> PayCollection = db.getCollection("Payments");

        // Grab Documents from Collection, remove _id field from Document, put into List<String>
        List<String> PaymentList = PayCollection.find().into(new ArrayList<>())
                .stream()
                .map(document -> {
                    document.remove("_id");
                    return document.toJson();
                })
                .collect(Collectors.toList());

        // For testing
        System.out.println("From getPayList\n" + PaymentList);

        return PaymentList;
    }
}