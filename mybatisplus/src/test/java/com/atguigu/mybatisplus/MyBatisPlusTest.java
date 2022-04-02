package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于测试 继承BaseMapper后,使用BaseMapper中的方法
 */
@SpringBootTest
public class MyBatisPlusTest {

    //这里报错的原因是,使用@Mapper注解或@MapperScan注解,只是将UserMapper接口的代理类放进IOC容器.而非UserMapper接口本身
    //在UserMapper接口上@Repository可以解决报错
    @Autowired
    private UserMapper userMapper;


    /**
     * 测试批量查询
     */
    @Test
    public void testSelectList(){
        //通过条件构造器查询一个list集合.若没有条件，则可以设置null为参数
        //返回的list中的元素类型/操作的表,也是由继承BaseMapper时填的泛型决定的
        List<User> list = userMapper.selectList(null);
        //拼接的sql是: SELECT id,name,age,email FROM user
        /*
        拼接的原理: Scan Entity就是扫描实体(pojo对象),抽取其中的属性.分析操作的表是谁.
        根据调用的方法,拼接好,放入Mybatis容器中
         */
        list.forEach(System.out::println);
        //这个输出方法可能和集合中的Iteratable有关系???
    }

    /**
     * 测试添加
     */
    @Test
    public void testInsert(){
        //实现新增用户信息
        //INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        //user.setId(100L);
        user.setName("张三");
        user.setAge(23);
        user.setEmail("zhangsan@atguigu.com");
        int result = userMapper.insert(user);
        System.out.println("result:"+result);//result:1
        System.out.println("id:"+user.getId());//id:1510254364610646017(雪花算法确定的id)
    }

    /**
     * 测试删除方法
     */
    @Test
    public void testDelete(){
        //1. 通过id删除用户信息
        //DELETE FROM user WHERE id=?
        /*int result = userMapper.deleteById(1510254364610646017L);
        System.out.println("result:"+result);*/

        //2.根据map集合中所设置的条件删除用户信息
        //DELETE FROM user WHERE name = ? AND age = ?
        /*Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 23);
        int result = userMapper.deleteByMap(map);
        System.out.println("result:"+result);//结果为0.没有成功,因为数据库中没有该条数据
*/

        //3.通过多个id实现批量删除
        //DELETE FROM user WHERE id IN ( ? , ? , ? )
        List list = Arrays.asList(1, 2L, 3);//这里传入int/long都可以
        //(java中的类型应该只是为了能放得下该数字大小.实际到了sql中,就是看数值是否一致,不看类型)
        int result = userMapper.deleteBatchIds(list);
        System.out.println("result:"+result);
    }

    /**
     * 测试修改方法
     */
    @Test
    public void testUpdate(){
        //修改用户信息
        //UPDATE user SET name=?, email=? WHERE id=?
        User user = new User();
        user.setId(4L);
        user.setName("李四");
        user.setEmail("lisi@atguigu.com");
        int result = userMapper.updateById(user);
        System.out.println("result:"+result);
    }

    /**
     * 测试查询方法(根据条件查询,很有可能查询多个数据,所以返回的是集合)
     */
    @Test
    public void testSelect(){
        //1. 通过id查询用户信息
        //SELECT id,name,age,email FROM user WHERE id=?
        /*User user = userMapper.selectById(1L);
        System.out.println(user);*/

        //2.根据多个id查询多个用户信息
        //SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        /*List<Long> list = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(list);
        users.forEach(System.out::println);*/

        //3.根据map集合中的条件查询用户信息
        //SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        /*Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 20);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);*/

        //4.查询所有数据(就是用条件查询时,填null)
        //SELECT id,name,age,email FROM user
        /*List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);*/


        /**
         * 放弃 BaseMapper中提供的方法.使用配置文件Mapper.xml和Mapper接口,实现查询
         */
        Map<String, Object> map = userMapper.selectMapById(1L);
        System.out.println(map);
    }

}
