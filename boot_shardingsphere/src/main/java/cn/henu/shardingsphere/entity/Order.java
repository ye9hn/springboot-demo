package cn.henu.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@TableName("t_order")
@Data
public class Order {
    //如果项目整合了shading-jdbc，当配置了sharding-jdbc的分布式序列时，当前注解自动使用sharding-jdbc的分布式序列
    // 如果项目没有整合sharding-jdbc，当前注解直接使用数据库自增ID策略
    @TableId(type = IdType.AUTO)
   // @TableId(type = IdType.ASSIGN_ID)//这个注解使用分布式ID（mybatis-plus的分布式ID也是基于雪花算法，优先级高于其他的生成策略）
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal amount;
}
