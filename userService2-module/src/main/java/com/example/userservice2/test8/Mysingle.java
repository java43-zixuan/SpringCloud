package com.example.userservice2.test8;

public class Mysingle {


    //类下存在默认的无参构造方法  new
    private Mysingle(){}
    //属性，方法，构造方法，代码块（没名字，没参数，没返回值）
    private static Mysingle mysingle = new Mysingle();
    //共有方法调用
    public static Mysingle getMysingle(){
        return mysingle;
    }
}
