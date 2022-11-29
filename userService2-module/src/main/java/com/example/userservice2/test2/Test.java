package com.example.userservice2.test2;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 打印数组元素的几种方法
 */
public class Test {

    public static void main(String[] args) {
        /**
         * 直接打印数组i不会报错，但是输出的不是i里边的元素而是一个hashCode值,是由三部分拼接组成
         * [I ：表示输出当前元素对象的类型
         * @
         * 4cdf35a9 ：表示当前对象地址的hashCode值（int类型，16进制表示形式）
         */
        int[] q = {1,2,3};
        System.out.println(q); //[I@4cdf35a9

        /**
         *  比较特殊的一个类型char 数组打印会直接打印出元素，String都不好使
         */
//        char[] a = {'a','b','c'};
//        System.out.println(a); //abc

        /**
         * 最常见的方式是for循环
         */
//        for(int i=0;i<q.length;i++ ){
//            System.out.println(q[i]);
//        }

        /**
         * 增强for循环
         */
//        for(int k:q){
//            System.out.println(k);
//        }

        /**
         * 工具类Arrays 有8中基本类型的toString方法
         */
        String value = Arrays.toString(q);
        System.out.println(value);//[1, 2, 3]

        Object[] w = {1,2,3};
        String value1 = Arrays.deepToString(w);
        System.out.println(value1);//[1, 2, 3]

        //使用Stream流的形式，然后使用forEach会一个一个打印元素
        IntStream s = Arrays.stream(q);
        s.forEach(System.out::println);

    }
}
