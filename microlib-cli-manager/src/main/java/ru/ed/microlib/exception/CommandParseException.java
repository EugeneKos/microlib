package ru.ed.microlib.exception;

public class CommandParseException extends Exception {
    private static final long serialVersionUID = 7836395895662680322L;

    public CommandParseException(String message) {
        super(message);
    }
}
