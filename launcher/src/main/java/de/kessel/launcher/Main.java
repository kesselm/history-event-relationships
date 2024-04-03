package de.kessel.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"de.kessel.events", "de.kessel.launcher"})
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}