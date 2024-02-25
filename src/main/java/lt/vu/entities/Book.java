package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Setter
@Table(name = "BOOK")

@NamedQueries({
        @NamedQuery(name = "Book.findAll", query = "select b from Book as b"),
        @NamedQuery(
                name = "Book.findAllNonTaken",
                query = "SELECT b FROM Book b WHERE b.borrower IS NULL"
        ),
        @NamedQuery(name = "Book.findAllbyAuthor", query = "SELECT b FROM Book b WHERE b.author = :author"
        )
})
public class Book {
    @Id
    @Size(max = 50)
    @Column(name = "ISBN")
    private String ISBN;
    @Size(max = 50)
    @Column(name = "TITLE")
    private String title;
    @Size(max = 50)
    @Column(name = "AUTHOR")
    private String author;
    @ManyToOne
    @JoinColumn(name="USER_ID")
    private User borrower;

    @Version
    @Column(name = "OPT_LOCK_VERSION")
    private Integer version;


    @ManyToMany
    @JoinTable(name = "history",
            joinColumns = @JoinColumn(name = "isbn"),
            inverseJoinColumns = @JoinColumn(name = "email"))
    private List<User> borrowers_history = new ArrayList<>();


    public Book(String ISBN, String title, String author) {
        this.ISBN = ISBN;
        this.title = title;
        this.author = author;
    }

    public Book(){
    }

    @Override
    public String toString() {
        return "Book{" +
                "ISBN=" + ISBN +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", borrower=" + borrower +
                ", version=" + version +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return (Objects.equals(ISBN, book.ISBN) && Objects.equals(title, book.title));
    }

    @Override
    public int hashCode() {
        String key= title.toLowerCase().replace(" ","")+"/"+author.toLowerCase().replace(" ","");
        return Objects.hash(key);
    }

    public void takeByUser(User user){
        borrowers_history.add(user);
        borrower = user;
    }

}
