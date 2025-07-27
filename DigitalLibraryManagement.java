import java.util.*;

class Book {
    String id, title, author, category;
    boolean issued = false;
    String issuedTo = null;

    Book(String id, String title, String author, String category) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
    }

    public String toString() {
        return id + " | " + title + " | " + author + " | " + category + (issued ? " | Issued to: " + issuedTo : " | Available");
    }
}

class Member {
    String id, name, email;

    Member(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String toString() {
        return id + " | " + name + " | " + email;
    }
}

class Library {
    Map<String, Book> books = new HashMap<>();
    Map<String, Member> members = new HashMap<>();

    void addBook(Book b) { books.put(b.id, b); }
    void removeBook(String id) { books.remove(id); }
    void addMember(Member m) { members.put(m.id, m); }
    void removeMember(String id) { members.remove(id); }

    Book getBook(String id) { return books.get(id); }
    Member getMember(String id) { return members.get(id); }

    List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.title.toLowerCase().contains(keyword.toLowerCase()) ||
                b.author.toLowerCase().contains(keyword.toLowerCase()) ||
                b.category.toLowerCase().contains(keyword.toLowerCase())) {
                result.add(b);
            }
        }
        return result;
    }

    List<Book> getBooksByCategory(String category) {
        List<Book> result = new ArrayList<>();
        for (Book b : books.values()) {
            if (b.category.equalsIgnoreCase(category)) result.add(b);
        }
        return result;
    }
}

public class DigitalLibraryManagement {
    static Scanner sc = new Scanner(System.in);
    static Library library = new Library();

    public static void main(String[] args) {
        // Demo data
        library.addBook(new Book("B1", "Java Programming", "James Gosling", "Programming"));
        library.addBook(new Book("B2", "Data Structures", "Mark Allen", "Computer Science"));
        library.addBook(new Book("B3", "Harry Potter", "J.K. Rowling", "Fiction"));
        library.addMember(new Member("U1", "Alice", "alice@mail.com"));
        library.addMember(new Member("U2", "Bob", "bob@mail.com"));

        while (true) {
            System.out.println("\n1. Admin Login\n2. User Login\n3. Exit");
            System.out.print("Choose: ");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) adminModule();
            else if (ch == 2) userModule();
            else break;
        }
    }

    static void adminModule() {
        System.out.print("Enter admin password: ");
        String pass = sc.nextLine();
        if (!pass.equals("admin123")) {
            System.out.println("Wrong password!");
            return;
        }
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. Add Book\n2. Remove Book\n3. Add Member\n4. Remove Member\n5. View All Books\n6. View All Members\n7. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) {
                System.out.print("Book ID: "); String id = sc.nextLine();
                System.out.print("Title: "); String title = sc.nextLine();
                System.out.print("Author: "); String author = sc.nextLine();
                System.out.print("Category: "); String cat = sc.nextLine();
                library.addBook(new Book(id, title, author, cat));
                System.out.println("Book added.");
            } else if (ch == 2) {
                System.out.print("Book ID to remove: "); String id = sc.nextLine();
                library.removeBook(id);
                System.out.println("Book removed.");
            } else if (ch == 3) {
                System.out.print("Member ID: "); String id = sc.nextLine();
                System.out.print("Name: "); String name = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                library.addMember(new Member(id, name, email));
                System.out.println("Member added.");
            } else if (ch == 4) {
                System.out.print("Member ID to remove: "); String id = sc.nextLine();
                library.removeMember(id);
                System.out.println("Member removed.");
            } else if (ch == 5) {
                for (Book b : library.books.values()) System.out.println(b);
            } else if (ch == 6) {
                for (Member m : library.members.values()) System.out.println(m);
            } else if (ch == 7) {
                break;
            }
        }
    }

    static void userModule() {
        System.out.print("Enter Member ID: ");
        String uid = sc.nextLine();
        Member user = library.getMember(uid);
        if (user == null) {
            System.out.println("No such user.");
            return;
        }
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. View All Books\n2. Search Book\n3. Browse by Category\n4. Issue Book\n5. Return Book\n6. Email Query\n7. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt(); sc.nextLine();
            if (ch == 1) {
                for (Book b : library.books.values()) System.out.println(b);
            } else if (ch == 2) {
                System.out.print("Enter keyword: "); String kw = sc.nextLine();
                List<Book> found = library.searchBooks(kw);
                for (Book b : found) System.out.println(b);
                if (found.isEmpty()) System.out.println("No books found.");
            } else if (ch == 3) {
                System.out.print("Enter category: "); String cat = sc.nextLine();
                List<Book> found = library.getBooksByCategory(cat);
                for (Book b : found) System.out.println(b);
                if (found.isEmpty()) System.out.println("No books found.");
            } else if (ch == 4) {
                System.out.print("Book ID to issue: "); String bid = sc.nextLine();
                Book b = library.getBook(bid);
                if (b == null) System.out.println("Book not found.");
                else if (b.issued) System.out.println("Book already issued.");
                else {
                    b.issued = true;
                    b.issuedTo = uid;
                    System.out.println("Book issued.");
                }
            } else if (ch == 5) {
                System.out.print("Book ID to return: "); String bid = sc.nextLine();
                Book b = library.getBook(bid);
                if (b == null) System.out.println("Book not found.");
                else if (!b.issued || !uid.equals(b.issuedTo)) System.out.println("You have not issued this book.");
                else {
                    b.issued = false;
                    b.issuedTo = null;
                    System.out.println("Book returned.");
                }
            } else if (ch == 6) {
                System.out.print("Enter your query: ");
                String query = sc.nextLine();
                System.out.println("Query sent to admin from " + user.email + ": " + query);
            } else if (ch == 7) {
                break;
            }
        }
    }
}



//Demo Account Credentials
//Admin password: admin123
//Demo users:
//
//Member ID: U1 (Alice)
//Member ID: U2 (Bob)