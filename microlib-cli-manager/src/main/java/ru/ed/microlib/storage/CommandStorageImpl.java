package ru.ed.microlib.storage;

import ru.ed.microlib.command.Command;

import ru.ed.microlib.exception.AlreadyExistCommandException;
import ru.ed.microlib.provider.CommandProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CommandStorageImpl implements CommandStorage {
    private CommandProvider commandProvider;

    private Map<String, Command> commandStorage;

    @Autowired
    public CommandStorageImpl(CommandProvider commandProvider) {
        this.commandProvider = commandProvider;
    }

    @PostConstruct
    private void initialize(){
        Set<Command> commands = commandProvider.getCommands();
        commandStorage = new HashMap<>(commands.size());
        for (Command command : commands){
            if(commandStorage.get(command.getName()) != null){
                throw new AlreadyExistCommandException("Command " + command.getName() + " already in storage");
            }
            commandStorage.put(command.getName(), command);
        }
    }

    public Command getCommand(String commandName){
        return commandStorage.get(commandName);
    }
}
