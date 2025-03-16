package com.example.admin_template;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Fetters
 */
@SpringBootApplication
@MapperScan("com.example.admin_template.mapper")
public class AdminTemplateApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminTemplateApplication.class, args);
    }
}
