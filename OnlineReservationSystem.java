import java.util.*;

class User {
    String username, password;
    User(String u, String p) { username = u; password = p; }
}

class Reservation {
    String pnr, username, trainNo, trainName, classType, date, from, to;
    Reservation(String pnr, String username, String trainNo, String trainName, String classType, String date, String from, String to) {
        this.pnr = pnr; this.username = username; this.trainNo = trainNo; this.trainName = trainName;
        this.classType = classType; this.date = date; this.from = from; this.to = to;
    }
    public String toString() {
        return "PNR: " + pnr + "\nUser: " + username + "\nTrain No: " + trainNo + "\nTrain Name: " + trainName +
               "\nClass: " + classType + "\nDate: " + date + "\nFrom: " + from + "\nTo: " + to;
    }
}

public class OnlineReservationSystem {
    static Scanner sc = new Scanner(System.in);
    static Map<String, User> users = new HashMap<>();
    static Map<String, Reservation> reservations = new HashMap<>();
    static Map<String, String> trainData = new HashMap<>();
    static Random rand = new Random();

    public static void main(String[] args) {
        // Demo data
        users.put("user1", new User("user1", "pass1"));
        trainData.put("101", "Rajdhani Express");
        trainData.put("102", "Shatabdi Express");

        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) {
                if (login()) mainMenu();
            } else break;
        }
    }

    static boolean login() {
        System.out.print("Username: ");
        String u = sc.nextLine();
        System.out.print("Password: ");
        String p = sc.nextLine();
        if (users.containsKey(u) && users.get(u).password.equals(p)) {
            System.out.println("Login successful.");
            currentUser = u;
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    static String currentUser = null;

    static void mainMenu() {
        while (true) {
            System.out.println("\n1. Reserve Ticket\n2. Cancel Ticket\n3. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) reserveTicket();
            else if (ch == 2) cancelTicket();
            else break;
        }
        currentUser = null;
    }

    static void reserveTicket() {
        System.out.print("Enter Train Number (101/102): ");
        String trainNo = sc.nextLine();
        String trainName = trainData.getOrDefault(trainNo, "Unknown Train");
        System.out.print("Class Type (Sleeper/AC): ");
        String classType = sc.nextLine();
        System.out.print("Date of Journey (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("From: ");
        String from = sc.nextLine();
        System.out.print("To: ");
        String to = sc.nextLine();
        String pnr = "PNR" + (1000 + rand.nextInt(9000));
        Reservation r = new Reservation(pnr, currentUser, trainNo, trainName, classType, date, from, to);
        reservations.put(pnr, r);
        System.out.println("Reservation successful! Your PNR: " + pnr);
    }

    static void cancelTicket() {
        System.out.print("Enter PNR to cancel: ");
        String pnr = sc.nextLine();
        Reservation r = reservations.get(pnr);
        if (r == null || !r.username.equals(currentUser)) {
            System.out.println("No reservation found for this PNR.");
            return;
        }
        System.out.println("Reservation Details:\n" + r);
        System.out.print("Confirm cancellation? (Y/N): ");
        String confirm = sc.nextLine();
        if (confirm.equalsIgnoreCase("Y")) {
            reservations.remove(pnr);
            System.out.println("Reservation cancelled.");
        } else {
            System.out.println("Cancellation aborted.");
        }
    }
}


//Demo User Credentials
//Username: user1
//Password: pass1