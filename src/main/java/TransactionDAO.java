import java.util.List;
import java.util.ArrayList;
import builder.ResponseDTO;

public class TransactionDAO {

    private static TransactionDAO Instance;
    private static List<TransactionDTO> transactionList = new ArrayList<>();

    public static TransactionDAO getInstance(){
        if(Instance == null){
            Instance = new TransactionDAO();
        }
        return Instance;
    }

    public static List<TransactionDTO> listTransaction() {
        return transactionList;
    }

    public static boolean isTransactionValid(TransactionDAO Instance) {
        return true;
    }


    public void createTransaction(String paymentMethod, String itemCode) {
        transactionList.add(new TransactionDTO(paymentMethod,itemCode));
    }
}