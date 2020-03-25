import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.*;
import java.util.stream.Collectors;

public class ItemsDAO {

    private static ItemsDAO Instance;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    ItemsDAO() {

    }

    public static ItemsDAO getInstance(){
        if(Instance == null){
            Instance = new ItemsDAO();
        }
        return Instance;
    }

    public void addItems(String name, String price, String machineCode) {
        // Connect to mongo
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> itemsCollection = db.getCollection("Items");

        // Create new DTO and convert to JSON
        ItemsDTO item = new ItemsDTO(name, price, machineCode);
        String itemJSON = gson.toJson(item);

        // Create new mongo Document from JSON
        Document newItem = Document.parse(itemJSON);

        // Add Document to Collection
        itemsCollection.insertOne(newItem);
    }

    public static List<String> getItemsList() {
        // Connect to mongo
        MongoDatabase db = DatabaseConnection.mongoClient.getDatabase("HW2Database");
        MongoCollection<Document> itemsCollection = db.getCollection("Items");

        // Grab Documents from Collection, remove _id field from Document, put into List<String>
        List<String> itemsList = itemsCollection.find().into(new ArrayList<>())
                .stream()
                .map(document -> {
                    document.remove("_id");
                    return document.toJson();
                })
                .collect(Collectors.toList());

        // For testing
        System.out.println("From getItemsList\n" + itemsList);

        return itemsList;
    }
}