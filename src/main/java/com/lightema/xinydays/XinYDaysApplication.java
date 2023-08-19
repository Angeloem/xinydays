package com.lightema.xinydays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class XinYDaysApplication {

    public static void main(String[] args) {
        SpringApplication.run(XinYDaysApplication.class, args);
    }
}
