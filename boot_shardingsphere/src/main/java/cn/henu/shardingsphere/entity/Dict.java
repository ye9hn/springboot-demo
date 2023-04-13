package cn.henu.shardingsphere.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_dict")
public class Dict {
    @TableId(type = IdType.ASSIGN_ID)
    private  Long id;

    private String dictType;
}
