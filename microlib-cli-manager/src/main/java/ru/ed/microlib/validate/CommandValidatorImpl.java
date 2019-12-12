package ru.ed.microlib.validate;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;
import ru.ed.microlib.exception.CommandValidateException;
import ru.ed.microlib.parser.CommandDetail;
import ru.ed.microlib.storage.CommandStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommandValidatorImpl implements CommandValidator {
    private CommandStorage commandStorage;

    @Autowired
    public CommandValidatorImpl(CommandStorage commandStorage) {
        this.commandStorage = commandStorage;
    }

    @Override
    public ValidateDetail validate(CommandDetail commandDetail) throws CommandValidateException {
        String commandName = commandDetail.getCommandName();
        Command command = commandStorage.getCommand(commandName);
        if(command == null){
            throw new CommandValidateException("Command: " + commandName + " not found");
        }
        Map<Argument, String> commandArguments = validateArgument(command, commandDetail);
        return new ValidateDetail(command, commandArguments);
    }

    private Map<Argument, String> validateArgument(Command command, CommandDetail commandDetail) throws CommandValidateException {
        Map<Argument, String> argumentStringMap = new HashMap<>();
        Map<String, String> commandArguments = commandDetail.getCommandArguments();
        Argument[] arguments = command.getArguments();
        for (Argument argument : arguments){
            String keyValue;
            if((keyValue = commandArguments.get(argument.getShortKey())) != null){
                argumentStringMap.put(argument, keyValue);
            } else if((keyValue = commandArguments.get(argument.getLongKey())) != null){
                argumentStringMap.put(argument, keyValue);
            } else if(argument.isMandatory()){
                throw new CommandValidateException(
                        "Argument " + argument.getShortKey() + ", " + argument.getLongKey() + " is required"
                );
            }
        }
        return argumentStringMap;
    }
}
