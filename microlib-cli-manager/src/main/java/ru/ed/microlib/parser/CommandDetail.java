package ru.ed.microlib.parser;

import java.util.Map;

public class CommandDetail {
    private String commandName;
    private Map<String, String> commandArguments;

    public CommandDetail(String commandName, Map<String, String> commandArguments) {
        this.commandName = commandName;
        this.commandArguments = commandArguments;
    }

    public String getCommandName() {
        return commandName;
    }

    public Map<String, String> getCommandArguments() {
        return commandArguments;
    }
}
