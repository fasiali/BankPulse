
public class Account {

    private int accountNumber;
    private String holderName;
    private double balance;

    public Account(int accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public void displayAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + holderName);
        System.out.println("Balance: " + String.format("%.2f", balance));
    }

    public void deposit(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
        }
        else {
            balance += amount;
            System.out.println("Deposit successful!");
        }
    }


    public void withdraw(double amount) {

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
        }
        else if (amount > balance) {
            System.out.println("Insufficient balance!");
        }
        else {
            balance -= amount;
            System.out.println("Withdrawal successful!");
        }
    }

    public int getAccountNumber() {
        return accountNumber;
    }

}
