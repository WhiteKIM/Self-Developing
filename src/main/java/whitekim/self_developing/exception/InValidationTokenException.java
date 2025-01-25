package whitekim.self_developing.exception;

public class InValidationTokenException extends RuntimeException {
    public InValidationTokenException() {
        super();
    }

    public InValidationTokenException(String message) {
        super(message);
    }
}
