package com.canx.cebulax.cebulax;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class CebulaxApplication {

    public static void main(String[] args) {
        SpringApplication.run(CebulaxApplication.class, args);
    }

}
