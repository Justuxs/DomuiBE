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
@Priority(2)
public class WeakValidator implements Serializable, IValidator {
    public WeakValidator() {}
    @Override
    public boolean validate(BookDto bookDto){
        return (!bookDto.getISBN().isEmpty() && !bookDto.getAuthor().isEmpty() && !bookDto.getTitle().isEmpty());
    }
}
