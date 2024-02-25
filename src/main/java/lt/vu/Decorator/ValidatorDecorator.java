package lt.vu.Decorator;

import lt.vu.rest.contracts.BookDto;
import lt.vu.usecases.IValidator;

import javax.decorator.Decorator;
import javax.decorator.Delegate;
import javax.inject.Inject;

@Decorator
public class ValidatorDecorator implements IValidator {

    @Inject
    @Delegate
    private IValidator validator;

    @Override
    public boolean validate(BookDto bookDto) {
        try {
            System.out.println("ValidatorDecorator");
            bookDto.setAuthor(bookDto.getAuthor().toLowerCase());
            bookDto.setTitle(bookDto.getTitle().toLowerCase());
            validator.validate(bookDto);
            return true;
        } catch (Exception ex) {
            System.out.println("Exception in Decorator " + ex.getMessage());
        }
        return false;
    }
}