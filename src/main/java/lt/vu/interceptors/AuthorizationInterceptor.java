package lt.vu.interceptors;

import lt.vu.rest.UsersController;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.io.Serializable;

@Interceptor
@Authorized
public class AuthorizationInterceptor implements Serializable {
    @Inject
    private UsersController userContext;

    @AroundInvoke
    public Object authorize(InvocationContext context) throws Exception, UnauthorizedException {
        if (userContext.isLogged()) {
            System.out.println("authorized");
            return context.proceed();

        } else {
            System.out.println("unauthorized");
            throw new UnauthorizedException();
        }
    }
}
