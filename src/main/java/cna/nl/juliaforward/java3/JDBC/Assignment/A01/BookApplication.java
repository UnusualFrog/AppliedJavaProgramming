package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import org.checkerframework.checker.units.qual.A;

public class BookApplication {
    public static void main(String[] args) {
        //BookDatabaseManager dbm = new BookDatabaseManager();
        Library lib = new Library();

        BookDatabaseManager.loadLibrary(lib);

//        System.out.println(lib.getAuthorList());
//        System.out.println(lib.getBookList());

//        Book book = new Book("JJJJ", "JJJJJJJJJ", 89, "31XX");
//        BookDatabaseManager.createBook(book);

//        Author author = new Author(133, "John", "Smith");
//        BookDatabaseManager.createAuthor(author);

//        System.out.println(BookDatabaseManager.getBook("ZZZZ").getTitle());
//        System.out.println(BookDatabaseManager.getAllBooks());

//        System.out.println(BookDatabaseManager.getAuthor("5").getFirstName() +" " + BookDatabaseManager.getAuthor("5").getLastName());
//        System.out.println(BookDatabaseManager.getAllAuthors());

//        BookDatabaseManager.updateBook("GGGG", new Book("GGGG", "This is an update", 20, "XXXX"));
        BookDatabaseManager.updateAuthor("133", new Author(133, "Mark", "Danielewski"));
    }
}
