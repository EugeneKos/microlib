package ru.ed.microlib.parser;

import ru.ed.microlib.exception.CommandParseException;

public interface CommandParser {
    CommandDetail parse(String command) throws CommandParseException;
}
