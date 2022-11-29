package com.example.userservice2.test10;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Test {

    @MyAnnotation("aaa")
    private String name;

    @MyAnnotation("ssss")
    public void a(){

    }

    /**
     * 上边是定义注解，创建注解
     * 下边在主方法使用反射来解析注解
     * @param args
     */
    public static void main(String[] args) throws Exception{
        Class clazz = Test.class;
        Field field = clazz.getDeclaredField("name");//这里由于属性是私有的，所以不能直接通过getField获取，得使用getDeclaredField获取
        MyAnnotation an = field.getAnnotation(MyAnnotation.class);//注解相当于一个对象,看起来像是在传参数，其实是把注解给能读懂注解的人，然后获取返回值
        System.out.println(an.value());


        Method method = clazz.getMethod("a");
        Annotation[] annotations = method.getAnnotations();
        Annotation annotations1 = annotations[0];
        Class anClass = annotations1.getClass();
        Method method1 = anClass.getMethod("value");
        String calue = (String)method1.invoke(annotations1);
        System.out.println(calue);
    }
}
