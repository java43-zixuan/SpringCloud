package com.example.userservice2.test16;

public class CompareAndSwap {

    //通过volatile保证内存的可见性
    private volatile  int value = 0;

    //获取内存值
    public synchronized int get() {
        return value;
    }

    //交换
    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        //读取内存中的值
        int oldValue = value;
        System.out.println("内存中的旧值："+oldValue);
        System.out.println("预期的值："+expectedValue);
        System.out.println("新的值："+newValue);
        //如果内存中的值和预期值相等，那么我们就修改内存中的值
        if (oldValue == expectedValue) {
            this.value = newValue;
        }
        //返回内存值，如果value被修改，返回的是修改前的值，主要用来给compareAndSet判断是否修改成功
        return oldValue;
    }

    //cas
    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        //如果预期值和内存值相等，就返回true否则返回false
        return expectedValue == compareAndSwap(expectedValue, newValue);
    }
}
