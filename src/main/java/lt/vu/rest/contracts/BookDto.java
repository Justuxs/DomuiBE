package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Book;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BookDto {

    private String ISBN;

    private String author;

    private String title;

    public BookDto(String ISBN, String author, String title) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
    }

    public BookDto() {
    }

    private static final String isbn_regex  = "^(97(8|9))?\\d{9}(\\d|X)$";

    public static List<BookDto> fromBooks(List<Book> books) {
        List<BookDto> books_= new ArrayList<>();
        for (Book book_:books
             ) {
            books_.add(fromBook(book_));
        }
        return books_;
    }

    public static BookDto fromBook(Book book) {
        return new BookDto(book.getISBN(),book.getTitle(),book.getAuthor());
    }


    @Override
    public String toString() {
        return "BookDto{" +
                "ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public Book toBook(){
        return new Book(ISBN,author,title);
    }
}
