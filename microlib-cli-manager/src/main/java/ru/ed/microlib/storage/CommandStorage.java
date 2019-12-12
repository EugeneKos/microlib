package ru.ed.microlib.storage;

import ru.ed.microlib.command.Command;

public interface CommandStorage {
    Command getCommand(String commandName);
}
