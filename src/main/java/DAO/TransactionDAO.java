package DAO;

import DTO.TransactionDTO;
import Database.DatabaseConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;
import java.util.stream.Collectors;

public class TransactionDAO {

    private static TransactionDAO Instance;
    public static List<TransactionDTO> transactionList = new ArrayList<>();

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    public static TransactionDAO getInstance(){
        if(Instance == null){
            Instance = new TransactionDAO();
        }
        return Instance;
    }

    public static List<String> listTransaction() {
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> transactionCollection = db.getCollection("Transactions");

        // Grab Documents from Collection, remove _id field from Document, put into List<String>
        List<String> transactionList = transactionCollection.find().into(new ArrayList<>())
                .stream()
                .map(document -> {
                    document.remove("_id");
                    return document.toJson();
                })
                .collect(Collectors.toList());

        return  transactionList;
    }

    public static boolean isTransactionValid(TransactionDAO Instance) {
        // check if payment method exists
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> transactionCollection = db.getCollection("Transactions");

        return true;
    }

    public void createTransaction(String paymentMethod, String itemCode) {
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> transactionCollection = db.getCollection("Transactions");

        // Create new DTO and convert to JSON
        TransactionDTO transaction = new TransactionDTO(paymentMethod, itemCode);
        String transactionJSON = gson.toJson(transaction);

        // Create new mongo Document from JSON
        Document newTransaction = Document.parse(transactionJSON);

        // Add Document to Collection
        transactionList.insertOne(newTransaction);
    }
}