package ru.ed.microlib.starter;

import ru.ed.microlib.command.Argument;
import ru.ed.microlib.command.Command;
import ru.ed.microlib.exception.CommandHandleException;
import ru.ed.microlib.handle.CommandHandler;
import ru.ed.microlib.storage.CommandStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class CliManagerStarterImpl implements CliManagerStarter {
    private static final String EXIT_CMD = "exit";
    private static final String PRINT_ALL_COMMAND_CMD = "*";
    private static final String EMPTY_CMD = "";
    private static final String BRACKET = ">";

    private CommandHandler commandHandler;
    private CommandStorage commandStorage;

    public CliManagerStarterImpl(CommandHandler commandHandler, CommandStorage commandStorage) {
        this.commandHandler = commandHandler;
        this.commandStorage = commandStorage;
    }

    @Override
    public void start(String greeting) {
        System.out.println(greeting);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(BRACKET);
            String line;
            while (!EXIT_CMD.equals(line = bufferedReader.readLine())){
                if(PRINT_ALL_COMMAND_CMD.equals(line)){
                    printAllCommand(commandStorage.getAllCommand());
                }
                else if(!EMPTY_CMD.equals(line)){
                    try {
                        commandHandler.handle(line);
                    } catch (CommandHandleException e) {
                        printError(e);
                    }
                }
                System.out.print(BRACKET);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printError(Throwable throwable){
        System.out.println("Error: " + throwable.getMessage());
        Throwable cause = throwable.getCause();
        if(cause == null){
            return;
        }
        printError(cause);
    }

    private void printAllCommand(Collection<Command> commands){
        for (Command command : commands){
            System.out.println(String.format(
                    "%s [%s] [%s]", command.getName(), command.getDescription(), getArgumentsStrForPrint(command.getArguments())
            ));
        }
    }

    private String getArgumentsStrForPrint(Argument[] arguments){
        StringBuilder builder = new StringBuilder();

        if(arguments == null || arguments.length == 0){
            return builder.toString();
        }

        for (Argument argument : arguments){
            builder.append("{").append(argument.getShortKey()).append(" ")
                    .append(argument.getLongKey()).append(" ")
                    .append(argument.isMandatory()).append("}, ");
        }

        return builder.substring(0, builder.length() - 2);
    }
}
