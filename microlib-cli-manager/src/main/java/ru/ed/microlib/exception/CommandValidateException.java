package ru.ed.microlib.exception;

public class CommandValidateException extends Exception {
    private static final long serialVersionUID = -5929182395004849960L;

    public CommandValidateException(String message) {
        super(message);
    }
}
