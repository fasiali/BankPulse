import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Base64;

public class FileStorage {

    public void saveAccounts(List<Account> accounts, String path) {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path), StandardCharsets.UTF_8)) {

            for (Account account : accounts) {
                String encodedName = Base64.getEncoder().encodeToString(account.getHolderName().getBytes(StandardCharsets.UTF_8)
                );

                writer.write(
                        "ACCOUNT|" + account.getAccountNumber() + "|" + encodedName + "|" + account.getBalance()
                );

                writer.newLine();

                for (Transaction transaction : account.getTransactions()) {

                    writer.write(
                            "TRANSACTION|" + account.getAccountNumber() + "|" + transaction.getType() + "|" +
                                    transaction.getAmount() + "|" +
                                    transaction.getTimestamp()
                    );
                    writer.newLine();
                }
            }
            System.out.println("Data saved successfully!");
        } catch (IOException e) {
            System.out.println("Error saving account data: " + e.getMessage());
        }
    }

    public List<Account> loadAccounts(String path) {

        List<Account> accounts = new ArrayList<>();
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            System.out.println("No previous data found. Starting fresh.");
            return accounts;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts[0].equals("ACCOUNT")) {
                    if (parts.length != 4) {
                        throw new IOException("Invalid account record");
                    }
                    int accountNumber = Integer.parseInt(parts[1]);
                    String name = new String(
                            Base64.getDecoder().decode(parts[2]),
                            StandardCharsets.UTF_8
                    );

                    double balance = Double.parseDouble(parts[3]);
                    if (balance < 0 || !Double.isFinite(balance)) {
                        throw new IOException("Invalid account balance");
                    }

                    if (findAccountInList(accounts, accountNumber) != null) {
                        throw new IOException("Duplicate account number");
                    }

                    Account account = new Account(
                            accountNumber, name, balance
                    );
                    accounts.add(account);

                } else if (parts[0].equals("TRANSACTION")) {

                    if (parts.length != 5) {
                        throw new IOException("Invalid transaction record");
                    }

                    int accountNumber = Integer.parseInt(parts[1]);

                    Account account = findAccountInList(
                            accounts, accountNumber
                    );

                    if (account == null) {
                        throw new IOException("Transaction has no matching account");
                    }

                    Transaction.Type type =
                            Transaction.Type.valueOf(parts[2]);

                    double amount = Double.parseDouble(parts[3]);

                    LocalDateTime timestamp =
                            LocalDateTime.parse(parts[4]);

                    if (amount <= 0 || !Double.isFinite(amount)) {
                        throw new IOException("Invalid transaction amount");
                    }

                    account.addTransaction(
                            new Transaction(type, amount, timestamp)
                    );

                } else {

                    throw new IOException("Unknown record type");
                }
            }

            System.out.println("Accounts loaded successfully!");

        } catch (IOException | RuntimeException e) {

            System.out.println(
                    "Could not load account data. Starting with zero accounts."
            );

            System.out.println("Reason: " + e.getMessage());

            accounts.clear();
        }

        return accounts;
    }

    private Account findAccountInList(
            List<Account> accounts, int accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }

        return null;
    }
}