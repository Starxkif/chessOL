package com.hmmnx.chessweb;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hmmnx.chessweb.mapper")
public class ChesswebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChesswebApplication.class, args);
    }

}
