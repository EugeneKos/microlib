package ru.ed.microlib.storage;

import ru.ed.microlib.command.Command;

import java.util.Collection;
import java.util.Map;

public class CommandStorageImpl implements CommandStorage {
    private Map<String, Command> commandStorage;

    public Command getCommand(String commandName){
        return commandStorage.get(commandName);
    }

    @Override
    public Collection<Command> getAllCommand() {
        return commandStorage.values();
    }
}
