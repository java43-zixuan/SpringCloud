package com.example.userservice3.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 公共实体，提供create_time、update_time、del_flag属性
 * @author: web
 * @date: 2022-07-12
 **/

@Data
public class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * IdType.ASSIGN_ID : 使用雪花算法.
     * IdType.ASSIGN_UUID : 不含中划线的UUID.
     * IdType.AUTO : 数据库 ID 自增
     */
    @TableId(value="id", type=IdType.AUTO)
    @ApiModelProperty(value = "主键ID，使用雪花算法")
    private Integer id;
    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    @TableField(value="create_time", fill=FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    @TableField(value="update_time", fill=FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 是否删除 1：已删除  0：正常<br/>
     * <a href="https://baomidou.com/pages/6b03c5/">逻辑删除参考文档</a><br/>
     * 只对自动注入的 sql 起效:
     */
    @TableLogic
    @TableField(value="del_flag", fill=FieldFill.INSERT)
    private Integer delFlag;


}
