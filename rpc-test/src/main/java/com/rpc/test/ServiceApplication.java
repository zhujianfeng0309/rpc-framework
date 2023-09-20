package com.rpc.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.rpc.test"})
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    【ServiceApplication应用】启动成功      ヾ(◍°∇°◍)ﾉﾞ");

    }
}