package ru.ed.microlib.context.listener;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import ru.ed.microlib.command.Command;
import ru.ed.microlib.exception.InjectCommandsListenerException;
import ru.ed.microlib.storage.CommandStorage;
import ru.ed.microlib.storage.CommandStorageImpl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
public class InjectCommandsApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final String FIELD_NAME = "commandStorage";

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        CommandStorage commandStorage = applicationContext.getBean(CommandStorage.class);

        if(!(commandStorage instanceof CommandStorageImpl)){
            // todo: Добавить логирование
            return;
        }

        Class<? extends CommandStorage> commandStorageClass = commandStorage.getClass();

        Map<String, Command> commandMap = getCommandMap(applicationContext);

        try {
            Field commandStorageField = commandStorageClass.getDeclaredField(FIELD_NAME);
            commandStorageField.setAccessible(true);
            commandStorageField.set(commandStorage, commandMap);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            // todo: Добавить логирование
            throw new InjectCommandsListenerException("Error inject commands in command storage", e);
        }
    }

    private Map<String, Command> getCommandMap(ApplicationContext applicationContext){
        Map<String, Command> commandMap = new HashMap<>();

        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefName : beanDefinitionNames){
            Object bean = applicationContext.getBean(beanDefName);
            if(bean instanceof Command){
                Command command = (Command) bean;
                commandMap.put(command.getName(), command);
            }
        }

        return commandMap;
    }
}
