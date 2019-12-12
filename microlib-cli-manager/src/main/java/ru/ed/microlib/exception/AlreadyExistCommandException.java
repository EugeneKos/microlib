package ru.ed.microlib.exception;

public class AlreadyExistCommandException extends RuntimeException {
    private static final long serialVersionUID = 9129416862300252593L;

    public AlreadyExistCommandException(String message) {
        super(message);
    }
}
