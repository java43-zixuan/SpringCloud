package com.example.userservice3.service.impl;

import com.example.userservice3.entity.User;
import com.example.userservice3.mapper.UserMapper;
import com.example.userservice3.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements IUserService {
}
