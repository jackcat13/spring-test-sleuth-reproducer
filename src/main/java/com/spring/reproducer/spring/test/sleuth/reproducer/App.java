package com.spring.reproducer.spring.test.sleuth.reproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.spring.reproducer"})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
