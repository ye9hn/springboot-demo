package com.henu.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ProductDao {
    @Update("update phone set stock=stock-1 where pid=#{pId} && stock>0")
    int seckillPhone(int pId);

    @Select("select stock from phone where pid=#{pId}")
    int selectPhoneStock(int pId);
}
