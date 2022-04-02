package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * Date:2022/2/12
 * Author:ybc
 * Description:
 */
@Data //通过lambok简化实体类开发
/*
代替了:
@NoArgsConstructor
@AllArgsConstructor(没有有参构造器)
@Getter
@Setter
@EqualsAndHashCode
 */
//设置实体类所对应的表名
//@TableName("t_user")
public class User {

    //只测试com.atguigu.mybatisplus.MyBatisPlusTest.testSelectList时,用到的字段
    /*private Long id; //因为数据库中使用的是bigint。按照数据大小范围来说应该用long


    private String name;

    private Integer age;

    private String email;*/


    //将属性所对应的字段指定为主键
    //@TableId注解的value属性用于指定主键的字段
    //@TableId注解的type属性设置主键生成策略
    //@TableId(value = "uid", type = IdType.AUTO)
    //@TableId("uid")
    private Long id; //因为数据库中使用的是bigint。按照数据大小范围来说应该用long

    //指定属性所对应的字段名
    //@TableField("user_name")
    private String name;

    private Integer age;

    private String email;

    //private SexEnum sex;

    /*@TableLogic
    private Integer isDeleted;*/

}
