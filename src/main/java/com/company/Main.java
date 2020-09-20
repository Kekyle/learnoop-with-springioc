package com.company;

import com.company.consoleapp.ConsoleApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(RootConfiguration.class);
        ConsoleApplication consoleApplication = applicationContext.getBean("consoleApplication", ConsoleApplication.class);
        consoleApplication.start();
    }
}
