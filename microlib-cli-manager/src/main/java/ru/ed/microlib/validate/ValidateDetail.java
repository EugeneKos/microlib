package ru.ed.microlib.validate;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;

import java.util.Map;

public class ValidateDetail {
    private Command command;
    private Map<Argument, String> commandArguments;

    public ValidateDetail(Command command, Map<Argument, String> commandArguments) {
        this.command = command;
        this.commandArguments = commandArguments;
    }

    public Command getCommand() {
        return command;
    }

    public Map<Argument, String> getCommandArguments() {
        return commandArguments;
    }
}
