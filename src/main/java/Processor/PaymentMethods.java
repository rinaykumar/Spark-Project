package Processor;

import DAO.PaymentDAO;
import Builder.ResponseBuilder;
import Builder.ResponseDTO;
import java.util.*;

public class PaymentMethods  {
    Date date = new Date();

    public ResponseDTO addPay(Map<String,String> args){
        UUID uuid = UUID.randomUUID();
        String machineCode = uuid.toString();

        PaymentDAO addPayDAO = PaymentDAO.getInstance();
        addPayDAO.addPayment(args.get("method"), machineCode);

        args.put("machineCode", machineCode);

        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setResponse("Payment Added");
        buildResponse.setParams(args);
        buildResponse.setResponseCode("OK");

        return buildResponse.build();
    }

    public ResponseDTO getPay(Map<String,String>args){

        ResponseBuilder buildResponse = new ResponseBuilder();
        buildResponse.setDate(date);
        buildResponse.setParams(args);
        buildResponse.setResponseCode("OK");
        buildResponse.setResponse("List of Payments");

        return buildResponse.build();
    }
}