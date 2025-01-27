package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import cna.nl.juliaforward.java3.JDBC.MariaDBProperties;
import org.checkerframework.checker.units.qual.A;

import java.sql.*;

public class BookDatabaseManager {

    public static final String DB_NAME = "books";

    public static void loadLibrary(Library lib) {
        String BOOKS_QUERY = "SELECT isbn, title, editionNumber, copyright FROM TITLES";
        String AUTHORS_QUERY = "SELECT authorID, firstName, lastName FROM AUTHORS";

        // Load all books from the titles table
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(BOOKS_QUERY);

            // Extract all book data
            while (rs.next()) {
                Book currentBook = new Book(rs.getString("isbn"), rs.getString("title"), rs.getInt("editionNumber"), rs.getString("copyright"));
                lib.getBookList().add(currentBook);

//                System.out.println("=".repeat(20));
//                System.out.println("ISBN: " + currentBook.getIsbn());
//                System.out.println("Title: " + currentBook.getTitle());
//                System.out.println("Edition Number: " + currentBook.getEditionNumber());
//                System.out.println("Copyright: " + currentBook.getCopyright());

                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load all authors
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(AUTHORS_QUERY);

            // Extract all book data
            while (rs.next()) {
                Author currentAuthor = new Author(rs.getInt("authorID"), rs.getString("firstName"), rs.getString("lastName"));
                lib.getAuthorList().add(currentAuthor);

//                System.out.println("=".repeat(20));
//                System.out.println("Author ID: " + currentAuthor.getAuthorID());
//                System.out.println("First Name: " + currentAuthor.getFirstName());
//                System.out.println("Last Name: " + currentAuthor.getLastName());

                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createBook(Book book) {
        String CREATE_BOOK_QUERY = "INSERT INTO titles VALUES (\"%s\", \"%s\", %d, \"%s\")";
        CREATE_BOOK_QUERY = String.format(CREATE_BOOK_QUERY, book.getIsbn(),book.getTitle(), book.getEditionNumber(), book.getCopyright());
        System.out.println(CREATE_BOOK_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(CREATE_BOOK_QUERY);

            System.out.println("Created book successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating book");
        }
    }

    public static void createAuthor(Author author) {
        String CREATE_AUTHOR_QUERY = "INSERT INTO authors VALUES (%d, \"%s\", \"%s\")";
        CREATE_AUTHOR_QUERY = String.format(CREATE_AUTHOR_QUERY, author.getAuthorID(), author.getFirstName(), author.getLastName());
        System.out.println(CREATE_AUTHOR_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(CREATE_AUTHOR_QUERY);

            System.out.println("Created author successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating author");
        }
    }
}
