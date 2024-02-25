package lt.vu.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Entity
@Getter
@Setter
@Table(name = "USER")
public class User {
    @Column(name = "NAME")
    private String name;
    @Column(name = "SURNAME")
    private String surname;
    @Id
    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @OneToMany(mappedBy = "borrower")
    private List<Book> taken_books = new ArrayList<>();

    @ManyToMany(mappedBy = "borrowers_history")
    private List<Book> books_history = new ArrayList<>();

    public User(String name, String email,String surname, String password) {
        this.name = name;
        this.email = email;
        this.surname = surname;
        this.password = password;
    }

    public User(String name, String surname, String email, String password, List<Book> taken_books) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.taken_books = taken_books;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return (Objects.equals(email, user.email) && Objects.equals(password, user.password));
    }

    @Override
    public int hashCode() {

        return Objects.hash(email);
    }

    public void addBook(Book book){
        taken_books.add(book);
        books_history.add(book);
    }
    public void removeBook(Book book){
        taken_books.remove(book);
    }
}
