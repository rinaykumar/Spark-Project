import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PaymentDAO {
    private static PaymentDAO Instance;

    public static List<PaymentDTO> database = new ArrayList<>();

    public static PaymentDAO getInstance() {
        if (Instance == null) {
            Instance = new PaymentDAO();
        }
        return Instance;
    }

    public void addPayment(String name, String machineCode) {
        database.add(new PaymentDTO(name, machineCode));

    }

    public static List<PaymentDTO>getPaymentList(){
        return database;
    }
}