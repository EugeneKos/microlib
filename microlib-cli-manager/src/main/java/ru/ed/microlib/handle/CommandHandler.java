package ru.ed.microlib.handle;

import ru.ed.microlib.exception.CommandHandleException;

public interface CommandHandler {
    void handle(String command) throws CommandHandleException;
}
