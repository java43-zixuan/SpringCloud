package com.example.userservice3.service.impl;



import com.example.userservice3.entity.BaseEntity;
import com.example.userservice3.mapper.BaseMapper;
import com.example.userservice3.service.IBaseService;
import org.springframework.stereotype.Service;

/**
 * 基于Mybatis-Plus的通用Service，提供Service层实体Entity的CURD操作<br/>
 * 具体用法参见<a href=" https://baomidou.com/pages/49cc81/#service-crud-接口">Mybatis-Plus的通用Service</a><br/>
 * 可在此扩展Service的公共方法<br/>
 * @param <M> 通用Mapper
 * @param <T> 数据库实体Entity
 *
 * @author: web
 * @date: 2022-07-12
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> extends com.baomidou.mybatisplus.extension.service.impl.ServiceImpl<M,T> implements IBaseService<T> {

}
