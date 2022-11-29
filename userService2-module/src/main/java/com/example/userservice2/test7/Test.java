package com.example.userservice2.test7;

import org.apache.poi.ss.formula.functions.T;

/**
 * Lambda表达式
 */
public class Test {
    public void method(MyInterface mi){
        mi.test("ttt");
        //mi肯定是MyInterface的一个子类
        //这是多态的效果，子类对象执行test方法
    }

    public static void main(String[] args) {
        //正常调用写法
        Son son = new Son();
        son.test("zzz");

        //多态，创建父类的写法
        MyInterface myInterface = new Son();
        myInterface.test("aaa");

        //匿名内部类的写法
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public void test(String a) {
                System.out.println("匿名内部类的写法"+a);
            }
        };
        myInterface1.test("eee");

        //lambda 写法
        MyInterface myInterface2 = a ->{
            System.out.println("lambda写法");
        };
        myInterface2.test("rrr");




        Test test = new Test();
        test.method(son);
        test.method(myInterface);
        test.method(myInterface1);
        test.method(myInterface2);
        test.method(s->{
            System.out.println("yyy");
        });
    }
}


/**
 * 内部类，重写MyInterface接口
 */
class Son implements MyInterface{
    public void test(String a) {
        System.out.println("子类重写方法"+a);
    }
}
