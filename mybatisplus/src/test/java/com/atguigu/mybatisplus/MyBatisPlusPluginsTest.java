package com.atguigu.mybatisplus;

import com.atguigu.mybatisplus.mapper.ProductMapper;
import com.atguigu.mybatisplus.mapper.UserMapper;
import com.atguigu.mybatisplus.pojo.Product;
import com.atguigu.mybatisplus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Description:测试分页插件 乐观锁 插件
 */
@SpringBootTest
public class MyBatisPlusPluginsTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 测试 分页插件功能
     */
    @Test
    public void testPage(){
        //Page参数:当前页码,每一页的数据条数
        //SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0 LIMIT ?,?
        //Parameters: 3(Long)[每一页容量*(是由当前页码-1)], 3(Long)
        //如果访问的第一页可以用: .... LIMIT ?
        Page<User> page = new Page<>(2, 3);
        userMapper.selectPage(page, null);//查询所有数据
        //查询出的数据都封装到上面创建的Page对象中了.(怎么封装的暂时不清楚)
        System.out.println("数据<List>: " + page.getRecords());//返回值为list,page中的数据
        System.out.println("总页数: " + page.getPages());
        System.out.println("总记录数: " + page.getTotal());//可以代替select count(*)使用
        System.out.println("是否有下一页: " + page.hasNext());
        System.out.println("是否有前一页: " + page.hasPrevious());
        System.out.println("当前页码: " + page.getCurrent());
        System.out.println("每一页容量: " + page.getSize());
    }

    @Test
    public void testPageVo(){
        Page<User> page = new Page<>(1, 3);
        userMapper.selectPageVo(page, 20);
        System.out.println("数据<List>: " + page.getRecords());//返回值为list,page中的数据
        System.out.println("总页数: " + page.getPages());
        System.out.println("总记录数: " + page.getTotal());//可以代替select count(*)使用
        System.out.println("是否有下一页: " + page.hasNext());
        System.out.println("是否有前一页: " + page.hasPrevious());
        System.out.println("当前页码: " + page.getCurrent());
        System.out.println("每一页容量: " + page.getSize());
    }

    /**
     * 测试乐观锁
     */
    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testProduct01(){
        //小李查询商品价格
        Product productLi = productMapper.selectById(1);
        System.out.println("小李查询的商品价格："+productLi.getPrice());
        //小王查询商品价格
        Product productWang = productMapper.selectById(1);
        System.out.println("小王查询的商品价格："+productWang.getPrice());
        //小李将商品价格+50
        productLi.setPrice(productLi.getPrice()+50);
        productMapper.updateById(productLi);
        //小王将商品价格-30
        productWang.setPrice(productWang.getPrice()-30);
        int result = productMapper.updateById(productWang);
        if(result == 0){
            System.out.println("操作失败~~~~");
            //操作失败，重试
            Product productNew = productMapper.selectById(1);
            productNew.setPrice(productNew.getPrice()-30);
            productMapper.updateById(productNew);
        }
        //老板查询商品价格
        Product productLaoban = productMapper.selectById(1);
        System.out.println("老板查询的商品价格："+productLaoban.getPrice());
        //没有@Version注解结果为70
        //没有if重操做,结果为150.因为小王没有执行成功
        //有了@Version和if  结果为120
    }

    /*
    小李:
    取出version=0,price=100

    sql: Preparing: UPDATE t_product SET name=?, price=?, version=? WHERE id=? AND version=?
    Parameters: 外星人笔记本(String), 150(Integer), 1(Integer), 1(Long), 0(Integer)
    Updates: 1 (判断当前版本0和数据库中版本0相等===>小李更改price为150,更改version为1.

    小王:
    取出version=0,price=100

    sql: UPDATE t_product SET name=?, price=?, version=? WHERE id=? AND version=?
    Parameters: 外星人笔记本(String), 70(Integer), 1(Integer), 1(Long), 0(Integer)
    Updates: 0 (判断当前版本0和数据库中版本1不相等,小李操作失败.

    小王:
    根据if判断再次取出数据
    (version=0,price=150)
    进行更改,更改后,原数据库中版本也为1==>小王更改price为120,更改version为2.
     */
}
