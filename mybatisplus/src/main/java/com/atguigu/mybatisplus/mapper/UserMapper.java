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
    //@MapKey("id") 这里的方法报错.暂时不知道为什么需要 MapKey???
    Map<String, Object> selectMapById(Long id);

    /**
     * 通过年龄查询用户信息并分页
     * @param page MyBatis-Plus所提供的分页对象，必须位于第一个参数的位置
     * @param age
     * @return
     */
    //Page<User> selectPageVo(@Param("page") Page<User> page, @Param("age") Integer age);

}
