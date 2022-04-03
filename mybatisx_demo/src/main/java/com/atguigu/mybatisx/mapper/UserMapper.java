package com.atguigu.mybatisx.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.atguigu.mybatisx.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 33115
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2022-04-03 18:17:27
* @Entity com.atguigu.mybatisx.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    //增加操作
    int insertSelective(User user);

    //删除操作
    int deleteByUidAndAgeAndEmail(@Param("uid") Long uid, @Param("age") Integer age, @Param("email") String email);

    //更改操作
    int updateAgeAndSexAndEmail(@Param("age") Integer age, @Param("sex") Integer sex, @Param("email") String email);

    //查询操作
    List<User> selectAgeAndSexByUidBetween(@Param("beginUid") Long beginUid, @Param("endUid") Long endUid);

    List<User> selectAllByAgeOrderByAgeDesc(@Param("age") Integer age);
}




