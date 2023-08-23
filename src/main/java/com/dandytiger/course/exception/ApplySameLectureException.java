package com.dandytiger.course.exception;

public class ApplySameLectureException extends RuntimeException{
    public ApplySameLectureException() {
        super();
    }

    public ApplySameLectureException(String message) {
        super(message);
    }

    public ApplySameLectureException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplySameLectureException(Throwable cause) {
        super(cause);
    }

}
