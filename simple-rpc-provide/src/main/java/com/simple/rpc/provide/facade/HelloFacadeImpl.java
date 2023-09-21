package com.simple.rpc.provide.facade;


import com.simple.rpc.api.HelloFacade;
import com.simple.rpc.core.annotation.RpcProvideService;
import org.springframework.stereotype.Service;

/**
 * 疑问：HelloFacadeImpl 没有生产单利的bean到spring
 * 两种方式解决：
 *  在@RpcProvideService上加上注解@Component
 *  在实现类上加@Service注解
 */
@RpcProvideService(serviceInterface = HelloFacade.class,appId = "rpc-provide",serviceVersion = "1.0.0")
@Service
public class HelloFacadeImpl implements HelloFacade {
    @Override
    public String hello(String name) {
        return "hello" + name;
    }
}