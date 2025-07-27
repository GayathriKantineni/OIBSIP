import java.util.*;

public class OnlineExamination {
    static Scanner sc = new Scanner(System.in);
    static Map<String, String> users = new HashMap<>();
    static Map<String, String> profiles = new HashMap<>();
    static String currentUser = null;

    static String[] questions = {
        "Java is a: \nA) Programming Language\nB) Operating System\nC) Database\nD) Browser",
        "Which is not a Java keyword?\nA) static\nB) Boolean\nC) void\nD) private",
        "Which company developed Java?\nA) Sun Microsystems\nB) Microsoft\nC) Apple\nD) Google"
    };
    static char[] answers = {'A', 'B', 'A'};

    public static void main(String[] args) {
        // Add a default user
        users.put("user", "pass");
        profiles.put("user", "Default User");

        while (true) {
            System.out.println("\n1. Login\n2. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                if (login()) {
                    dashboard();
                }
            } else {
                System.out.println("Goodbye!");
                break;
            }
        }
    }

    static boolean login() {
        System.out.print("Username: ");
        String uname = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        if (users.containsKey(uname) && users.get(uname).equals(pass)) {
            currentUser = uname;
            System.out.println("Login successful. Welcome, " + profiles.get(uname) + "!");
            return true;
        } else {
            System.out.println("Invalid credentials.");
            return false;
        }
    }

    static void dashboard() {
        while (true) {
            System.out.println("\n1. Update Profile\n2. Change Password\n3. Take Exam\n4. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt();
            sc.nextLine();
            if (ch == 1) {
                updateProfile();
            } else if (ch == 2) {
                changePassword();
            } else if (ch == 3) {
                takeExam();
            } else if (ch == 4) {
                System.out.println("Logged out.");
                currentUser = null;
                break;
            }
        }
    }

    static void updateProfile() {
        System.out.print("Enter new profile name: ");
        String name = sc.nextLine();
        profiles.put(currentUser, name);
        System.out.println("Profile updated.");
    }

    static void changePassword() {
        System.out.print("Enter current password: ");
        String oldPass = sc.nextLine();
        if (users.get(currentUser).equals(oldPass)) {
            System.out.print("Enter new password: ");
            String newPass = sc.nextLine();
            users.put(currentUser, newPass);
            System.out.println("Password changed.");
        } else {
            System.out.println("Incorrect current password.");
        }
    }

    static void takeExam() {
        int score = 0;
        char[] userAnswers = new char[questions.length];
        int timeLimit = 30; // seconds
        long startTime = System.currentTimeMillis();

        System.out.println("\nExam started! You have " + timeLimit + " seconds.");

        for (int i = 0; i < questions.length; i++) {
            long elapsed = (System.currentTimeMillis() - startTime) / 1000;
            if (elapsed >= timeLimit) {
                System.out.println("\nTime's up! Auto-submitting your answers.");
                break;
            }
            System.out.println("\nQ" + (i + 1) + ": " + questions[i]);
            System.out.print("Your answer (A/B/C/D): ");
            String ans = sc.nextLine().toUpperCase();
            if (ans.length() > 0) userAnswers[i] = ans.charAt(0);
            else userAnswers[i] = ' ';
        }

        // Auto-submit if time runs out
        for (int i = 0; i < questions.length; i++) {
            if (userAnswers[i] == answers[i]) score++;
        }
        System.out.println("\nExam finished. Your score: " + score + "/" + questions.length);
    }
}