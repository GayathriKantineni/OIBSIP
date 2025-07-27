import java.util.*;

class Transaction {
    String type;
    double amount;
    String details;

    Transaction(String type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
    }

    public String toString() {
        return type + ": " + amount + " " + details;
    }
}

class Account {
    String userId;
    String userPin;
    double balance;
    List<Transaction> history;

    Account(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.history = new ArrayList<>();
    }

    void addTransaction(String type, double amount, String details) {
        history.add(new Transaction(type, amount, details));
    }
}

class ATMOperations {
    private Account account;

    ATMOperations(Account account) {
        this.account = account;
    }

    void showHistory() {
        if (account.history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            for (Transaction t : account.history) {
                System.out.println(t);
            }
        }
    }

    void withdraw(double amount) {
        if (amount > 0 && amount <= account.balance) {
            account.balance -= amount;
            account.addTransaction("Withdraw", amount, "");
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    void deposit(double amount) {
        if (amount > 0) {
            account.balance += amount;
            account.addTransaction("Deposit", amount, "");
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    void transfer(Account toAccount, double amount) {
        if (amount > 0 && amount <= account.balance) {
            account.balance -= amount;
            toAccount.balance += amount;
            account.addTransaction("Transfer to " + toAccount.userId, amount, "");
            toAccount.addTransaction("Transfer from " + account.userId, amount, "");
            System.out.println("Transferred: " + amount + " to " + toAccount.userId);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    double getBalance() {
        return account.balance;
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Demo accounts
        Account user1 = new Account("user1", "1234", 1000);
        Account user2 = new Account("user2", "5678", 2000);

        Map<String, Account> accounts = new HashMap<>();
        accounts.put(user1.userId, user1);
        accounts.put(user2.userId, user2);

        System.out.println("Welcome to the ATM!");

        // Login
        Account current = null;
        while (true) {
            System.out.print("Enter User ID: ");
            String uid = sc.nextLine();
            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();
            if (accounts.containsKey(uid) && accounts.get(uid).userPin.equals(pin)) {
                current = accounts.get(uid);
                break;
            } else {
                System.out.println("Invalid credentials. Try again.");
            }
        }

        ATMOperations atm = new ATMOperations(current);

        // Menu
        while (true) {
            System.out.println("\n1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                atm.showHistory();
            } else if (choice == 2) {
                System.out.print("Enter amount to withdraw: ");
                double amt = sc.nextDouble();
                sc.nextLine();
                atm.withdraw(amt);
            } else if (choice == 3) {
                System.out.print("Enter amount to deposit: ");
                double amt = sc.nextDouble();
                sc.nextLine();
                atm.deposit(amt);
            } else if (choice == 4) {
                System.out.print("Enter recipient User ID: ");
                String toId = sc.nextLine();
                if (accounts.containsKey(toId) && !toId.equals(current.userId)) {
                    System.out.print("Enter amount to transfer: ");
                    double amt = sc.nextDouble();
                    sc.nextLine();
                    atm.transfer(accounts.get(toId), amt);
                } else {
                    System.out.println("Invalid recipient.");
                }
            } else if (choice == 5) {
                System.out.println("Thank you for using the ATM. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
        sc.close();
    }
}


//Demo Account Credentials
//User ID: user1, PIN: 1234
//User ID: user2, PIN: 5678