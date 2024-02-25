package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Book;
import lt.vu.entities.User;
import lt.vu.interceptors.Authorized;
import lt.vu.persistence.BooksDAO;
import lt.vu.persistence.UsersDAO;
import lt.vu.rest.contracts.BookDto;
import lt.vu.usecases.Calculator;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.util.concurrent.ExecutionException;

@SessionScoped
@Path("/users")
public class UsersController implements Serializable {


    @Inject
    @Setter @Getter
    private UsersDAO usersDAO;

    @Inject
    @Setter @Getter
    private BooksDAO booksDAO;

    private User Current_user;
    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createUser(User user) {

        System.out.println(user.toString());
        User tem=usersDAO.findOne(user.getEmail());
        if(tem!=null) {
            return Response.status(409).entity("User existing").build();
        } else {
            usersDAO.persist(user);
        }

        return Response.status(201).entity("User created successfully").build();
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(User user) {
        User tem=usersDAO.findOne(user.getEmail());
        if(tem!=null && tem.equals(user)) {
            System.out.println("Useris prisjungia");
            Current_user=user;
            return Response.status(200).entity("ok").build();
        } else {
            return Response.status(401).entity("Unauthorized ").build();

        }
    }

    @Path("/take/{id}")
    @POST
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized
    public Response takeBook(@PathParam("id") final Integer id) throws InterruptedException {
        //if(Current_user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        Current_user = usersDAO.findOne(Current_user.getEmail());
        Book book = booksDAO.loadByhash(id);

        if(Current_user == null || book == null ) return Response.status(Response.Status.CONFLICT).build();
        if(Current_user.getEmail().toLowerCase().equals("justas@gmail.com")){
            System.out.println("Justas@gmail.com");
            Thread.sleep(5000);
        }
        book.takeByUser(Current_user);
        Current_user.addBook(book);
        Current_user=usersDAO.update(Current_user);
        booksDAO.update(book);
        System.out.println("Knyga paimta "+" "+id+" "+Current_user.getEmail());
        return Response.ok(book.getISBN()).build();
    }


    @Path("/return/{isbn}")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Authorized

    public Response returnBook(@PathParam("isbn") final String isbn) {
        //if(Current_user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        Current_user = usersDAO.findOne(Current_user.getEmail());
        if(Current_user == null ) return Response.status(Response.Status.CONFLICT).build();

        Book book = booksDAO.findOne(isbn);

        if( book==null || !Current_user.equals(book.getBorrower())) return Response.status(Response.Status.CONFLICT).build();

        book.setBorrower(null);
        Current_user.removeBook(book);

        Current_user=usersDAO.update(Current_user);
        booksDAO.update(book);

        System.out.println("Knyga pasalinta ");
        return Response.ok(book.getISBN()).build();
    }

    @Path("/taken")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Authorized

    public Response getTakenbooks() {
        //if(Current_user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        Current_user = usersDAO.findOne(Current_user.getEmail());
        if(Current_user == null ) return Response.status(Response.Status.CONFLICT).build();
        return Response.ok(BookDto.fromBooks(Current_user.getTaken_books())).build();
    }
    @Path("/history")
    @GET
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Authorized

    public Response getHistory() {
        //if(Current_user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        Current_user = usersDAO.findOne(Current_user.getEmail());
        if(Current_user == null ) return Response.status(Response.Status.CONFLICT).build();
        return Response.ok(BookDto.fromBooks(Current_user.getBooks_history())).build();
    }

    @Inject
    @Setter @Getter
    private Calculator calculator;


    @Path("/statistic")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Authorized

    public Response statistic() throws ExecutionException, InterruptedException {
        //if(Current_user == null) return Response.status(Response.Status.UNAUTHORIZED).build();
        Current_user = usersDAO.findOne(Current_user.getEmail());
        String res;
        if(Current_user.getEmail().toLowerCase().equals("justas@gmail.com")){
            res=calculator.countBooks(Current_user);
        } else {
            res=calculator.countBooks(Current_user);
        }
        return Response.ok(res).build();
    }

    public boolean isLogged() {
        return Current_user!=null;
    }
}
