package com.simple.rpc.core.annotation;


import com.simple.rpc.core.config.EnableRpcImportBeanDefinitionRegistrarConfiguration;
import com.simple.rpc.core.config.EnableRpcImportSelectorConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
//@Import(EnableRpcImportSelectorConfiguration.class)
@Import(EnableRpcImportBeanDefinitionRegistrarConfiguration.class)
public @interface EnableRpc {
}