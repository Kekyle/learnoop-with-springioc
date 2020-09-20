package com.company.consoleapp.util;

import org.springframework.stereotype.Component;

@Component
public class ConsoleWriter implements Writer {
    @Override
    public void write(String value) {
        System.out.print(value);
    }
}
