package com.simple.rpc.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"com.simple.rpc"})
public class RpcConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpcConsumerApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    【simple-rpc-consumer应用】启动成功      ヾ(◍°∇°◍)ﾉﾞ");
    }
}