package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.service.ProductService;
import com.atguigu.mybatisplus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MybatisPlusDatasourceApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    /**
     * 1.从master数据源获取id=1的记录
     * 2.从slaver_1数据源中获取id=1的记录
     */
    @Test
    public void test(){
        System.out.println(userService.getById(1));//User(uid=1, userName=Jone, age=18, sex=0, email=test1@baomidou.com, isDeleted=1)
        System.out.println(productService.getById(1));//Product(id=1, name=外星人笔记本, price=100, version=0)
    }

}
