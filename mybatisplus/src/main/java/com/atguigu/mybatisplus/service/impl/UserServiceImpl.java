package com.atguigu.mybatisplus.service.impl;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.atguigu.mybatisplus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 1.自定义接口实现类 继承了 自定义接口(自定义接口继承了IService接口),所以要实现IService的所有抽象类
 * 2.所以要让实现类继承ServiceImpl(ServiceImpl实现了IService)
 * 3.以上操作的目的是,既能使用IService提供的方法,也能自定义方法(UserService中声明即可)
 */
@Service //只在实现类上添加@Service.使之称为组件
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
}
