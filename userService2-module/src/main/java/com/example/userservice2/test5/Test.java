package com.example.userservice2.test5;

import com.example.common.dto.UserDto;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

/**
 * stream流
 */
public class
Test {
    public static void main(String[] args) {
        /**
         * filter筛选 filter的方法参数为一个条件
         */
        List<Integer> integerList = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> filter = integerList.stream().filter(i -> i > 3);
        System.out.println("filter筛选========>"+filter.collect(Collectors.toList()));

        /**
         * distinct去除重复元素
         */
        List<Integer> distinctList = Arrays.asList(1, 1, 2, 3, 4, 5);
        Stream<Integer> distinct = distinctList.stream().distinct();
        System.out.println("distinct去除========>"+distinct.collect(Collectors.toList()));

        /**
         * limit返回指定流个数 limit的参数值必须>=0，否则将会抛出异常
         */
        List<Integer> limitList = Arrays.asList(1, 1, 2, 3, 4, 5);
        Stream<Integer> limit= limitList.stream().limit(3);
        System.out.println("limit========>"+limit.collect(Collectors.toList()));

        /**
         * skip跳过流中的元素
         * 例子跳过前两个元素，所以打印结果为2,3,4,5，skip的参数值必须>=0，否则将会抛出异常
         */
        List<Integer> skipList = Arrays.asList(1, 1, 2, 3, 4, 5);
        Stream<Integer> skip = skipList.stream().skip(2);
        System.out.println("skip跳过========>"+skip.collect(Collectors.toList()));

        /**
         * map流映射 所谓流映射就是将接受的元素映射成另外一个元素。
         */
        List<String> mapList = Arrays.asList("Java 8", "Lambdas",  "In", "Action");
        Stream<Integer> maplys = mapList.stream().map(String::length);
        System.out.println("map流映射========>"+maplys.collect(Collectors.toList()));

        /**
         * flatMap流转换
         * map(w -> w.split(“”))的返回值为Stream<String[]>，我们想获取Stream，可以通过flatMap方法完成Stream ->Stream的转换
         */
        List<String> wordList = Arrays.asList("Hello", "World");
        List<String> strList = wordList.stream()
                .map(w -> w.split(""))
                .flatMap(Arrays::stream)
//                .distinct()
                .collect(Collectors.toList());
        System.out.println("flatMap流转换========>"+strList);

        /**
         *  allMatch匹配所有
         */
        List<Integer> allmatchList = Arrays.asList(4, 4, 5);
        if (allmatchList.stream().allMatch(i -> i > 3)) {
            System.out.println("allMatch========>值都大于3");
        }
        /**
         *  anyMatch匹配其中一个
         */
        List<Integer> anyMatchList = Arrays.asList(1, 2, 3, 4, 5);
        if (anyMatchList.stream().anyMatch(i -> i > 3)) {
            System.out.println("anyMatch========>存在大于3的值");
        }
        /**
         *  noneMatch全部不匹配
         */
        List<Integer> noneMatchList = Arrays.asList(1, 2, 3);
        if (noneMatchList.stream().noneMatch(i -> i > 3)) {
            System.out.println("noneMatch========>值都小于3");
        }

        /**
         * 统计
         * count
         * counting
         */
        List<Integer> countList = Arrays.asList(1, 2, 3, 4, 5);
        Long result1 = countList.stream().count();
        Long result2 = countList.stream().collect(counting());
        System.out.println("统计1>"+result1+"=========统计2>"+result2);

        /**
         * 查找
         * findFirst查找第一个
         * findAny随机查找一个
         * 通过findAny方法查找到其中一个大于三的元素并打印，因为内部进行优化的原因，当找到第一个满足大于三的元素时就结束，该方法结果和findFirst方法结果一样。提供findAny方法是为了更好的利用并行流，findFirst方法在并行上限制更多
         */
        Optional<Integer> findFirst = integerList.stream().filter(i -> i > 3).findFirst();
        Optional<Integer> findAny = integerList.stream().filter(i -> i > 3).findAny();
        System.out.println("findFirst查找===========》"+findFirst);
        System.out.println("findAny查找===========》"+findAny);

        /**
         * 求和
         * 求和、求最大值、最小值的时候，对于相同操作有不同的方法可以选择执行。可以选择collect、reduce、min/max/sum方法，推荐使用min、max、sum方法。因为它最简洁易读，同时通过mapToInt将对象流转换为数值流，避免了装箱和拆箱操作。
         */
        int sum = integerList.stream().reduce(0, (a, b) -> (a + b));
        int sum2 = integerList.stream().reduce(0, Integer::sum);
        System.out.println("reduce求和============>"+sum);
        //如果数据类型为double、long，则通过summingDouble、summingLong方法进行求和。
        List<Double> doubleList = new ArrayList<>();
        doubleList.add(2.5);
        doubleList.add(1.5);
        doubleList.add(3.9);//7.9
        Double sum3 = doubleList.stream().collect(summingDouble(a -> a));
        System.out.println("通过summingDouble求和============>"+sum3);
        //通过sum
        int sum4 = integerList.stream().mapToInt(a -> a).sum();
        System.out.println("通过sum求和============>"+sum4);

        /**
         * 获取流中最小最大值
         * 通过min/max获取最小最大值
         * 通过minBy/maxBy获取最小最大值
         * 通过reduce获取最小最大值
         */
        Optional<Integer> min1 = integerList.stream().min(Integer::compareTo);
        Optional<Integer> max1 = integerList.stream().max(Integer::compareTo);
        System.out.println("第一种方式最大值最小值================》最小："+min1+"最大："+max1);

        Optional<Integer> min3 = integerList.stream().collect(minBy(Integer::compareTo));
        Optional<Integer> max3 = integerList.stream().collect(maxBy(Integer::compareTo));
        System.out.println("第三种方式最大值最小值================》最小："+min3+"最大："+max3);
        Optional<Integer> min4 = integerList.stream().reduce(Integer::min);
        Optional<Integer> max4 = integerList.stream().reduce(Integer::max);
        System.out.println("第四种方式最大值最小值================》最小："+min4+"最大："+max4);
        /**
         * 如果数据类型为double、long，则通过averagingDouble、averagingLong方法进行求平均。
         */
//        double average = doubleList.stream().collect(averagingInt(Dish::getCalories));

        /**
         * 通过foreach进行元素遍历
         */
        List<Integer> foreachList = Arrays.asList(1, 2, 3, 4, 5);
        //foreachList.stream().forEach(System.out::println);


        UserDto userDto = new UserDto();
        userDto.setId(4);userDto.setUserName("张三");userDto.setAge("12");
        UserDto userDto1 = new UserDto();
        userDto1.setId(5);userDto1.setUserName("李四");userDto1.setAge("12");
        UserDto userDto2 = new UserDto();
        userDto2.setId(9);userDto2.setUserName("王五");userDto2.setAge("14");
        List<UserDto>userDtos = new ArrayList<>();
        userDtos.add(userDto);
        userDtos.add(userDto1);
        userDtos.add(userDto2);

        OptionalInt minUser = userDtos.stream().mapToInt(UserDto::getId).min();
        System.out.println(minUser.getAsInt());
        Optional<Integer> min = userDtos.stream().map(UserDto::getId).collect(minBy(Integer::compareTo));
        System.out.println(min.hashCode());

        /**
         * joining拼接流中的元素
         */
        String joining = userDtos.stream().map(UserDto::getUserName).collect(Collectors.joining(", "));
        System.out.println("joining拼接==============>"+joining);

        /**
         * 在collect方法中传入groupingBy进行分组，其中groupingBy的方法参数为分类函数。还可以通过嵌套使用groupingBy进行多级分类。
         */
        Map<String, List<UserDto>> result = userDtos.stream().collect(Collectors.groupingBy(UserDto::getAge));
        System.out.println("单级分类============>"+result);
        Map<String, Map<String, List<UserDto>>> groupBys = userDtos.stream().collect(Collectors.groupingBy(UserDto::getAge,Collectors.groupingBy(UserDto::getUserName)));
        System.out.println("多级分类============>"+groupBys);
    }
}
