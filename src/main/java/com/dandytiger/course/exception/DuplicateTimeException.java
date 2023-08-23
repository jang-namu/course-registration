package com.dandytiger.course.exception;

public class DuplicateTimeException extends RuntimeException{
    public DuplicateTimeException() {
        super();
    }

    public DuplicateTimeException(String message) {
        super(message);
    }

    public DuplicateTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateTimeException(Throwable cause) {
        super(cause);
    }
}
