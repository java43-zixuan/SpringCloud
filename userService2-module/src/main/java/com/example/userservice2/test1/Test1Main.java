package com.example.userservice2.test1;

public class Test1Main {

    public static void main(String[] args) {
        Box<String> box1 = new Box();
        box1.setData("asdasd");
        Box<Integer> box2 = new Box();
        box2.setData(12);
        Box<Double> box3 = new Box();
        box3.setData(12.3);

        Test1Util.showBox(box1);
        Test1Util.showBox(box2);
        Test1Util.showBox(box3);
    }
}
