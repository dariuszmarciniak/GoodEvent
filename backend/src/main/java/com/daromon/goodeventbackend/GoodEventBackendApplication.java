package com.daromon.goodeventbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.daromon.goodeventbackend")
public class GoodEventBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodEventBackendApplication.class, args);
    }

}
