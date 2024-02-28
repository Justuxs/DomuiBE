package lt.vu.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class Book {

    private String isbn;
    private String author;
    private String title;
    private User borrower;

    private List<User> borrowers_history = new ArrayList<>();

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", borrower=" + borrower +
                '}';
    }
}