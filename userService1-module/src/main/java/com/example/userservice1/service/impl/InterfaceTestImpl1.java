package com.example.userservice1.service.impl;

import com.example.userservice1.service.InterfaceTest;
import org.springframework.stereotype.Service;

@Service
public class InterfaceTestImpl1 implements InterfaceTest {
    @Override
    public void fun1() {
        System.out.println("我是impl1");
    }
}
