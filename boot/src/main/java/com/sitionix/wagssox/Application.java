package com.sitionix.wagssox;

import com.sitionix.forge.inbox.boot.config.EnableInbox;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableInbox
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
