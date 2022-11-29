package com.example.userservice2.test16;

public class TestCompareAndSwap {
        private static CompareAndSwap cas = new CompareAndSwap();

        public static void main(String[] args) {
            for (int i = 0; i < 10; i++) {
                new Thread(new Runnable() {
                    public void run() {
                        //获取预期值
                        int expectedValue = cas.get();
                        //新的值
                        int newValue = (int) (Math.random() * 101);
                        //尝试去修改值，并接收修改情况
                        boolean b = cas.compareAndSet(expectedValue, newValue);
                        System.out.println(b);
                    }
                }).start();
            }
        }

}
