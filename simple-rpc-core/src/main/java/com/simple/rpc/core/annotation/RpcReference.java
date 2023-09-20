package com.simple.rpc.core.annotation;

import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Autowired
public @interface RpcReference {

    String appId() default "";

    String registryAddress() default "127.0.0.1:2181";

    String serviceVersion() default "1.0";

    long timeout() default 5000;

}