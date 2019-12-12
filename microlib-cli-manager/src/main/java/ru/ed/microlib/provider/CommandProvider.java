package ru.ed.microlib.provider;

import ru.ed.microlib.command.Command;

import java.util.Set;

public interface CommandProvider {
    Set<Command> getCommands();
}
