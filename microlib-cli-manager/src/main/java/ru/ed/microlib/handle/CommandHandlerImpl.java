package ru.ed.microlib.handle;

import ru.ed.microlib.command.Command;
import ru.ed.microlib.exception.CommandHandleException;
import ru.ed.microlib.exception.CommandParseException;
import ru.ed.microlib.exception.CommandValidateException;
import ru.ed.microlib.parser.CommandDetail;
import ru.ed.microlib.parser.CommandParser;
import ru.ed.microlib.validate.CommandValidator;
import ru.ed.microlib.validate.ValidateDetail;

public class CommandHandlerImpl implements CommandHandler {
    private CommandParser commandParser;
    private CommandValidator commandValidator;

    public CommandHandlerImpl(CommandParser commandParser, CommandValidator commandValidator) {
        this.commandParser = commandParser;
        this.commandValidator = commandValidator;
    }

    @Override
    public void handle(String commandText) throws CommandHandleException {
        try {
            CommandDetail commandDetail = commandParser.parse(commandText);
            ValidateDetail validate = commandValidator.validate(commandDetail);
            Command command = validate.getCommand();
            command.perform(validate.getCommandArguments());
        } catch (CommandValidateException | CommandParseException e) {
            throw new CommandHandleException("Command handle error", e);
        }
    }
}
