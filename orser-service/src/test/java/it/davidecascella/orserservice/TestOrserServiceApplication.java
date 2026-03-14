package it.davidecascella.orserservice;

import org.springframework.boot.SpringApplication;

public class TestOrserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(OrserServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
