package ru.ed.microlib;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.ed.microlib.context.CliManagerApplicationContext;
import ru.ed.microlib.starter.CliManagerStarter;

public class CliManagerApp {
    public static void cliStart(){
        cliStart(new AnnotationConfigApplicationContext(CliManagerApplicationContext.class));
    }

    public static void cliStart(String springContextXmlFileName){
        cliStart(new ClassPathXmlApplicationContext(springContextXmlFileName));
    }

    public static void cliStart(Class<?> springContextClass){
        cliStart(new AnnotationConfigApplicationContext(springContextClass));
    }

    private static void cliStart(ApplicationContext applicationContext){
        CliManagerStarter cliManagerStarter = applicationContext.getBean(CliManagerStarter.class);
        cliManagerStarter.start();
    }
}