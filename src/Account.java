import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Account {

    private int accountNumber;
    private String holderName;
    private double balance;

    private List<Transaction> transactions = new ArrayList<>();

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void deposit(double amount) {

        if (amount <= 0)
        {
            System.out.println("Invalid deposit amount!");
        }
        else
        {
            balance += amount;
            transactions.add(new Transaction(Transaction.Type.DEPOSIT, amount, LocalDateTime.now()));
            System.out.println("Deposit successful!");
        }
    }

    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactions.add(new Transaction(Transaction.Type.WITHDRAWAL, amount, LocalDateTime.now()));
            System.out.println("Withdrawal successful!");
        }
    }

    public void displayAccountDetails() {

        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: " + String.format("%.2f", balance));
    }

    public void printStatement() {

        System.out.println("\n===== TRANSACTION STATEMENT =====");
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);

        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }

        System.out.println("Current Balance: ₹" +
                String.format("%.2f", balance));
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public double getBalance() {
        return balance;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}