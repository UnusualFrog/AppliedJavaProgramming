package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private int editionNumber;
    private String copyright;
    private List<Author> authorList;

    public Book(String isbn, String title, int editionNumber, String copyright) {
        this.isbn = isbn;
        this.title = title;
        this.editionNumber = editionNumber;
        this.copyright = copyright;
        this.authorList = new ArrayList<Author>();
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public String getCopyright() {
        return copyright;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void addAuthor(Author author) {
        this.authorList.add(author);

        if(!author.getBookList().contains(this)) {
            author.addBook(this);
        }
    }
}
