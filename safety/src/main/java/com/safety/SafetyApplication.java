package com.safety;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SafetyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SafetyApplication.class, args);
    }

}
