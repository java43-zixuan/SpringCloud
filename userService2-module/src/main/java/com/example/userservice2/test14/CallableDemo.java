package com.example.userservice2.test14;

import com.example.common.dto.UserDto;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池创建,以及callable跟Future的使用
 * 使用executor.submit()、使用executor.使用executor()、使用executor.invokeAll()等方法
 */
public class CallableDemo {

    private static final int CORE_POOL_SIZE = 5;
    private static final int MAX_POOL_SIZE = 10;
    private static final int QUEUE_CAPACITY = 10;
    private static final Long KEEP_ALIVE_TIME = 1L;

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //通过ThreadPoolExecutor构造函数自定义参数创建
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(QUEUE_CAPACITY),
                new ThreadPoolExecutor.CallerRunsPolicy());


        //execute()没有返回值，可以做那些不需要返回值的操作
        executor.execute(() -> {
            System.out.println("hello world");
        });


        List<UserDto> list = new ArrayList();
        UserDto userDto1 = new UserDto();
        userDto1.setUserName("zhangsan");userDto1.setAge("19");
        UserDto userDto2 = new UserDto();
        userDto2.setUserName("lisi");userDto1.setAge("17");
        UserDto userDto3 = new UserDto();
        userDto3.setUserName("wangwu");userDto1.setAge("20");
        list.add(userDto1);
        list.add(userDto2);
        list.add(userDto3);


        List<Callable<String>> callables = new ArrayList<>();
        for(UserDto userDto:list){
            Callable<String> callable = () -> test(userDto);
            callables.add(callable);
        }

        Callable<String> callable2 = () -> test(userDto1);
        Future<String> future2 = executor.submit(callable2);
        System.out.println("future2===========>"+future2.get());

        List<Future<String>> futures = executor.invokeAll(callables);
        for (Future<String> future : futures) {
            String str = future.get();
            System.out.println(str);
        }

//        //关闭线程池
//        executor.shutdown();
    }


    public static String test(UserDto userDto){
        return userDto.getUserName();
    }

}