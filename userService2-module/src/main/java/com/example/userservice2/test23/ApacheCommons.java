package com.example.userservice2.test23;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Apache 常用工具类
 *
 * jar包引入在common工程pom及父类pom中
 */
public class ApacheCommons {

    public static void main(String[] args) {
        /**
         * commons-lang3
         *
         *  StringUtils;
         *  NumberUtils;
         *  DateUtils;
         *  DateFormatUtils;
         */




        /**
         * commons-io
         * FileUtils;
         */

        /**
         * commons-beanutils
         * BeanUtils:用于封装数据,将map中属性相同的数据赋值到bean中，克隆bean。
         */
        Map<String,String> map = new HashMap();
        map.put("id","25");
        map.put("studentId","15");
        map.put("content","aaaaqwqeqweq");
        map.put("value","35");
        Question question = new Question();
        try{
            BeanUtils.copyProperties(question, map);
            System.out.println(question);
            Object obj = BeanUtils.cloneBean(question);
            System.out.println(obj);
        }catch (Exception e){
            e.printStackTrace();
        }



        /**
         * commons-collections
         * ListUtils:并集、交集、差集
         * SetUtils;
         * MapUtils;
         */

        List<String> list1 = new ArrayList<>();
        list1.add("a");
        list1.add("b");
        list1.add("c");
        list1.add("d");
        list1.add("e");
        System.out.println("集合list1:"+list1);
        List<String> list2 = new ArrayList<>();
        list2.add("d");
        list2.add("e");
        list2.add("f");
        list2.add("g");
        list2.add("h");
        System.out.println("集合list2:"+list2);
        //差集（以左边参数为准）
        List<String> subtractList = ListUtils.subtract(list1, list2);
        System.out.println("在list1但不在list2中:"+subtractList);
        List<String> subtractList2 = ListUtils.subtract(list2, list1);
        System.out.println("在list2但不在list1中:"+subtractList2);

        //交集
        List<String> intersectionList = ListUtils.intersection(list1, list2);
        System.out.println("list1、list2交集"+intersectionList);
        List<String> retainAll = ListUtils.retainAll(list1, list2);
        System.out.println("list1、list2交集"+retainAll);

        //并集（但有重复，类似于mysql的union all）
        List<String> unionList = ListUtils.union(list1, list2);
        System.out.println("list1、list2并集（有重复）"+unionList);
        //并集（但无重复，类似于mysql中的union）
        List<String> sumList = ListUtils.sum(list1, list2);
        System.out.println("list1、list2并集（无重复）"+sumList);


        /*
        isNotEmpty ( ) 是否不为空
        isEmpty ( ) 是否为空
        putAll ( ) 添加所有元素
        getString ( ) 获取String类型的值
        getObject ( ) 获取Object类型的值
        getInteger ( )获取Integer类型的值
        get*** ( ) 类似上面的
        EMPTY_MAP 获取一个不可修改的空类型Map
        unmodifiableMap 获取一个不可以修改的Map（不能新增或删除）
        unmodifiableSortedMap 获取一个不可以修改的有序的Map（不能新增或删除）
        fixedSizeMap 获取一个固定长度的map
        multiValueMap 获取一个多值的map(即一个key可以对应多个value值)
        invertMap 返回一个key与value对调的map
        predicatedMap() 返回一个满足predicate条件的map
        lazyMap 返回一个lazy的map（值在需要的时候可以创建）
        */


    }
}
