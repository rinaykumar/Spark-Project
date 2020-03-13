import builder.ResponseBuilder;
import builder.ResponseDTO;
import java.util.*;

public class ItemsProcessor {

    Date date = new Date();

    public ResponseDTO addItems(Map<String, String> args) {
        // For testing
        System.out.println("From ItemsProcessor");
        System.out.println("Add Item");

        UUID uuid = UUID.randomUUID();
        String machineCode = uuid.toString();

        ItemsDAO addItemsDAO = new ItemsDAO();
        addItemsDAO.addItems(args.get("name"), args.get("price"), machineCode);

        args.put("machineCode", machineCode);

        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setParams(args);
        buildResponse.setResponseCode("200 OK");
        buildResponse.setResponse("Item Added");

        return buildResponse.build();
    }

    public ResponseDTO listItems(Map<String, String> args) {
        // For testing
        System.out.println("From ItemsProcessor");
        System.out.println("List Items");

        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setParams(args);
        buildResponse.setResponseCode("200 OK");
        buildResponse.setResponse("List of Items");

        return buildResponse.build();
    }
}
