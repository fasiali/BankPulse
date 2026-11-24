import java.util.Scanner;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        FileStorage storage = new FileStorage();

        String filePath = "accounts.txt";

        List<Account> loadedAccounts =
                storage.loadAccounts(filePath);

        Bank bank = new Bank();

        // Restore previously saved accounts.
        for (Account account : loadedAccounts) {
            bank.getAccounts().add(account);
        }

        // Generate the next unused account number.
        int maxAccountNumber = 1000;

        for (Account account : bank.getAccounts()) {
            maxAccountNumber = Math.max(
                    maxAccountNumber,
                    account.getAccountNumber()
            );
        }

        // Set the next account number.
        // Bank's default starts at 1001, so this is handled
        // through the method added below in Bank.java.

        int choice;

        do {

            System.out.println("\n===== BANKPULSE =====");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. List Accounts");
            System.out.println("5. Print Statement");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:

                    System.out.print("Enter holder name: ");

                    String name = sc.nextLine();

                    bank.openAccount(name);

                    break;

                case 2:

                    System.out.print("Enter account number: ");

                    int depositAcc = sc.nextInt();

                    Account depositAccount =
                            bank.findAccount(depositAcc);

                    if (depositAccount != null) {

                        System.out.print("Enter amount: ");

                        double depositAmount = sc.nextDouble();

                        depositAccount.deposit(depositAmount);

                    } else {

                        System.out.println("Account not found!");
                    }

                    break;

                case 3:

                    System.out.print("Enter account number: ");

                    int withdrawAcc = sc.nextInt();

                    Account withdrawAccount =
                            bank.findAccount(withdrawAcc);

                    if (withdrawAccount != null) {

                        System.out.print("Enter amount: ");

                        double withdrawAmount = sc.nextDouble();

                        withdrawAccount.withdraw(withdrawAmount);

                    } else {

                        System.out.println("Account not found!");
                    }

                    break;

                case 4:

                    bank.listAccounts();

                    break;

                case 5:

                    System.out.print("Enter account number: ");

                    int statementAcc = sc.nextInt();

                    Account statementAccount =
                            bank.findAccount(statementAcc);

                    if (statementAccount != null) {

                        statementAccount.printStatement();

                    } else {

                        System.out.println("Account not found!");
                    }

                    break;

                case 6:

                    storage.saveAccounts(
                            bank.getAccounts(), filePath
                    );

                    System.out.println(
                            "Thank you for using BankPulse!"
                    );

                    break;

                default:

                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);

        sc.close();
    }
}
