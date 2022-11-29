package com.example.userservice1.service.impl;

import com.example.userservice1.mapper.UserMapper;
import com.example.userservice1.entity.User;
import com.example.userservice1.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
}
