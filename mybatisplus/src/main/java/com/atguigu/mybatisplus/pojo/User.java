package com.atguigu.mybatisplus.pojo;

import com.atguigu.mybatisplus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

/**
 * Date:2022/2/12
 * Author:ybc
 * Description:
 */

/*
@Data代替了:
@NoArgsConstructor
@AllArgsConstructor(没有有参构造器)
@Getter
@Setter
@EqualsAndHashCode
 */

/*1.mybatis-plus能自动找到操作表:
    通过继承BaseMapper时填写的泛型决定的(改表名为t_user就不能匹配到mybatis_plus.user)
@TableName设置实体类所对应的表名(就能改变匹配的表名)*/
//@TableName("t_user")
@Data //通过lambok简化实体类开发
public class User {

    //只测试com.atguigu.mybatisplus.MyBatisPlusTest.testSelectList时,用到的字段
    /*private Long id; //因为数据库中使用的是bigint。按照数据大小范围来说应该用long


    private String name;

    private Integer age;

    private String email;*/

    /*
    关于雪花算法:
    1.mybatis-plus会用雪花算法为主键的值,找到一个唯一的数字
    2.mybatis-plus认为默认的主键叫id(更改为uid,如果没有声明,就不管主键的值了)
     */

    //将属性所对应的字段指定为主键
    //1.@TableId注解的value属性用于指定主键的字段
    // (不带value时,User实体类在数据库表映射时就是当前属性名.)
    //2.@TableId注解的type属性设置主键生成策略,默认是IdType.ASSIGN_ID(雪花算法),即使uid是自增,也用雪花算法
    //  type = IdType.AUTO,开启主键自增.不过我的数据库中是以最大id进行增长的.
    //@TableId(value = "uid", type = IdType.AUTO)
    @TableId("uid")//数据库表的字段名为uid,User实体类中用的是id
    private Long id; //因为数据库中使用的是bigint。按照数据大小范围来说应该用long
    //拼接的sql: SELECT uid AS id,name,age,email FROM t_user

    //指定属性所对应的字段名
    @TableField("user_name")
    private String name;
    //拼接后: SELECT uid AS id,user_name AS name,age,email FROM t_user

    private Integer age;

    private String email;

    //学习通用枚举时,用到的
    private SexEnum sex;

    //逻辑删除: 删除的属性还能恢复
    @TableLogic
    private Integer isDeleted;
    //拼接sql: UPDATE t_user SET is_deleted=1 WHERE uid IN ( ? , ? , ? ) AND is_deleted=0
    //添加逻辑删除后查询的sql: SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0

}
