import builder.ResponseBuilder;
import builder.ResponseDTO;

import java.util.*;

public class ItemsProcessor extends Factory {

    Date date = new Date();

    public ResponseDTO process(Map<String, String> args) {

        String endpoint = args.get("endpoint");

        // After getting endpoint, either addItem or listItems method is called
        if (endpoint.equals("addItem")) {
            return addItems(args);
        }

        if (endpoint.equals("listItems")) {
            return listItems(args);
        }
        return null;
    }

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
        buildResponse.setResponseCode("OK");
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
        buildResponse.setResponseCode("OK");
        buildResponse.setResponse("List of Items");

        return buildResponse.build();
    }
}
