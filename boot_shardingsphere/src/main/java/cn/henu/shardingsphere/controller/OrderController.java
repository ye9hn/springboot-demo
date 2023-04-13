package cn.henu.shardingsphere.controller;

import cn.henu.shardingsphere.entity.*;
import cn.henu.shardingsphere.mapper.DictMapper;
import cn.henu.shardingsphere.mapper.OrderItemMapper;
import cn.henu.shardingsphere.mapper.OrderMapper;
import cn.henu.shardingsphere.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private DictMapper dictMapper;

    @GetMapping("/insert")
    public void inserOrder(){
        User user=new User();
        user.setUname("强哥");
        userMapper.insert(user);

        Order order=new Order();
        order.setOrderNo("ATGUIGU0001");
        order.setUserId(user.getId());
        order.setAmount(new BigDecimal(100));
        orderMapper.insert(order);
    }
    @GetMapping("/database-strategy")
    public void insertDatabaseStrategy(){
        for (long i = 0; i < 4; i++) {
            Order order=new Order();
            order.setOrderNo("ATGUIGU0001");
            order.setUserId(i+1);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    @GetMapping("/table-strategy")
    public void insertTableStrategy(){
        for (long i = 100; i < 108; i++) {
            Order order=new Order();
            order.setOrderNo("ATGUIGU"+i);
            order.setUserId(1L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }

        for (long i = 108; i < 112; i++) {
            Order order=new Order();
            order.setOrderNo("ATGUIGU"+i);
            order.setUserId(2L);
            order.setAmount(new BigDecimal(100));
            orderMapper.insert(order);
        }
    }

    /**
     * 水平分片；查询所有的数据
     * @return
     */
    @GetMapping("/selectallorder")
    public List<Order> selectALLOrder(){
        return orderMapper.selectList(null);
    }


    @GetMapping("/insert-orderitem")
    public void insertOrderItem(){
        for (long i = 100; i < 108; i++) {
            Order order=new Order();
            order.setOrderNo("ATGUIGU"+i);
            order.setUserId(1L);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrderNo("ATGUIGU"+i);
                orderItem.setUserId(1L);
                orderItem.setPrice(new BigDecimal(10));
                orderItem.setCount(2);
                orderItemMapper.insert(orderItem);
            }
            orderMapper.insert(order);
        }

        for (long i = 108; i < 116; i++) {
            Order order=new Order();
            order.setOrderNo("ATGUIGU"+i);
            order.setUserId(2L);
            for (int j = 0; j < 3; j++) {
                OrderItem orderItem=new OrderItem();
                orderItem.setOrderNo("ATGUIGU"+i);
                orderItem.setUserId(2L);
                orderItem.setPrice(new BigDecimal(15));
                orderItem.setCount(6);
                orderItemMapper.insert(orderItem);
            }
            orderMapper.insert(order);
        }
    }

    /**
     * 获取所有的订单总价格
     * @return
     */
    @GetMapping("/orderamount")
    public List<OrderVO> getOrderAmount(){
        return orderMapper.getOrderAmount();
    }

    /**
     * 广播表
     */
    @GetMapping("/insertBroadcast")
    public void inserBroadCast(){
        Dict dict=new Dict();
        dict.setDictType("type00001");
        dictMapper.insert(dict);
    }

    @GetMapping("/getbroadcast")
    public List<Dict> getBroadCast(){
       return dictMapper.selectList(null);
    }
}
