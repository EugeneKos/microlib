package ru.ed.microlib.context;

import ru.ed.microlib.handle.CommandHandler;
import ru.ed.microlib.handle.CommandHandlerImpl;
import ru.ed.microlib.parser.CommandParser;
import ru.ed.microlib.parser.CommandParserImpl;
import ru.ed.microlib.provider.CommandProvider;
import ru.ed.microlib.provider.DefaultCommandProvider;
import ru.ed.microlib.starter.CliManagerStarter;
import ru.ed.microlib.starter.CliManagerStarterImpl;
import ru.ed.microlib.storage.CommandStorage;
import ru.ed.microlib.storage.CommandStorageImpl;
import ru.ed.microlib.validate.CommandValidator;
import ru.ed.microlib.validate.CommandValidatorImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CliManagerContext {
    @Bean
    public CommandParser commandParser(){
        return new CommandParserImpl();
    }

    @Bean
    public CommandProvider commandProvider(){
        return new DefaultCommandProvider();
    }

    @Bean
    public CommandStorage commandStorage(CommandProvider commandProvider){
        return new CommandStorageImpl(commandProvider);
    }

    @Bean
    public CommandValidator commandValidator(CommandStorage commandStorage){
        return new CommandValidatorImpl(commandStorage);
    }

    @Bean
    public CommandHandler commandHandler(CommandParser commandParser, CommandValidator commandValidator){
        return new CommandHandlerImpl(commandParser, commandValidator);
    }

    @Bean
    public CliManagerStarter cliManagerStarter(CommandHandler commandHandler){
        return new CliManagerStarterImpl(commandHandler);
    }
}
