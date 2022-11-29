package com.example.userservice1.entity;

import lombok.Data;

@Data
public class AdmissionCard {

    /**
     * 准考证号
     */
    private String no;
    /**
     * 考生姓名
     */
    private String name;
    /**
     * 考生性别
     */
    private String sex;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 学习工作单位
     */
    private String school;
    /**
     * 报考单位
     */
    private String enterSchool;
    /**
     * 报考专业
     */
    private String major;
    /**
     * 报考点
     */
    private String enterName;
    /**
     * 考试地点
     */
    private String examAddress;
    /**
     * 考生头像
     */
    private String studentImg;
}
