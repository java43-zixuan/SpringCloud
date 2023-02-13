package com.example.userservice2.test7;

import lombok.Data;

@Data
public class User {

    private int age;
    private String name;

    public User(String name,int age){
        this.name = name;
        this.age = age;
    }
}
