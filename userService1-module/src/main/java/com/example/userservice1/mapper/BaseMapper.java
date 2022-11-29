package com.example.userservice1.mapper;

/**
 * 基于Mybatis-plus的BaseMapper，无需编写mapper.xml文件，即可获得基础的CRUD功能<br/>
 * 可参考<a href=https://baomidou.com/pages/49cc81/#mapper-层-选装件>Mapper层-选装件</a>
 * <a href=https://gitee.com/baomidou/mybatis-plus-samples/tree/master/mybatis-plus-sample-deluxe>自定义BaseMapper</a>实现，具体步骤为：<br/>
 * 1、BaseMapper中定义方法<br/>
 * 2、新建类继承AbstractMethod，实现具体方法<br/>
 * 3、通过自定义SQL注入DefaultSqlInjector加入该方法，实现自定义方法注入<br/>
 * @param <T> 数据库实体Entity
 * @author: web
 * @date: 2022-07-12
 *
 **/
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {


}
