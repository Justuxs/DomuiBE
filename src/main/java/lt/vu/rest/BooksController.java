package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Book;
import lt.vu.persistence.BooksDAO;
import lt.vu.rest.contracts.BookDto;
import lt.vu.usecases.AdminCalculator;
import lt.vu.usecases.IValidator;
import lt.vu.usecases.StrongValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/books")
public class BooksController {


    @Inject
    @Setter @Getter
    private BooksDAO booksDAO;

    @Inject
    @Setter @Getter
    private IValidator strongValidator;

    @Inject
    @Setter @Getter
    private AdminCalculator calculator;

    @Path("/donate")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createBook(BookDto book) {
        if(!strongValidator.validate(book))
            return Response.status(409).entity("Book props are bad").build();
        Book book_= book.toBook();

        try {
            booksDAO.persist(book_);
            return Response.status(201).entity("Book created successfully").build();

        }
        catch (Exception exception){
            return Response.status(409).entity("Book ISBN IS bad").build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Path("/{isbn}")
    public Response editBook(@PathParam("isbn") final String isbn, BookDto book) {
        if(!strongValidator.validate(book))
            return Response.status(409).entity("Book props are bad").build();


        try {
            Book book_true = booksDAO.findOne(isbn);
            book_true.setAuthor(book.getAuthor());
            book_true.setTitle(book.getTitle());

            booksDAO.update(book_true);
            return Response.status(201).entity("Book updated successfully").build();

        }
        catch (Exception exception){
            return Response.status(409).entity("Book ISBN IS bad").build();
        }
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() throws ExecutionException, InterruptedException {
        List<Book> books = booksDAO.loadAll();
        if (books == null || books.size() ==0) {
            return Response.ok(null).build();
        }
        return Response.ok(BookDto.fromBooks(books)).build();
    }

    @GET
    @Path("/gruped")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGrupedBooks() throws ExecutionException, InterruptedException {
        List<lt.vu.rest.contracts.BooksDto> books=booksDAO.loadAllgrouped();
        if(books==null || books.size()==0)Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(books).build();
    }



}
