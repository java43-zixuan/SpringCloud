package com.example.userservice2.test9;

import com.example.userservice2.test1.Box;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射
 */
public class Test {

    public static void main(String[] args)throws Exception {
       //1.获取运行时类
       Class clazz =  Class.forName("com.example.userservice2.test1.Test1Util");
       //2.获取方法
       Method method = clazz.getMethod("showBox",Box.class);
       //3.分析方法
        int modifiers = method.getModifiers();//权限修饰符
        Class returnType = method.getReturnType();//返回类型
        String methodName = method.getName();//方法名字
        Class[] params = method.getParameterTypes();//方法参数
        System.out.println("权限修饰符:"+modifiers);
        System.out.println("返回类型:"+returnType);
        System.out.println("方法名字:"+methodName);


        Box<String> box1 = new Box();
        box1.setData("asdasd");
        method.invoke(clazz,box1);
        //4.获取属性
        Field[] fields = clazz.getFields();
        for(int i = 0;i < fields.length;i++) {
            System.out.println(fields[i]);//打印输出
        }
        Field field = clazz.getField("name");
        System.out.println(field);

    }
}
