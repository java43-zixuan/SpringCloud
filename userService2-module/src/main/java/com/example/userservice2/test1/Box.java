package com.example.userservice2.test1;

public class Box<T> {

    private T data;




    /**
     * 泛型可以在参数里用到、也可以在返回值中用到（void 改成T）
     * @param t
     * @param <T>
     */
    public static <T> void test(T t){

    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
