package team01.studyCm.chat.exception;

public class UnauthorizedAccessException  extends RuntimeException {

    public UnauthorizedAccessException() {
        super();
    }

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}

