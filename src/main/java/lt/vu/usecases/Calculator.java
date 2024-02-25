package lt.vu.usecases;


import lt.vu.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Default;
import java.util.concurrent.ExecutionException;

@RequestScoped
@Default
public class Calculator {


    private LongCalculator longCalculationComponent;

    public String countBooks(User user) throws ExecutionException, InterruptedException {
        String statistic= user.getEmail()+" yra peimes knygu- "+user.getBooks_history().size()+" iš kurių negražino "+user.getTaken_books().size()+"\n SKOLA :"+(user.getBooks_history().size()-user.getTaken_books().size());
        return statistic;
    }
}