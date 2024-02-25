package lt.vu.rest;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.dao.BookMapper;
import lt.vu.mybatis.dao.UserMapper;
import lt.vu.mybatis.model.Book;
import lt.vu.rest.contracts.BookDto_;
import lt.vu.rest.contracts.UserDto;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;

@SessionScoped
@Path("/admin")
public class AdminController implements Serializable {


    private boolean access = false;


    @Inject
    @Setter @Getter
    private BookMapper bookMapper;
    @Inject
    @Setter @Getter
    private UserMapper userMapper;

    @Path("/history/{isbn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistory(@PathParam("isbn") final String isbn) {

        return Response.ok(bookMapper.selectHistoryForBook(isbn)).build();

    }
    @Path("/books")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks() {

        return Response.ok(BookDto_.fromBooks(bookMapper.selectAll())).build();
    }

    @Path("/info/{isbn}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookBorrower(@PathParam("isbn") final String isbn) {
        Book book= bookMapper.selectByPrimaryKey(isbn);
        if(book == null )return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok(UserDto.toUserDto(book.getBorrower())).build();
    }

    @Path("/userHistory/{email}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserHistory(@PathParam("email") final String email) {
        java.util.List<Book> books= userMapper.selectHistoryForUser(email);

        if(books == null)return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(BookDto_.fromBooks(books)).build();
    }



}
