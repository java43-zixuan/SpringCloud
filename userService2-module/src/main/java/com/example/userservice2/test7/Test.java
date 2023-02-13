package com.example.userservice2.test7;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Lambda表达式
 */
public class Test {
    public void method(MyInterface mi){
        mi.test("ttt");
        //mi肯定是MyInterface的一个子类
        //这是多态的效果，子类对象执行test方法
    }

    private static class Dog{
        String name;
        int age;

        //无参构造
        public Dog(){
            System.out.println("一个Dog对象通过无参构造被实例化了");
        }
        //有参构造
        public Dog(String name,int age){
            System.out.println("一个Dog对象通过有参构造被实例化了");
            this.name = name;
            this.age = age;
        }
    }
    //定义一个函数式接口,用以获取无参的对象
    @FunctionalInterface
    private interface GetDog{
        //若此方法仅仅是为了获得一个Dog对象，而且通过无参构造去获取一个Dog对象作为返回值
        Dog test();
    }

    //定义一个函数式接口,用以获取有参的对象
    @FunctionalInterface
    private interface GetDogWithParameter{
        //若此方法仅仅是为了获得一个Dog对象，而且通过有参构造去获取一个Dog对象作为返回值
        Dog test(String name,int age);
    }

    // 测试
    public static void main(String[] args) {
        //lambda表达式实现接口
        GetDog lm = Dog::new; //引用到Dog类中的无参构造方法，获取到一个Dog对象
        Dog dog = lm.test();
        System.out.println("修狗的名字："+dog.name+" 修狗的年龄："+dog.age); //修狗的名字：null 修狗的年龄：0
        GetDogWithParameter lm2 = Dog::new;//引用到Dog类中的有参构造，来获取一个Dog对象
        Dog dog1 = lm2.test("萨摩耶",2);
        System.out.println("修狗的名字："+dog1.name+" 修狗的年龄："+dog1.age);//修狗的名字：萨摩耶 修狗的年龄：2


        List<User> userList = new ArrayList<>();

        User g1 = new User("u11",231);
        User g2 = new User("u12",232);
        User g3 = new User("u13",233);
        User g4 = new User("u14",234);
        User g5 = new User("u15",235);
        User g6 = new User("u16",236);

        userList.add(g1);
        userList.add(g2);
        userList.add(g3);
        userList.add(g4);
        userList.add(g5);
        userList.add(g6);

        /**使用lambda表达式截取List中的某一个属性的值*/
        System.out.println("使用lambda表达式截取List中的某一个属性的值");
        List<Integer> ids = userList.stream().map(e -> e.getAge()).collect(Collectors.toList());
        System.out.println("取出某一属性的结果值为：" + ids);

        System.out.println("《《《《《《《《《《》》》》》》》》");

        System.out.println("使用lambda表达式遍历循环List");
        userList.stream().forEach(user ->{
            System.out.println("user值："+user.toString());
        });

        System.out.println("《《《《《《《《《《》》》》》》》》");

        System.out.println("使用lambda表达式过滤某一个属性的值");
        System.out.println("过滤前userList的值:" + userList);
        userList = userList.stream().filter(s -> s.getAge() > 24).collect(Collectors.toList());
        System.out.println("过滤后userList的值:" + userList);



        //正常调用写法
        Son son = new Son();
        son.test("zzz");

        //多态，创建父类的写法
        MyInterface myInterface = new Son();
        myInterface.test("aaa");

        //匿名内部类的写法
        MyInterface myInterface1 = new MyInterface() {
            @Override
            public void test(String a) {
                System.out.println("匿名内部类的写法"+a);
            }
        };
        myInterface1.test("eee");

        //lambda 写法
        MyInterface myInterface2 = a ->{
            System.out.println("lambda写法");
        };
        myInterface2.test("rrr");




        Test test = new Test();
        test.method(son);
        test.method(myInterface);
        test.method(myInterface1);
        test.method(myInterface2);
        test.method(s->{
            System.out.println("yyy");
        });
    }
}


/**
 * 内部类，重写MyInterface接口
 */
class Son implements MyInterface{
    public void test(String a) {
        System.out.println("子类重写方法"+a);
    }
}
