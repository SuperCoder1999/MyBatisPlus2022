package com.atguigu.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@MapperScan("com.atguigu.admin.mapper") 简化，
// 其他的接口就可以不用标注@Mapper注解,意思是com.atguigu.admin.mapper包下面的所有接口都是被标注了@Mapper的.实际上,就是扫描Mapper类的吧?
@MapperScan("com.atguigu.mybatisplus.mapper")
@SpringBootApplication
public class MybatisplusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisplusApplication.class, args);
    }

}
