package lt.vu.usecases;

import lt.vu.entities.Book;
import lt.vu.persistence.BooksDAO;

import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Stateless
public class LongCalculator {

    @Asynchronous
    public void performLongCalculation(BooksDAO booksDAO, LongCalculationCallback callback) throws ExecutionException, InterruptedException {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        List<lt.vu.entities.Book> books= booksDAO.loadAll();
        int paiimtu = 0;
        for (Book book: books
             ) {
            if(book.getBorrower()!= null){
                paiimtu+=1;
            }
        }
        callback.onComplete(new AsyncResult<>(paiimtu));
    }

    public interface LongCalculationCallback {
        void onComplete(AsyncResult<Integer> result) throws ExecutionException, InterruptedException;
    }
}