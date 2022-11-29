package com.example.userservice2.test10;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解
 * 自定义注解跟接口很像，接口中包含（属性、方法、构造方法、块）
 * 自定义注解中指包含（属性、方法），属性不经常使用
 *
 * is-a   写完注解后需要大佬认证否则不能直接使用
 * 元注解@Target  表示注解可以放在哪用，比如属性上：FIELD  方法上:METHOD
 * @Retention 必须使用，是定义被它所注解的注解保留多久，一共有三种策略
 * source：注解只保留在源文件，当Java文件编译成class文件的时候，注解被遗弃；被编译器忽略
 * class：注解被保留到class文件，但jvm加载class文件时候被遗弃，这是默认的生命周期
 * runtime：注解不仅被保存到class文件中，jvm加载class文件之后，仍然存在
 * 生命周期长度 SOURCE < CLASS < RUNTIME
 */
@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {

    //包括方法在内，前边的修饰符都可以去掉，如下灰色的字体可以去掉
    //public static final String test = "aaa";
    //需要有基础类型的返回值。所以写void会报错
    //public abstract String test();
    String value();
}
