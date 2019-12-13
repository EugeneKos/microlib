package ru.ed.microlib.starter;

import ru.ed.microlib.exception.CommandHandleException;
import ru.ed.microlib.handle.CommandHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class CliManagerStarterImpl implements CliManagerStarter {
    private static final String EXIT_CMD = "exit";
    private static final String BRACKET = ">";

    private CommandHandler commandHandler;

    @Autowired
    public CliManagerStarterImpl(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public void start(String greeting) {
        System.out.println(greeting);
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print(BRACKET);
            String line;
            while (!EXIT_CMD.equals(line = bufferedReader.readLine())){
                try {
                    commandHandler.handle(line);
                } catch (CommandHandleException e) {
                    printError(e);
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
