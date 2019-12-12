package ru.ed.microlib.parser;

import ru.ed.microlib.exception.CommandParseException;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommandParserImpl implements CommandParser {
    private static final String COMMAND_REGEXP = "([a-z]+)\\s*(.*)";
    private static final String ARGUMENTS_REGEXP = "(-[a-z]|--[a-z]+)\\s+(\\S+)";

    @Override
    public CommandDetail parse(String command) throws CommandParseException {
        CommandKeys commandKeys = parseItem(COMMAND_REGEXP, command, this::getCommandAndKeys);
        if(commandKeys == null){
            throw new CommandParseException("Impossible to parse: " + command);
        }

        String commandName = commandKeys.getCommand();
        String commandArgs = commandKeys.getKeysAndArguments();

        List<KeyArgument> keyArguments = parseItem(
                ARGUMENTS_REGEXP, commandArgs, this::getKeysAndArguments
        );

        return getDetail(commandName, keyArguments);
    }

    private CommandDetail getDetail(String commandName, List<KeyArgument> keyArguments){
        Map<String, String> arguments = new HashMap<>();
        for (KeyArgument keyArgument : keyArguments){
            arguments.put(keyArgument.getKey(), keyArgument.getArgument());
        }
        return new CommandDetail(commandName, arguments);
    }

    private <R> R parseItem(String regexp, String item, Function<Matcher, R> function){
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(item);
        return function.apply(matcher);
    }

    private CommandKeys getCommandAndKeys(Matcher matcher){
        if(matcher.find()){
            return new CommandKeys(matcher.group(1), matcher.group(2));
        }
        return null;
    }

    private List<KeyArgument> getKeysAndArguments(Matcher matcher){
        List<KeyArgument> keyArguments = new LinkedList<>();
        while (matcher.find()){
            keyArguments.add(new KeyArgument(matcher.group(1), matcher.group(2)));
        }
        return keyArguments;
    }

    private class CommandKeys{
        private String command;
        private String keysAndArguments;

        CommandKeys(String command, String keysAndArguments) {
            this.command = command;
            this.keysAndArguments = keysAndArguments;
        }

        String getCommand() {
            return command;
        }

        String getKeysAndArguments() {
            return keysAndArguments;
        }
    }

    private class KeyArgument{
        private String key;
        private String argument;

        KeyArgument(String key, String argument) {
            this.key = key;
            this.argument = argument;
        }

        String getKey() {
            return key;
        }

        String getArgument() {
            return argument;
        }
    }
}
