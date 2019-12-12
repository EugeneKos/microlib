package ru.ed.microlib.exception;

public class CommandHandleException extends Exception {
    private static final long serialVersionUID = 5164775107018414897L;

    public CommandHandleException(String message, Throwable cause) {
        super(message, cause);
    }
}
