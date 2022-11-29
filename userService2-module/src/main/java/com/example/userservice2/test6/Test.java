package com.example.userservice2.test6;

import java.util.Arrays;
import java.util.Collections;

/**
 * 排序
 */
public class Test {

    public static void main(String[] args) {

        /**
         * 冒泡排序升序
         */
        Integer [] str= {1,3,2,5,4,7,9,10,6};
        for (int i = 0; i < str.length; i++) {
            for (int j = 0; j < str.length-i-1; j++) {
                if(str[j]>str[j+1]){
                    int temp=str[j+1];
                    str[j+1]=str[j];
                    str[j]=temp;
                }
            }
        }
        System.out.println("冒泡排序升序"+ Arrays.toString(str));


        /**
         * 冒泡排序降序
         */
        Integer [] str1= {1,3,2,5,4,7,9,10,6};
        for (int i = 0; i < str1.length; i++) {
            for (int j = 0; j < str1.length-i-1; j++) {
                if(str1[j]<str1[j+1]){
                    int temp=str1[j+1];
                    str1[j+1]=str1[j];
                    str1[j]=temp;
                }
            }
        }
        System.out.println("冒泡排序降序"+Arrays.toString(str1));


        /**
         * Arrays升序
         */
        Integer [] str2= {1,3,2,5,4,7,9,10,6};
        Arrays.sort(str2);
        System.out.println("Java内置的排序功能升序"+Arrays.toString(str2));


        /**
         * Arrays降序
         * Arrays.sort默认是升序排列，可以使用*Collections.reverseOrder()来更改排序方式。
         * 注：需注意不支持基本类型(int，double，char等)，如果是int型需要改成Integer，float要改成Float。
         */
        Integer [] str3= {1,3,2,5,4,7,9,10,6};
        Arrays.sort(str3, Collections.reverseOrder());
        System.out.println("Java内置的排序功能降序"+Arrays.toString(str3));



    }
}
