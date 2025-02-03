package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import cna.nl.juliaforward.java3.JDBC.MariaDBProperties;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for handling DB queries for working with a MariaDB database.
 */
public class BookDatabaseManager {

    /**
     * The name of the database to connect to.
     */
    public static final String DB_NAME = "books";

    /**
     * Loads book and author data from a MariaDB database and populates the provided library.
     *
     * @param lib The library object to be populated with books, authors, and their relationships.
     */
    public static void loadLibrary(Library lib) {
        String BOOKS_QUERY = "SELECT isbn, title, editionNumber, copyright FROM TITLES";
        String AUTHORS_QUERY = "SELECT authorID, firstName, lastName FROM AUTHORS";
        String RELATIONSHIP_QUERY = "SELECT b.authorID, a.firstName, a.lastName, b.isbn, c.title, c.editionNumber, c.copyright FROM\n" +
                "authors a JOIN authorisbn b ON a.authorID = b.authorID\n" +
                "JOIN titles c ON c.isbn = b.isbn ";

        // Load all books from the titles table
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME,
                    MariaDBProperties.DATABASE_USER,
                    MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(BOOKS_QUERY);

            // Extract and add books to the library
            while (rs.next()) {
                Book currentBook = new Book(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getInt("editionNumber"),
                        rs.getString("copyright")
                );
                lib.getBookList().add(currentBook);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load all authors from the authors table
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME,
                    MariaDBProperties.DATABASE_USER,
                    MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(AUTHORS_QUERY);

            // Extract and add authors to the library
            while (rs.next()) {
                Author currentAuthor = new Author(
                        rs.getInt("authorID"),
                        rs.getString("firstName"),
                        rs.getString("lastName")
                );
                lib.getAuthorList().add(currentAuthor);
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Load relationships between authors and books
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME,
                    MariaDBProperties.DATABASE_USER,
                    MariaDBProperties.DATABASE_PASSWORD);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(RELATIONSHIP_QUERY);

            // Extract and establish relationships between books and authors
            while (rs.next()) {
                int authorID = rs.getInt("authorID");
                String isbn = rs.getString("isbn");

                // Retrieve book and author from the library
                Book currentBook = lib.getBook(isbn);
                Author currentAuthor = lib.getAuthor(authorID);

                // Add relationships if the book and author exist
                if (currentBook != null) {
                    currentBook.addAuthor(new Author(
                            authorID,
                            rs.getString("firstName"),
                            rs.getString("lastName")
                    ));
                }

                if (currentAuthor != null) {
                    currentAuthor.addBook(new Book(
                            isbn,
                            rs.getString("title"),
                            rs.getInt("editionNumber"),
                            rs.getString("copyright")
                    ));
                }
            }
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CREATE methods

    /**
     * Inserts a new book record into the database.
     *
     * @param book The book object containing the details of the book to be added.
     */
    public static void createBook(Book book) {
        String CREATE_BOOK_QUERY = "INSERT INTO titles VALUES (?, ?, ?, ?)";
        System.out.println(CREATE_BOOK_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME,
                    MariaDBProperties.DATABASE_USER,
                    MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(CREATE_BOOK_QUERY);

            // Set query parameters with book details
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setInt(3, book.getEditionNumber());
            pstmt.setString(4, book.getCopyright());

            pstmt.executeUpdate();

            System.out.println("Created book successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating book");
        }
    }

    /**
     * Inserts a new author record into the database.
     *
     * @param author The Author object containing the details of the author to be added.
     */
    public static void createAuthor(Author author) {
        String CREATE_AUTHOR_QUERY = "INSERT INTO authors VALUES (?, ?, ?)";
        System.out.println(CREATE_AUTHOR_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME,
                    MariaDBProperties.DATABASE_USER,
                    MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(CREATE_AUTHOR_QUERY);

            // Set query parameters with author details
            pstmt.setInt(1, author.getAuthorID());
            pstmt.setString(2, author.getFirstName());
            pstmt.setString(3, author.getLastName());

            pstmt.executeUpdate();

            System.out.println("Created author successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating author");
        }
    }


    public static void createRelation(Book book, Author author) {
        String CREATE_RELATION_QUERY = "INSERT INTO authorisbn VALUES (?, ?)";
        System.out.println(CREATE_RELATION_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(CREATE_RELATION_QUERY);
            pstmt.setInt(1, author.getAuthorID());
            pstmt.setString(2, book.getIsbn());
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Created relation successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error creating relation");
        }
    }

    // GET Methods
    public static Book getBook(String isbn) {
        String GET_BOOK_QUERY = "SELECT * FROM titles WHERE isbn = ?";
        System.out.println(GET_BOOK_QUERY);

        Book book = null;
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(GET_BOOK_QUERY);
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                book = new Book(rs.getString("isbn"), rs.getString("title"), rs.getInt("editionNumber"), rs.getString("copyright"));

                System.out.println("Successfully retrieved book!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting book");
        }

        return book;
    }

    public static List<Book> getAllBooks() {
        String GET_BOOKS_QUERY = "SELECT * FROM titles";
        System.out.println(GET_BOOKS_QUERY);

        List<Book> books = new ArrayList<Book>();
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(GET_BOOKS_QUERY);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = null;
                book = new Book(rs.getString("isbn"), rs.getString("title"), rs.getInt("editionNumber"), rs.getString("copyright"));
                books.add(book);

                System.out.println("Successfully retrieved: " + rs.getString("title"));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting books");
        }

        return books;
    }

