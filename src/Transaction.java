import java.time.LocalDateTime;

public class Transaction {

    public enum Type {
        DEPOSIT,
        WITHDRAWAL
    }

    private Type type;
    private double amount;
    private LocalDateTime timestamp;

    public Transaction(Type type, double amount, LocalDateTime timestamp) {
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void displayTransaction() {
        System.out.println(
                timestamp + " | " + type + " | ₹" +
                        String.format("%.2f", amount)
        );
    }
}