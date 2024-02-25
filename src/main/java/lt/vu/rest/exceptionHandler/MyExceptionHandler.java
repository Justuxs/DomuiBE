package lt.vu.rest.exceptionHandler;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MyExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        if(exception.getMessage().equals("ARJUNA016053: Could not commit transaction.")){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Deja, knyga jau buvo paiimta kito vartotojo")
                    .build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An error occurred: " + exception.getMessage())
                .build();
    }

}
