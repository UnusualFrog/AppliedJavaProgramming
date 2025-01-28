package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> bookList = new ArrayList<Book>();
    private List<Author> authorList = new ArrayList<Author>();

    public List<Book> getBookList() {
        return bookList;
    }

    public List<Author> getAuthorList() {
        return authorList;
    }

    public void addBook(Book book) {
        bookList.add(book);
    }

    public void addAuthor(Author author) {
        authorList.add(author);
    }

    public Book getBook(String isbn) {
        for (Book book : bookList) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }
        return null;
    };

    public Author getAuthor(int authorID) {
        for (Author author : authorList) {
            if (author.getAuthorID() == authorID ) {
                return author;
            }
        }
        return null;
    }
}
