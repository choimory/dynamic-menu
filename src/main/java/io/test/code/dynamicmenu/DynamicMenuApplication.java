package io.test.code.dynamicmenu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DynamicMenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(DynamicMenuApplication.class, args);
    }

}
