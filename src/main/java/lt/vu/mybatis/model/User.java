package lt.vu.mybatis.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class User {
    private String email;

    private String name;

    private String password;

    private String surname;

    private List<Book> history;

}