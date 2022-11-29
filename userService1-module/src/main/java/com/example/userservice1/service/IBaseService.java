package com.example.userservice1.service;


import com.example.userservice1.entity.BaseEntity;

/**
 * 基于Mybatis-plus的顶级Service接口
 * @param <T> 数据库实体Entity
 * @author ZH
 */
public interface IBaseService<T extends BaseEntity> extends com.baomidou.mybatisplus.extension.service.IService<T> {

}
