package lt.vu.usecases;


import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Book;
import lt.vu.entities.User;
import lt.vu.persistence.BooksDAO;

import javax.ejb.AsyncResult;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RequestScoped
@Specializes
public class AdminCalculator extends Calculator implements LongCalculator.LongCalculationCallback {

    @Inject
    @Setter
    @Getter
    private BooksDAO booksDAO;

    @EJB
    private LongCalculator longCalculationComponent;
    @Override
    public String countBooks(User user) throws ExecutionException, InterruptedException {

        CompletableFuture<Integer> future = new CompletableFuture<>();
        longCalculationComponent.performLongCalculation(booksDAO, new LongCalculator.LongCalculationCallback() {
            @Override
            public void onComplete(AsyncResult<Integer> result) throws ExecutionException, InterruptedException {
                System.out.println("Paimtu knygu: " + result.get());
                future.complete(result.get());
            }
        });
        List<Book> books = booksDAO.loadAll();
        int nepaimtu = 0;
        for (Book book : books) {
            if (book.getBorrower() == null) {
                nepaimtu += 1;
            }
        }
        System.out.println("Nepaimtu knygu: " + nepaimtu);

        Integer result = future.get();
        System.out.println("Result: " + result);
        String statistic = super.countBooks(user)+ "\n Viso paimtu knygu: "+result+"\n Viso nepaimtu knygu :"+nepaimtu;

        return statistic;
    }


    @Override
    public void onComplete(AsyncResult<Integer> result) throws ExecutionException, InterruptedException {
        System.out.println("Paimtu knygu: "+result.get());
    }
}