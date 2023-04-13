package cn.henu.shardingsphere;

import cn.henu.shardingsphere.entity.Order;
import cn.henu.shardingsphere.entity.User;
import cn.henu.shardingsphere.mapper.OrderMapper;
import cn.henu.shardingsphere.mapper.UserMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class ReadWriteTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Test
    public void testInsertOrderAndUser(){
        User user=new User();
        user.setUname("强哥");
        userMapper.insert(user);

        Order order=new Order();
        order.setOrderNo("ATGUIGU0001");
        order.setUserId(user.getId());
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }
}
