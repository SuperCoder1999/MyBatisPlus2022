package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Date:2022/2/15
 * Author:ybc
 * Description:
 */
@Service
@DS("master") //指定操作的数据源,value就是为数据源设定的名字master或slave_1
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
