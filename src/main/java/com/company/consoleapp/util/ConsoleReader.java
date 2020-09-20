package com.company.consoleapp.util;

import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleReader implements Reader {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String readString() {
        return scanner.next();
    }

    @Override
    public int readInt() {
        return scanner.nextInt();
    }
}
