package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.Book;


import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class BookDto_ {

    private String ISBN;

    private String author;

    private String title;

    public BookDto_(String ISBN, String author, String title) {
        this.ISBN = ISBN;
        this.author = author;
        this.title = title;
    }

    public BookDto_() {
    }


    public static List<BookDto_> fromBooks(List<Book> books) {
        List<BookDto_> books_= new ArrayList<>();
        for (Book book_:books
             ) {
            books_.add(fromBook(book_));
        }
        return books_;
    }

    public static BookDto_ fromBook(Book book) {
        return new BookDto_(book.getIsbn(),book.getTitle(),book.getAuthor());
    }


    @Override
    public String toString() {
        return "BookDto{" +
                "ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                '}';
    }


}
