package com.dandytiger.course.exception;

public class OverlapsLectureTimeException extends RuntimeException{
    public OverlapsLectureTimeException() {
        super();
    }

    public OverlapsLectureTimeException(String message) {
        super(message);
    }

    public OverlapsLectureTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public OverlapsLectureTimeException(Throwable cause) {
        super(cause);
    }
}
