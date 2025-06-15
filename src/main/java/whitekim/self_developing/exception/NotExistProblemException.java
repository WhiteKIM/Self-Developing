package whitekim.self_developing.exception;

/**
 * 존재하지 않는 문제 접근 시 발생
 */
public class NotExistProblemException extends RuntimeException {
    public NotExistProblemException() {
        super();
    }

    public NotExistProblemException(String message) {
        super(message);
    }
}
