package com.example.userservice3.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name="role")
@TableName("role")
public class Role implements Serializable {
    private static final long serialVersionUUID = 1L;

    @Id
    @TableId
    private String id;

    @Column(length = 255,columnDefinition = "VARCHAR(255) comment '角色名称' DEFAULT '' ")
    private String roleName;
}
