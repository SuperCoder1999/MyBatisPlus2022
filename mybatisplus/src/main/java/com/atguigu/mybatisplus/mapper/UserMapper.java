package com.atguigu.mybatisplus.mapper;

import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Date:2022/2/12   BaseMapper 是由 mybatis-plus提供的接口.其中有很多可以直接用的方法
 * (这些方法的实现是由谁完成的??BaseMapper.xml吗?)
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据id查询用户信息为map集合
     * 放弃 BaseMapper中提供的方法.使用配置文件Mapper.xml和Mapper接口,实现查询
     */
    /*将表中的数据以map集合的方式查询，一条数据对应一个map；若有多条数据，就会产生多个map集合，并
    且最终要以一个map的方式返回数据，此时需要通过@MapKey注解设置map集合的键，值是每条数据所对应的
    map集合*/
    @MapKey("id")
    Map<String, Object> selectMapById(Long id);

    /**
     * 自定义分页功能 : 通过年龄查询用户信息并分页
     * @param page MyBatis-Plus所提供的分页对象，必须位于第一个参数的位置(用于将查询数据封装到Page对象中)
     *             (为什么借Page就可以完成分页,这个不清楚???)
     */
    //@Param规定传参的名称
    Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);

}
