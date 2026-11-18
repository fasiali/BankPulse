
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Account> accounts = new ArrayList<>();
    private int nextAccountNumber = 1001;

    public void openAccount(String name) {

        Account account = new Account(nextAccountNumber, name, 0);
        accounts.add(account);

        System.out.println("Account created successfully!");
        System.out.println("Account Number: " + nextAccountNumber);

        nextAccountNumber++;
    }

    public Account findAccount(int accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }


    public void listAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        for (Account account : accounts) {
            account.displayAccountDetails();
            System.out.println("-------------------");
        }
    }

}

