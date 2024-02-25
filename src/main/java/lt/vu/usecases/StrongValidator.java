package lt.vu.usecases;

import lt.vu.rest.contracts.BookDto;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestScoped
@Alternative
@Priority(1)
public class StrongValidator implements Serializable, IValidator {
    public StrongValidator() {}
    private static final String isbn_regex  = "^(97(8|9))?\\d{9}(\\d|X)$";
    @Override
    public boolean validate(BookDto bookDto){
        Pattern pattern= Pattern.compile(isbn_regex);
        Matcher matcher = pattern.matcher(bookDto.getISBN());
        return (matcher.matches() && !bookDto.getAuthor().isEmpty() && !bookDto.getTitle().isEmpty());
    }
}
