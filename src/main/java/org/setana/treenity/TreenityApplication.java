package org.setana.treenity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TreenityApplication {

    public static void main(String[] args) {
        SpringApplication.run(TreenityApplication.class, args);
    }

}
