package cn.henu.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("t_order_item")
@Data
public class OrderItem {
    @TableId(type= IdType.AUTO)
    private  Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal price;
    private Integer count;
}
