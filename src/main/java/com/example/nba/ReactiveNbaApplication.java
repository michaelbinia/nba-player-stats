package com.example.nba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The main application class for the ReactiveNbaApplication.
 * This class initializes and runs the Spring Boot application.
 */
@SpringBootApplication
public class ReactiveNbaApplication {
    /**
     * The entry point of the application. This method is used to bootstrap and launch
     * the Spring application from the main method.
     *
     * @param args an array of command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(ReactiveNbaApplication.class, args);
    }
}