    public static Author getAuthor(String authorID) {
        String GET_AUTHOR_QUERY = "SELECT * FROM authors WHERE authorID = ?";
        System.out.println(GET_AUTHOR_QUERY);

        Author author = null;
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(GET_AUTHOR_QUERY);
            pstmt.setString(1, authorID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                author = new Author(rs.getInt("authorID"), rs.getString("firstName"), rs.getString("lastName"));

                System.out.println("Successfully retrieved authors!");
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting authors");
        }

        return author;
    }

    public static List<Author> getAllAuthors() {
        String GET_AUTHORS_QUERY = "SELECT * FROM authors";
        System.out.println(GET_AUTHORS_QUERY);

        List<Author> authors = new ArrayList<Author>();
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(GET_AUTHORS_QUERY);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Author author = null;
                author = new Author(rs.getInt("authorID"), rs.getString("firstName"), rs.getString("lastName"));
                authors.add(author);

                System.out.println("Successfully retrieved: " + rs.getString("firstName") + " " + rs.getString("lastName"));
            }

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error getting authors");
        }

        return authors;
    }

    // UPDATE Methods
    public static void updateBook(String isbn, Book book) {
        String UPDATE_BOOK_QUERY = "UPDATE titles SET isbn = ?, title = ?, editionNumber = ?, copyright = ? WHERE isbn = ?";
        System.out.println(UPDATE_BOOK_QUERY);

        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOK_QUERY);
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setInt(3, book.getEditionNumber());
            pstmt.setString(4, book.getCopyright());
            pstmt.setString(5, isbn);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Successfully updated book!");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating book");
        }
    }

    public static void updateAuthor(int authorID, Author author) {
        String UPDATE_BOOK_QUERY = "UPDATE authors SET authorID = ?, firstName = ?, lastName = ? WHERE authorID = ?";
        System.out.println(UPDATE_BOOK_QUERY);

        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(UPDATE_BOOK_QUERY);
            pstmt.setInt(1, author.getAuthorID());
            pstmt.setString(2, author.getFirstName());
            pstmt.setString(3, author.getLastName());
            pstmt.setInt(4, authorID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Successfully updated book!");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error updating book");
        }
    }

    // DELETE Methods
    public static void deleteBook(String isbn) {
        String DELETE_BOOK_QUERY = "DELETE FROM titles WHERE isbn = ?";
        System.out.println(DELETE_BOOK_QUERY);

        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(DELETE_BOOK_QUERY);
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Successfully deleted book!");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting book");
        }
    }

    public static void deleteAuthor(String authorID) {
        String DELETE_AUTHOR_QUERY = "DELETE FROM authors WHERE authorID = ?";
        System.out.println(DELETE_AUTHOR_QUERY);

        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(DELETE_AUTHOR_QUERY);
            pstmt.setString(1, authorID);
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Successfully deleted author!");

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting author");
        }
    }

    public static void deleteRelation(Book book, Author author) {
        String DELETE_RELATION_QUERY = "DELETE FROM authorisbn WHERE authorID = ? AND isbn = ?";
        System.out.println(DELETE_RELATION_QUERY);
        try {
            Connection conn = DriverManager.getConnection(
                    MariaDBProperties.DATABASE_URL + DB_NAME, MariaDBProperties.DATABASE_USER, MariaDBProperties.DATABASE_PASSWORD);
            PreparedStatement pstmt = conn.prepareStatement(DELETE_RELATION_QUERY);
            pstmt.setInt(1, author.getAuthorID());
            pstmt.setString(2, book.getIsbn());
            ResultSet rs = pstmt.executeQuery();

            System.out.println("Deleted relation successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting relation");
        }
    }
}
