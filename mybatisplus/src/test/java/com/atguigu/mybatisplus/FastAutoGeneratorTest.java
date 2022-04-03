package com.atguigu.mybatisplus;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * Description:代码生成器
 */
public class FastAutoGeneratorTest {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://123.57.92.107:3306/mybatis_plus?characterEncoding=utf-8&userSSL=false", "root", "N331150871")
                .globalConfig(builder -> {
                    builder.author("atguigu") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            //.fileOverride() // 覆盖已生成文件
                            .outputDir("D://mybatis_plus"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.atguigu") // 设置父包名
                            .moduleName("mybatisplus") // 设置父包模块名 最终生成的所有内容都在com.atguigu.mybatisplus下
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://mybatis_plus")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("t_user") // 设置需要生成的表名(针对哪个表进行代码生成)
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀(这个"过滤"不清楚什么意思???).可能是针对表t_Xxx创建Xxx.java
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }

}
