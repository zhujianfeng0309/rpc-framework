package com.simple.rpc.consumer.controller;

import com.simple.rpc.api.HelloFacade;
import com.simple.rpc.core.annotation.RpcReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {

    @RpcReference(appId = "rpc-provide",serviceVersion = "1.0.0", timeout = 3000)
    private HelloFacade helloFacade;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String sayHello() {
        return helloFacade.hello("simple rpc");
    }
}