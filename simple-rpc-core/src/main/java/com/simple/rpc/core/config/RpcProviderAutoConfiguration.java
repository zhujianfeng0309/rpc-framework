package com.simple.rpc.core.config;


import com.simple.rpc.core.RpcProvider;
import com.simple.rpc.registry.RegistryFactory;
import com.simple.rpc.registry.RegistryService;
import com.simple.rpc.registry.RegistryType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RpcProviderAutoConfiguration {


    @Value("${server.port}")
    private int serverPort;

    @Bean
    public RpcProvider init() throws Exception {
        // todo  spi方式获取注册类型/自定义扩展注解获取注册类型
        RegistryType type = RegistryType.valueOf("ZOOKEEPER");
        RegistryService serviceRegistry = RegistryFactory.getInstance("127.0.0.1:2181", type);
        return new RpcProvider(serverPort, serviceRegistry);
    }

}