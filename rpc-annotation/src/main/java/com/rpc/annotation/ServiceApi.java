package com.rpc.annotation;

import java.lang.annotation.*;

/**
 * @Retention 注解
 * 功能：指明修饰的注解的生存周期，即会保留到哪个阶段。
 * RetentionPolicy的取值包含以下三种：
 * SOURCE：源码级别保留，编译后即丢弃。
 * CLASS:编译级别保留，编译后的class文件中存在，在jvm运行时丢弃，这是默认值。
 * RUNTIME： 运行级别保留，编译后的class文件中存在，在jvm运行时保留，可以被反射调用。
 */
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface ServiceApi {

}
