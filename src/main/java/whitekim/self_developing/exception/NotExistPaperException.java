package whitekim.self_developing.exception;

/**
 * Throw : If User access paper, not exist or unavailable
 */
public class NotExistPaperException extends RuntimeException {
    public NotExistPaperException() {
        super();
    }

    public NotExistPaperException(String message) {
        super(message);
    }
}
