package com.example.common.dto;

import lombok.Data;

@Data
public class UserDto extends BaseDto{

    private Integer id;

    private String userName;

    private String age;

    private String studentNum;

    private String className;

    private String userPwd;
}
