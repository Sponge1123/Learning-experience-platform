package com.buka;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@MapperScan("com.buka.mapper")
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
}