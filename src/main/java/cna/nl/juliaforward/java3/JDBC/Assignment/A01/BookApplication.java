package cna.nl.juliaforward.java3.JDBC.Assignment.A01;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;



public class BookApplication {
    public static void printOptions() {
        System.out.println("=".repeat(20));
        System.out.println("0. Exit");
        System.out.println("1. Show All Books");
        System.out.println("2. Show All Authors");
        System.out.println("3. Edit Book by ISBN");
        System.out.println("4. Edit Author by ID");
        System.out.println("5. Add New Book");
        System.out.println("=".repeat(20));
    }

    public static void showAllBooks(Library lib) {
        for (Book book : lib.getBookList()) {
            System.out.print("Title: " + book.getTitle() + " " + "\nISBN: " + book.getIsbn() +
                    "\nEdition: " + book.getEditionNumber() + "\nCopyright: " + book.getCopyright());

            // Single or Multi author
            if(book.getAuthorList().size() > 1) {
                System.out.print("\nAuthors: ");
                for(int i = 0; i < book.getAuthorList().size(); i++) {
                    // Check for last author
                    if (i == book.getAuthorList().size() - 1) {
                        System.out.print(book.getAuthorList().get(i).getFirstName() + " " + book.getAuthorList().get(i).getLastName());
                    } else {
                        System.out.print(book.getAuthorList().get(i).getFirstName() + " " + book.getAuthorList().get(i).getLastName() + ", ");
                    }
                }
            } else {
                System.out.print("\nAuthor: " + book.getAuthorList().getFirst().getFirstName() + " " + book.getAuthorList().getFirst().getLastName());
            }


            System.out.println();
            System.out.println("-".repeat(20));
        }
    }

    public static void showAllAuthors(Library lib) {
        for (Author author : lib.getAuthorList()) {
            System.out.print("Name: " + author.getFirstName() + " " + author.getLastName() +
                    "\nAuthorID: " + author.getAuthorID());

            // Single or Multi author
            if(author.getBookList().size() > 1) {
                System.out.print("\nBooks: \n");
                for(int i = 0; i < author.getBookList().size(); i++) {
                    // Check for last book
                    if (i == author.getBookList().size() - 1) {
                        System.out.print("\t" + author.getBookList().get(i).getTitle());
                    } else {
                        System.out.print("\t" + author.getBookList().get(i).getTitle() + ", \n");
                    }
                }
            } else {
                System.out.print("\nBook: " + author.getBookList().getFirst().getTitle());
            }


            System.out.println();
            System.out.println("-".repeat(20));
        }
    }

    public static void editBook(Library lib, String editIsbn, String newTitle, int newEdition, String newCopyright) {
        Book currentBook = lib.getBook(editIsbn);
        Book newBook = new Book(editIsbn, newTitle, newEdition, newCopyright);

        if (newBook.getTitle().isEmpty()) {
            newBook.setTitle(currentBook.getTitle());
        }

        if (newBook.getEditionNumber() == -1) {
            newBook.setEditionNumber(currentBook.getEditionNumber());
        }

        if (newBook.getCopyright().isEmpty()) {
            newBook.setCopyright(currentBook.getCopyright());
        }

        lib.setBook(editIsbn, newBook);
        BookDatabaseManager.updateBook(editIsbn, newBook);
    }

    public static void editAuthor(Library lib, int authorID, String firstName, String lastName) {
        Author currentAuthor = lib.getAuthor(authorID);
        Author newAuthor = new Author(authorID, firstName, lastName);

        if (newAuthor.getFirstName().isEmpty()) {
            newAuthor.setFirstName(currentAuthor.getFirstName());
        }

        if (newAuthor.getLastName().isEmpty()) {
            newAuthor.setLastName(currentAuthor.getLastName());
        }

        lib.setAuthor(authorID, newAuthor);
        BookDatabaseManager.updateAuthor(authorID, newAuthor);
    }

    public static void addNewBook(Library lib, String newISBN, String newTitle, int newEdition, String newCopyright, List<Author> authorList) {
        Book newBook = new Book(newISBN, newTitle, newEdition, newCopyright);
        for (Author author : authorList) {
            newBook.addAuthor(author);
        }

        BookDatabaseManager.createBook(newBook);
    }

    public static Author getNewBookAuthor(Library lib, List<Author> newAuthors) {
        System.out.println("Creating new Author");
        System.out.println("Enter AuthorID: ");
        int authorID = Integer.parseInt((new Scanner(System.in)).nextLine());

        System.out.println("Enter Author First Name: ");
        String firstName = (new Scanner(System.in)).nextLine();

        System.out.println("Enter Author Last Name: ");
        String lastName = (new Scanner(System.in)).nextLine();

        Author newAuthor = new Author(authorID, firstName, lastName);
        lib.addAuthor(newAuthor);
        return newAuthor;
    }

    public static Author getExistingBookAuthors(Library lib, List<Author> existingAuthors) {
        for (Author author : lib.getAuthorList()) {
            System.out.print("AuthorID: " + author.getAuthorID() + " ");
            System.out.println("Name: " + author.getFirstName() + " " + author.getLastName());

        }
        System.out.println("Enter AuthorID: ");
        int choice = Integer.parseInt((new Scanner(System.in)).nextLine());

        return lib.getAuthor(choice);
    }


