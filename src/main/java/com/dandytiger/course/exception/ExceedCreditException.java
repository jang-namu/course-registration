package com.dandytiger.course.exception;

public class ExceedCreditException extends RuntimeException{
    public ExceedCreditException() {
        super();
    }

    public ExceedCreditException(String message) {
        super(message);
    }

    public ExceedCreditException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceedCreditException(Throwable cause) {
        super(cause);
    }
}
