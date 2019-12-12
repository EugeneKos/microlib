package ru.ed.microlib.provider;

import ru.ed.microlib.command.Command;

import java.util.HashSet;
import java.util.Set;

public class DefaultCommandProvider implements CommandProvider {
    @Override
    public Set<Command> getCommands() {
        return new HashSet<>();
    }
}
