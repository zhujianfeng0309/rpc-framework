package com.simple.rpc.provide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(scanBasePackages = {"com.simple.rpc"})
public class RpcProvideApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcProvideApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    【simple-rpc-provide应用】启动成功      ヾ(◍°∇°◍)ﾉﾞ");

    }
}