package ru.ed.microlib.command;

import java.util.Map;

public interface Command {
    String getName();
    String getDescription();
    Argument[] getArguments();
    void perform(Map<Argument, String> arguments);
}
