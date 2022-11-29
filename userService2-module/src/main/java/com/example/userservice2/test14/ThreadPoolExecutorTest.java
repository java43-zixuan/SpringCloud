package com.example.userservice2.test14;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池
 * 线程池的意义在于复用线程池中的核心线程，所以创建线程池后不需要关闭，否则就失去了线程池的意义
 * 那么不关闭线程池，当在定时任务中使用线程池了。就会因为重复调用而重复进行线程池的创建，就会导致内存溢出。
 * 所以可以使用静态线程池的方式，类似单例的模式去创建线程池。
 */
public class ThreadPoolExecutorTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException{





    }



    public static void testTransactional() throws ExecutionException, InterruptedException {
        int inter = 3;
        ThreadPoolExecutor poolExecutor = CreateThreadPoolUtil.getInstance();
        FutureTask[] integerFuture = new FutureTask[inter];
        for (int i = 0; i < inter; i++) {
            int finalI = i + 1;
            integerFuture[i] = new FutureTask<>(() -> {
                return new TransactionCallback<String>() {
                    @Override
                    public String doInTransaction(TransactionStatus transactionStatus) {
                        System.out.println("多线程运行了---" + finalI);
                        return "业务执行成功";
                    }
                };
            });
            poolExecutor.execute(integerFuture[i]);
        }
        for (int i = 0; i < inter; i++) {
            System.out.println("integerFuture返回结果 = " + i + "==" + integerFuture[i].get());
        }

        ThreadMXBean bean = ManagementFactory.getThreadMXBean();
        System.out.println("线程总数为 = " + bean.getThreadCount());

    }


}
