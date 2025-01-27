package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

public class BookApplication {
    public static void main(String[] args) {
        //BookDatabaseManager dbm = new BookDatabaseManager();
        Library lib = new Library();

        BookDatabaseManager.loadLibrary(lib);

//        System.out.println(lib.getAuthorList());
//        System.out.println(lib.getBookList());

//        Book book = new Book("ZZZZ", "ZZZZZZZ", 99, "20XX");
//        BookDatabaseManager.createBook(book);

        Author author = new Author(123, "Eric", "Stock");
        BookDatabaseManager.createAuthor(author);

    }
}
