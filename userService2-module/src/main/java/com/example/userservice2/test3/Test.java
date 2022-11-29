package com.example.userservice2.test3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数组跟集合相互转化
 */
public class Test {
    public static void main(String[] args) {

        /**
         * 数组--->集合
         */
        Integer[] array = {1,2,3};//asList方法参数是泛型,所以这里使用Integer 包装类，否则使用int无法接收数据
        List<Integer> a =Arrays.asList(array);
        //虽然可以转换成集合，但是不能对其进行增删操作，会报java.lang.UnsupportedOperationException错误。
        //是因为List不是我们定义的ArrayList。是Arrays里自己定义的一个私有静态内部类
        //a.add(4);
        //a.remove(0);
        //这种情况如果需要使用add,remove等方法，需要我们自己构造Arraylist
        ArrayList<Integer> arrayList = new ArrayList<>(a);

        /**
         * 集合-->数组
         */
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        //list.toArray方法有两种，一种是没有参数的（需要使用Object来接收），一种有泛型参数
        Object[] objects = list.toArray();
        for (Object o:objects){
            //使用的时候需要自己去强转类型
            System.out.println((int)o);
        }
        //带有泛型参数的toArray方法的意思是需要自己定义一个类型数组，然后把list集合里的元素放入定义的数组中，之后再打印定义的数组就有元素了
        Integer[] b = new Integer[list.size()];
        list.toArray(b);
        System.out.println(b[0]);
        //这里如果定义的数组没有给长度，或者长度不够的话，需要使用新的数组来接收，否则报错。因为在toArray里边有判断，如果定义的数组长度不够它会返回新的数组
        Integer[] c = new Integer[0];
        list.toArray(c);
        //System.out.println(c[0]);  // java.lang.ArrayIndexOutOfBoundsException: 0   由于长度不够报数组越界错误
        Integer[] d = list.toArray(c);
        System.out.println(d[0]);
    }
}
