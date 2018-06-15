package me.qspeng.streamService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "me.qspeng.streamService")
public class StreamServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StreamServiceApplication.class, args);
    }
}
