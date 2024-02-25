package lt.vu.rest;

import lt.vu.rest.exceptionHandler.MyExceptionHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class ApiConfiguration extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(
                UsersController.class,
                BooksController.class,
                MyExceptionHandler.class,
                AdminController.class
        ));
    }
}