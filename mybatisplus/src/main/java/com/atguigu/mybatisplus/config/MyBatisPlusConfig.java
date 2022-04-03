package com.atguigu.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Date:2022/2/14
 * Author:ybc
 * Description:
 */
//@MapperScan("com.atguigu.admin.mapper") 简化，
// 其他的接口就可以不用标注@Mapper注解,意思是com.atguigu.admin.mapper包下面的所有接口都是被标注了@Mapper的.实际上,就是扫描Mapper类的吧?
@Configuration
//扫描mapper接口所在的包
@MapperScan("com.atguigu.mybatisplus.mapper")
public class MyBatisPlusConfig {

    /**
     * 拦截器: 先写了sql查询功能.拦截器拦截查询,在实现查询的基础上实现分页功能
     * @return
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){

        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        //添加分页插件.参数选择MySQL数据库,因为不同的数据库的分页功能实现方式不一样
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        //添加乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

}
