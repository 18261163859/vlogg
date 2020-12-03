package com.smx.vlogg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
        (exclude = {DataSourceAutoConfiguration.class})
public class VloggApplication {

    public static void main(String[] args) {
        SpringApplication.run(VloggApplication.class, args);
    }

}
