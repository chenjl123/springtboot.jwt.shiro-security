package com.cn.shiro;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@MapperScan("com.cn.shiro.mapper")
public class ShiroJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtApplication.class, args);
    }

}
