package com.example.userservice1.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MybatisPlusInterceptor是一系列的实现InnerInterceptor的拦截器链，也可以理解为一个集合。可以包括如下的一些拦截器：
 *
 * 自动分页: PaginationInnerInterceptor（最常用）
 * 多租户: TenantLineInnerInterceptor
 * 动态表名: DynamicTableNameInnerInterceptor
 * 乐观锁: OptimisticLockerInnerInterceptor
 * sql性能规范: IllegalSQLInnerInterceptor
 *
 * Page<SysUserOrg> page = new Page<> (pageNum,pageSize);   //查询第pageNum页，每页pageSize条数据
 * //将分页参数page作为Mybatis或Mybatis Plus的第一个参数传入持久层函数，即可完成分页查询
 * return mySystemMapper.selectUser(page, 其他参数 );
 */

@Configuration
public class MybatisConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //向Mybatis过滤器链中添加分页拦截器
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        return interceptor;
    }

}
