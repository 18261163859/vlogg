package com.smx.vlogg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@MapperScan("com.smx.vlogg.mapper")
public class VloggApplication {

    public static void main(String[] args) {
        SpringApplication.run(VloggApplication.class, args);
    }

}
