package ru.ed.microlib;

import ru.ed.microlib.context.CliManagerContext;
import ru.ed.microlib.starter.CliManagerStarter;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CliManager {
    public static void start(String greeting, ApplicationContext context) {
        if(context == null){
            context = new AnnotationConfigApplicationContext(CliManagerContext.class);
        }
        CliManagerStarter cliManagerStarter = context.getBean(CliManagerStarter.class);
        cliManagerStarter.start(greeting);
    }
}
