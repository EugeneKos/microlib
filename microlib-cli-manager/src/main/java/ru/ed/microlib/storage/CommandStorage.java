package ru.ed.microlib.storage;

import ru.ed.microlib.command.Command;

import java.util.Collection;

public interface CommandStorage {
    Command getCommand(String commandName);
    Collection<Command> getAllCommand();
}
