package com.toilamdev.stepbystep;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class)
@SpringBootApplication
public class StepbystepApplication {
    public static void main(String[] args) {
        SpringApplication.run(StepbystepApplication.class, args);
    }
}
