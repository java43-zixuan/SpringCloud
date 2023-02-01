package com.example.userservice2.test19;

import com.google.common.base.Joiner;
import com.sun.deploy.util.StringUtils;
import org.jsoup.internal.StringUtil;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 京东一面
 * 将List转换为String的几种方式
 */
public class test {


    public static void main(String[] args) {
        List<String> list = Arrays.asList("ly","lxr","zx");

        //不用导包的
        //第一种：循环
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<list.size();i++){
            sb.append(list.get(i));
            sb.append(",");
        }
        String str1 = sb.substring(0,sb.length()-1);
        System.out.println("第一种方式:"+str1);

        //第二种：String.join()方法
        String str2 = String.join(",",list);
        System.out.println("第二种方式:"+str2);

        //第三种：com.sun.deploy.util.StringUtils 的join方法
        String str3 =StringUtils.join(list,",");
        System.out.println("第三种方式:"+str3);

        //第四种：Stream流
        String str4 =list.stream().collect(Collectors.joining(","));
        System.out.println("第四种方式:"+str4);

        //第五种：Stream流拼接josn格式
        String str5 =list.stream().collect(Collectors.joining(",","{","}"));
        System.out.println("第五种方式:"+str5);

        //需要导包
        //第六种：jsoup 爬虫 org.jsoup.internal.StringUtil
        String str6 = StringUtil.join(list,",");
        System.out.println("第六种方式:"+str6);

        //第七种：guava 使用 com.google.common.base.Joiner 这个类
        Joiner joiner = Joiner.on(",");
        String str7 = joiner.join(list);
        System.out.println("第七种方式:"+str7);
    }

}
