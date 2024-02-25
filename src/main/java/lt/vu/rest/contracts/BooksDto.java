package lt.vu.rest.contracts;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class BooksDto {
    private int id;
    private String author;

    private String title;

    private int quantity;

    public BooksDto(int id,String author, String title, int quantity) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.quantity = quantity;
    }


}
