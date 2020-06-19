package ru.ed.microlib.starter;

import ru.ed.microlib.exception.CommandHandleException;
import ru.ed.microlib.handle.CommandHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CliManagerStarterImpl implements CliManagerStarter {
    private static final String GREETING = "cli-manager-app-v.1.0";
    private static final String EXIT_CMD = "exit";
    private static final String EMPTY_CMD = "";
    private static final String BRACKET = ">";

    private CommandHandler commandHandler;

    public CliManagerStarterImpl(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void start() {
        System.out.println(GREETING);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(BRACKET);
            String line;
            while (!EXIT_CMD.equals(line = bufferedReader.readLine())){
                if(!EMPTY_CMD.equals(line)){
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
}
