import builder.ResponseBuilder;
import builder.ResponseDTO;
import java.util.*;

public class Factory {

    public static ResponseDTO process(String input) {
        int firstSlashIndex,
                firstQuestIndex,
                httpIndex,
                firstEqualIndex,
                firstAndIndex = 0;
        String process = ""; // Stores the process
        Map<String, String> args = new HashMap<String, String>(); // Stores the params and values
        Date date = new Date();

        // Parse the input to get process and params/values
        firstSlashIndex = input.indexOf('/');
        firstQuestIndex = input.indexOf('?');
        httpIndex = input.indexOf("HTTP");
        firstEqualIndex = input.indexOf('=');
        firstAndIndex = input.indexOf('&');

        if (firstQuestIndex != -1) {
            process = input.substring(firstSlashIndex+1, firstQuestIndex);
        } else {
            process = input.substring(firstSlashIndex+1, httpIndex-1);
        }

        // Add endpoint name to map
        args.put("endpoint", process);

        if (firstEqualIndex != -1) {
            String param = input.substring(firstQuestIndex+1, firstEqualIndex);
            String value = "";
            if (firstAndIndex != -1) {
                value = input.substring(firstEqualIndex+1, firstAndIndex);
            } else {
                value = input.substring(firstEqualIndex+1, httpIndex-1);
            }
            args.put(param, value);
        }

        if (firstEqualIndex != input.lastIndexOf('=')) {
            String param = input.substring(firstAndIndex+1, input.indexOf('=', firstEqualIndex+1));
            String value = input.substring(input.indexOf('=', firstEqualIndex+1)+1, httpIndex-1);
            args.put(param, value);
        }

        // Testing parsing by printing out map
        for (Map.Entry<String, String> entry : args.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // Instantiate correct processor subclass based on value stored in process
        switch(process) {
            case "addPaymentMethod":
            case "getAllPaymentMethods":
                // Send args map to PaymentProcessor
                PaymentMethods PaymentMethods = new PaymentMethods();
                return PaymentMethods.process(args);
            case "addItem":
            case "listItems":
                // Send args map ItemsProcessor
                ItemsProcessor itemsProcessor = new ItemsProcessor();
                return itemsProcessor.process(args);
            case "createTransaction":
                TransactionProcessor createTransactionProcess = new TransactionProcessor();
                return createTransactionProcess.process(args);
            case "listTransactions":
                System.out.println("List Transactions!!!");
                TransactionProcessor listTransactionProcessor = new TransactionProcessor();
                return listTransactionProcessor.process(args);
            case "" :
                return null;
            default:
                System.out.println("Error"); // For testing

                ResponseBuilder buildResponse = new ResponseBuilder();
                buildResponse.setDate(date);
                buildResponse.setParams(args);
                buildResponse.setResponseCode("ERROR");
                buildResponse.setResponse("Input Error");

                return buildResponse.build();
        }
    }
}
