package Processor;

import DAO.TransactionDAO;
import Builder.ResponseBuilder;
import Builder.ResponseDTO;
import java.util.*;

public class TransactionProcessor  {

    Date date = new Date();

    public ResponseDTO process(Map<String, String> args) {

        String endpoint = args.get("endpoint");

        if (endpoint.equals("createTransaction")) {
            return createTransaction(args);
        }

        if (endpoint.equals("listTransactions")) {
            return listTransactions(args);
        }
        return null;
    }

    public ResponseDTO listTransactions(Map<String, String> args) {

        // Use response builder to build response
        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setParams(args);
        buildResponse.setResponseCode("OK");
        buildResponse.setResponse("List of Transactions");

        return buildResponse.build();
    }

    public ResponseDTO createTransaction(Map<String, String> args) {
        TransactionDAO createTransactionDAO = new TransactionDAO();
        createTransactionDAO.createTransaction(args.get("paymentMethod"),args.get("itemCode"));

        // Use response builder to build response
        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setParams(args);
        buildResponse.setResponseCode("OK");
        buildResponse.setResponse("Transaction Added");

        return buildResponse.build();
    }
}