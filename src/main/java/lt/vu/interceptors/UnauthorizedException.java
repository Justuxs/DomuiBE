package lt.vu.interceptors;


public class UnauthorizedException
        extends RuntimeException {
    public UnauthorizedException() {
        super("Unauthorized");
    }
}