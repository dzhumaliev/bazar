package solutions.isky.gaurangarevolution.domain.global.exceptions;

/**
 * Created by isky on 02.03.2018.
 */

public class NoNetworkException extends RuntimeException{

    public NoNetworkException() {
        super();
    }

    public NoNetworkException(String message) {
        super(message);
    }

    public NoNetworkException(String message, Throwable cause) {
        super(message, cause);
    }

}
