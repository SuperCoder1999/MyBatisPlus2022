package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * 测试Wrapper条件构造器,是用于BaseMapper中的
 * 1.所有的修改中,QueryWrapper可以代替UpdateWrapper(不清楚,User设置为null时,QueryWrapper能否代替)
 * 2.LambdaQueryWrapper和LambdaUpdateWrapper 与  QueryWrapper和UpdateWrapper  有区别
 */
@SpringBootTest
public class MyBatisPlusWrapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询  条件封装 : 用户名包含a，年龄在20到30之间，邮箱信息不为null
     */
    @Test
    public void test01(){

        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        //构造条件封装 (泛型填写用到的表)
        //查询用户名包含a，年龄在20到30之间，邮箱信息不为null的用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a")//填写的是表的字段名,不是实体类的字段名.查询用户名包含a
                .between("age", 20, 30)//或者用.gt()大于等于/.le()小于等于[gt/ge/lt/le这几个方法不清楚].年龄在20到30之间
                .isNotNull("email");//邮箱信息不为null
        //链式结构:一个方法返回值为当前类对象.则这个方法的结果(这个类对象)还能再调用其他方法
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 查询  条件封装: 按照年龄的降序排序，若年龄相同，则按照id升序排序
     */
    @Test
    public void test02(){
        //查询用户信息，按照年龄的降序排序，若年龄相同，则按照id升序排序
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 ORDER BY age DESC,uid ASC
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("age")
                .orderByAsc("uid");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 删除  条件封装: 邮箱地址为null  (也用查询的条件封装: queryWrapper)
     */
    @Test
    public void test03(){
        //删除邮箱地址为null的用户信息
        //UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");
        int result = userMapper.delete(queryWrapper);
        System.out.println("result:"+result);
    }

    /**
     * 修改  条件封装: 修改（年龄大于20并且用户名中包含有a）或邮箱为null的用户信息
     * 1. 修改的方法userMapper.update(user, queryWrapper);其中user是封装好的要改的数据(新的数据中null不会影响原数据)
     */
    @Test
    public void test04(){
        //将（年龄大于20并且用户名中包含有a）或 (邮箱为null的用户信息修改)
        //UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)//and优先级比or高
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", 20)//(年龄大于20
                .like("user_name", "a")//并且用户名中包含有a)
                .or() //前半部分和后半部分进行 或
                .isNull("email");//(邮箱为null的用户信息修改)
        User user = new User();
        user.setName("小明");
        user.setEmail("test@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result:"+result);
    }

    /**
     * 修改 (UpdateWrapper)
     * 修改的方法userMapper.update(user, UpdateWrapper);其中user可以填写null.
     * 此时,在UpdateWrapper中设置要修改的内容即可
     */
    @Test
    public void test08(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        //sql:UPDATE t_user SET user_name=?,email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        //Parameters: 小黑(String), abc@atguigu.com(String), %a%(String), 20(Integer)
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));
        //设置要修改的内容
        updateWrapper.set("user_name", "小黑").set("email","abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result："+result);
    }

    /**
     * 修改,使用lambda表达式  条件封装: 修改用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息
     * 1. 和上面test04的条件不同
     */
    @Test
    public void test05(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        //lambda中的条件优先执行(为什么先执行,不清楚
        //UPDATE t_user SET user_name=?, email=? WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a")//用户名中包含有a
                .and(i->i.gt("age",20).or().isNull("email"));//i就是一个Wrapper的实现类
        User user = new User();
        user.setName("小红");
        user.setEmail("test@atguigu.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result:"+result);
    }

    /**
     * 指定查询的字段(而不是全部查询出来)
     */
    @Test
    public void test06(){
        //查询用户的用户名、年龄、邮箱信息
        //SELECT user_name,age,email FROM t_user WHERE is_deleted=0
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name", "age", "email");//参数为 表中的字段名
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        maps.forEach(System.out::println);
    }

    /**
     * 子查询
     */
    @Test
    public void test07(){
        //查询id小于等于100的用户信息
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (uid IN (select uid from t_user where uid <= 100))
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //子查询,第一个参数为子查询的字段(就是内部查询提供给外部查询的数据)
        queryWrapper.inSql("uid", "select uid from t_user where uid <= 100");
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 模拟,开发中组装条件的情况(较复杂)
     */
    @Test
    public void test09(){
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            //isNotBlank判断某个字符创是否不为空字符串、不为null、不为空白符
            queryWrapper.like("user_name", username);
        }
        if(ageBegin != null){
            queryWrapper.ge("age", ageBegin);
        }
        if(ageEnd != null){
            queryWrapper.le("age", ageEnd);
        }
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 模拟,开发中组装条件的情况,test09 的升级版(将判断也封装到Wrapper中)
     */
    @Test
    public void test10(){
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //之前用的每一个Wrapper,都有两种重载方法,即带有condition和不带有condition的方法.
        queryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /**
     * 模拟,开发中组装条件的情况,test10 的升级版(不需要输入表的column列名)
     */
    @Test
    public void test11(){
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 AND (user_name LIKE ? AND age <= ?)
        String username = "a";
        Integer ageBegin = null;
        Integer ageEnd = 30;
        //创建的是Wrapper下的LambdaQueryWrapper对象
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //这里的SFunction不清楚是什么.不过还算好记-->类名::getXxx
        queryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> list = userMapper.selectList(queryWrapper);
        list.forEach(System.out::println);
    }

    /*
    QueryWrapper中的ge()等方法,可以用condition,column和value组合
    LambdaQueryWrapper中的ge()等方法,和QueryWrapper中的ge()方法有区别,不能用column了.
     */

    /**
     * 使用LambdaUpdateWrapper代替UpdateWrapper
     */
    @Test
    public void test12(){
        //将用户名中包含有a并且（年龄大于20或邮箱为null）的用户信息修改
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a")
                .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail));
        updateWrapper.set(User::getName, "小黑").set(User::getEmail,"abc@atguigu.com");
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result："+result);
    }
    /*
    LambdaUpdateWrapper中的set()等方法,和UpdateWrapper中的ge()方法有区别,不能用column了.
     */


    /*
    返回值 List<User> || List<Map<String,Object>> || Map<String, Object> 的区别
    1. test02中List<User> list = userMapper.selectList(queryWrapper);.返回的是list集合,每个元素是一个User对象
    输出形式是:   User(id=4, name=小明, age=21, email=test@atguigu.com, isDeleted=0)
                User(id=6, name=Jone, age=18, email=test1@baomidou.com, isDeleted=0)
                User(id=7, name=Jone, age=18, email=test1@baomidou.com, isDeleted=0)
          是由User实体类中的toString()方法决定的
     2. test06中List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
     返回的形式是: 每个User对象都被封装到Map集合中,封装的方式是:
        将每一个字段及其值放入Map的key-value中,多个key-value组成一个Map即User对象(根据查询的字段,key-value比真实的User对象中的属性少)
      输出形式:
                {user_name=小明, age=21, email=test@atguigu.com}
                {user_name=Billie, age=24, email=test5@baomidou.com}
                {user_name=Jone, age=18, email=test1@baomidou.com}
            是先遍历List集合, 取出每一个Map,将一个Map作为List的元素.
      3. 自定义sql查询: Map<String, Object> map = userMapper.selectMapById(1L);
      返回形式是: 以@MapKey决定的Map集合的key.将User对象作为对应key的value
        输出形式:
        {1={name=Jone, id=1, age=18, email=test1@baomidou.com}, 2={name=Jack, id=2, age=20, email=test2@baomidou.com}, 3={name=Tom, id=3, age=28, email=test3@baomidou.com}}
        最外面的{}就是代表一个Map,1,2..代表key.后面的value就是User对象
     */
}
