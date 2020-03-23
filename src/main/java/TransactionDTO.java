public class TransactionDTO {
    public final String paymentMethod;
    public final String itemCode;

    public TransactionDTO(String paymentMethod, String itemCode) {
        this.paymentMethod = paymentMethod;
        this.itemCode = itemCode;
    }
}