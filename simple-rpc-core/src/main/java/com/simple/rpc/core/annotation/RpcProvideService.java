package com.simple.rpc.core.annotation;


import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;

/**
 * Inherited：通过对注解上使用元注解Inherited声明出的注解，在使用时用在类上，可以被子类所继承，对属性或方法无效。
 *
 *@Retention
 * @Retention 用于设置注解（Annotation）的有效范围，描述了注解的生命周期。
 * Retention注解有一个属性value，是RetentionPolicy类型，RetentionPolicy有3个值
 *
 * RetentionPolicy.SOURCE // 表明注解只保留在源文件，只在源文件有效；
 * RetentionPolicy.CLASS // 表明注解保留在class文件，在class文件有效；
 * RetentionPolicy.RUNTIME // 表明注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在

 *
 * @Target 用于设置注解的作用范围，取值有：
 *
 * @Target(ElementType.TYPE)——用于描述接口、类、枚举、注解
 * @Target(ElementType.FIELD)——用于描述枚举常量
 * @Target(ElementType.METHOD)——用于描述方法
 * @Target(ElementType.PARAMETER)——用于描述方法参数
 * @Target(ElementType.CONSTRUCTOR) ——用于描述构造函数
 * @Target(ElementType.LOCAL_VARIABLE)——用于描述局部变量
 * @Target(ElementType.ANNOTATION_TYPE)——用于描述注解
 * @Target(ElementType.PACKAGE)——用于描述包
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({TYPE})
@Inherited
public @interface RpcProvideService {

    String appId() default "";

    Class<?> serviceInterface() default Object.class;

    String serviceVersion() default "1.0";

}