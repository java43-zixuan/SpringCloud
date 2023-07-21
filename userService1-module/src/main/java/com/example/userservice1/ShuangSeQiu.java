package com.example.userservice1;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

public class ShuangSeQiu {

    private static final Integer[] blue = {1,6,10,11,12,14};
    private static final Integer[] red = {1,6,10,11,12,14,19,21,23,24};



    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        int i = 0;
        while(i<10000000){
            List<Integer> result = luck();
            if(result.size()!=0){
                set.add(JSONObject.toJSONString(result));
            }
            i++;
        }
        System.out.println("一共生成幸运号码："+set.size()+"个");
        Object[] str = set.toArray();
        for (int j=0;j<str.length;j++){
            System.out.println("第"+(j+1)+"个幸运号码:"+str[j].toString().substring(1,str[j].toString().length()-1));
        }


    }
    public static List<Integer> luck(){
        //定义一个数组，存储6个数字 红色球
        Integer[] arr=new Integer [6];
        //遍历数组，为每个位置生成一个随机号码，范围red，不能重复。
        for (int i = 0; i <= arr.length-1 ; i++) {
            while (true) {
                //注意：当前判断生成的号码是否与之前的重复，出现重复则重新生成，直到不重复再存进数组
                //定义一个flag，默认data是没有重复的
                int redIdx= (int)(Math.random()*red.length);
                int redData = red[redIdx];
                boolean flag=true;
                for (int j = 0; j < i; j++) {
                    if(redData==arr[j]){
                        flag=false;
                        //重复，不能用，退出循环重新生成新的数。
                        break;
                    }
                }
                if (flag){
                    //data在之前没有出现，可以存入数组。
                    arr[i]=redData;
                    break;
                }
            }
        }
        //把红球排序
        Arrays.sort(arr);
        List<Integer> result = new ArrayList<>();
        //把红球加到结果集
        result.addAll(Arrays.asList(arr));
        //筛选出能够使用的篮球
        List<Integer> syBlue = new ArrayList<>();
        for(int i:blue){
            if(!Arrays.asList(arr).contains(i)){
                syBlue.add(i);
            }
        }
        if(syBlue.size() == 0){
            return new ArrayList<>();
        }
        //随机找一个篮球
        int buleIdx= (int)(Math.random()*syBlue.size());
        int buleData = syBlue.get(buleIdx);
        //组装结果集
        result.add(buleData);
        return result;
    }


}
