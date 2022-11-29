package com.example.userservice2.test4;

/**
 * 运算符-三目运算符
 */
public class Test {
    public static void main(String[] args) {


        boolean b = (true?true:false?true:true)?false:true;
        System.out.println(b);

        boolean c = true?true:false;
        System.out.println(c);

        boolean d = true?true:false?true:true;
        System.out.println(d);
    }
}
