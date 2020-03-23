import builder.ResponseBuilder;
import builder.ResponseDTO;
import java.util.*;

//I'm making the assumption of using PaymentDTO (as a cursor) to access PaymentDAO then return ResponseDTO
public class PaymentMethods extends Factory {
    Date date = new Date();

    public ResponseDTO process(Map<String, String> args) {
        //   System.out.println("PASS IN PROCESS");
        String endpoint = args.get("endpoint");

        // After getting endpoint, either addItem or listItems method is called
        if (endpoint.equals("addPaymentMethod")) {
            // System.out.println("PASS IN HERE");
            return addPay(args);
        }

        if (endpoint.equals("getAllPaymentMethods")) {
            return getPay(args);
        }
        return null;
    }

    public ResponseDTO addPay(Map<String,String> args){
        UUID uuid = UUID.randomUUID();
        String machineCode = uuid.toString();

        PaymentDAO addPayDAO = new PaymentDAO();
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