    public static void main(String[] args) {
        Library lib = new Library();
        BookDatabaseManager.loadLibrary(lib);

        printOptions();
        System.out.println("Choose an option: ");
        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        while (!Objects.equals(choice, "0")) {

            if (Objects.equals(choice, "0")) {
                System.out.println("Exiting...");
                break;
            } else if (Objects.equals(choice, "1")) {
                showAllBooks(lib);

            } else if (Objects.equals(choice, "2")) {
                showAllAuthors(lib);
            } else if (Objects.equals(choice, "3")) {
                System.out.println("Enter ISBN of book to edit: ");
                String editISBN = scanner.nextLine();

                System.out.println("Enter new title(or blank to keep original title): ");
                String newTitle = scanner.nextLine();

                System.out.println("Enter new edition number( or -1 to keep original edition number): ");
                int newEditionNumber = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter new copyright(or blank to keep original copyright): ");
                String newCopyright = scanner.nextLine();

                editBook(lib, editISBN, newTitle, newEditionNumber, newCopyright);
            } else if (Objects.equals(choice, "4")) {
                System.out.println("Enter Author ID of author to edit: ");
                int editAuthorID = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter new first name(or blank to keep original name): ");
                String newFirstName = scanner.nextLine();

                System.out.println("Enter new last name( or blank to keep original name): ");
                String newLastName = scanner.nextLine();

                editAuthor(lib, editAuthorID, newFirstName, newLastName);
            } else if (Objects.equals(choice, "5")) {
                System.out.println("Enter ISBN of book to edit: ");
                String newISBN = scanner.nextLine();

                System.out.println("Enter new title: ");
                String newTitle = scanner.nextLine();

                System.out.println("Enter new edition number: ");
                int newEditionNumber = Integer.parseInt(scanner.nextLine());

                System.out.println("Enter new copyright: ");
                String newCopyright = scanner.nextLine();

                List<Author> newAuthorList = new ArrayList<>();
                Author currentAuthor;
                System.out.println("=".repeat(20));
                System.out.println("0. Exit");
                System.out.println("1. Add existing author");
                System.out.println("2. Add new author");
                System.out.println("Choose an option: ");
                System.out.println("=".repeat(20));
                String authorChoice = scanner.nextLine();


                while(!Objects.equals(authorChoice, "0")) {
                    if (Objects.equals(authorChoice, "1")) {
                        currentAuthor = getExistingBookAuthors(lib, newAuthorList);
                    } else {
                        currentAuthor = getNewBookAuthor(lib, newAuthorList);
                    }
                    newAuthorList.add(currentAuthor);

                    System.out.println("=".repeat(20));
                    System.out.println("0. Exit");
                    System.out.println("1. Add existing author");
                    System.out.println("2. Add new author");
                    System.out.println("=".repeat(20));
                    System.out.println("Choose an option: ");
                    authorChoice = scanner.nextLine();
                }

                addNewBook(lib, newISBN, newTitle, newEditionNumber, newCopyright, newAuthorList);
            } else {
                System.out.println("Invalid choice");
            }

            printOptions();
            System.out.println("Choose an option: ");
            choice = scanner.nextLine();
        }







//        // Show all books and their authors
//        for (Book book : lib.getBookList()) {
//            System.out.println(book.getTitle());
//            for (Author author : book.getAuthorList()) {
//                System.out.println(author.getFirstName() + " " + author.getLastName());
//            }
//            System.out.println("-".repeat(20));
//        }
//
//        System.out.println("0".repeat(20));
//
//        // Show all authors and their books
//        for (Author author : lib.getAuthorList()) {
//            System.out.println(author.getFirstName() + " " + author.getLastName());
//            for (Book book : author.getBookList()) {
//                System.out.println(book.getTitle());
//            }
//            System.out.println("-".repeat(20));
//        }
//
////        System.out.println(lib.getAuthorList());
////        System.out.println(lib.getBookList());
//
////        Book book = new Book("JJJJ", "JJJJJJJJJ", 89, "31XX");
////        BookDatabaseManager.createBook(book);
//
////        Author author = new Author(133, "John", "Smith");
////        BookDatabaseManager.createAuthor(author);
//
////        System.out.println(BookDatabaseManager.getBook("ZZZZ").getTitle());
////        System.out.println(BookDatabaseManager.getAllBooks());
//
////        System.out.println(BookDatabaseManager.getAuthor("5").getFirstName() +" " + BookDatabaseManager.getAuthor("5").getLastName());
////        System.out.println(BookDatabaseManager.getAllAuthors());
//
////        BookDatabaseManager.updateBook("GGGG", new Book("GGGG", "This is an update", 20, "XXXX"));
////        BookDatabaseManager.updateAuthor("133", new Author(133, "Mark", "Danielewski"));
////        BookDatabaseManager.deleteBook("JJJJ");
////        BookDatabaseManager.deleteAuthor("123");
    }
}
