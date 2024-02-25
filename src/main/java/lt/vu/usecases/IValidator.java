package lt.vu.usecases;

import lt.vu.rest.contracts.BookDto;

public interface IValidator {
    public default boolean validate(BookDto bookDto) {
        return false;
    }
}
