package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import java.util.ArrayList;
import java.util.List;

public class Author {
    private int authorID;
    private String firstName;
    private String lastName;
    private List<Book> bookList;

    public Author(int authorID, String firstName, String lastName) {
        this.authorID = authorID;
        this.firstName = firstName;
        this.lastName = lastName;
        bookList = new ArrayList<Book>();
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public int getAuthorID() {
        return authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void addBook(Book book) {
        this.bookList.add(book);

        if(!book.getAuthorList().contains(this)) {
            book.addAuthor(this);
        }
    }
}
