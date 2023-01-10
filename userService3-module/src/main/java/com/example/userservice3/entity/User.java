package com.example.userservice3.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="user")
@Data
public class User extends BaseEntity implements Serializable {
    private static final long serialVersionUUID = 1L;

    @TableField(value = "name")
    private String name;

    @TableField(value = "pwd")
    private String pwd;
}
