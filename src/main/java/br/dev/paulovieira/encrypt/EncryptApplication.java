package br.dev.paulovieira.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class EncryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(EncryptApplication.class, args);
    }

}
