package ru.ed.microlib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.ed.microlib.context.CliManagerApplicationContext;
import ru.ed.microlib.starter.CliManagerStarter;

public class CliManagerApp {
    public static void cliStart(String greeting){
        cliStart(new AnnotationConfigApplicationContext(CliManagerApplicationContext.class), greeting);
    }

    public static void cliStart(String springContextXmlFileName, String greeting){
        cliStart(new ClassPathXmlApplicationContext(springContextXmlFileName), greeting);
    }

    public static void cliStart(Class<?> springContextClass, String greeting){
        cliStart(new AnnotationConfigApplicationContext(springContextClass), greeting);
    }

    private static void cliStart(ApplicationContext applicationContext, String greeting){
        CliManagerStarter cliManagerStarter = applicationContext.getBean(CliManagerStarter.class);
        cliManagerStarter.start(greeting);
    }
}