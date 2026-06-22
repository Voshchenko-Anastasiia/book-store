package com.epam.rd.autocode.spring.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BookStoreApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(BookStoreApplication.class, args);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}