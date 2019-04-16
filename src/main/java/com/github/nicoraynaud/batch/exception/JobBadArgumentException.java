package com.github.nicoraynaud.batch.exception;

public class JobBadArgumentException extends RuntimeException {
    public JobBadArgumentException() {
    }

    public JobBadArgumentException(String message) {
        super(message);
    }

    public JobBadArgumentException(String message, Throwable cause) {
        super(message, cause);
    }

    public JobBadArgumentException(Throwable cause) {
        super(cause);
    }

    public JobBadArgumentException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
