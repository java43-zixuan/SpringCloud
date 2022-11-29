package com.example.userservice1.entity;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@TableName(value ="user")
@Data
@ExcelIgnoreUnannotated//忽略不加@ExcelProperty的字段进行导出
public class User extends BaseEntity implements Serializable {

    /**
     * 用户名称
     */
    @TableField(value = "user_name")
    @ExcelProperty(value = "姓名",index = 0)
    private String userName;

    /**
     * 密码(MD5加密)
     * @TableField(value = “xxxx”,updateStrategy = FieldStrategy.IGNORED)  如果要存入空值的话
     */
    @TableField(value = "user_pwd")
    private String userPwd;

    /**
     * 学号
     */
    @TableField(value = "student_num")
    @ExcelProperty(value = "学号",index = 1)
    private String studentNum;

    /**
     * 年龄
     */
    @TableField(value = "age")
    @ExcelProperty(value = "年龄",index = 2)
    private String age;

    /**
     * 班级
     */
    @TableField(value = "class_name")
    @ExcelProperty(value = "班级",index = 3)
    private String className;

    /**
     * 锁定
     */
    @TableField(value = "user_lock")
    private String userLock;

    public User(){}


    public User(String userName,String age,String studentNum,String className) {
        this.userName = userName;
        this.age = age;
        this.studentNum = studentNum;
        this.className = className;

    }

}
