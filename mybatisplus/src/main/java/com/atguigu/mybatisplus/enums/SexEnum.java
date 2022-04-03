package com.atguigu.mybatisplus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

/**
 * Date:2022/2/15
 * Author:ybc
 * Description:
 */
@Getter
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    //将注解所标识的属性的值代替枚举对象存储到数据库中(否则存的就是'MALE'字符串)
    @EnumValue
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName) {
        this.sex = sex;
        this.sexName = sexName;
    }
}
