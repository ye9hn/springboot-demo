package com.henu.mybat.dao;

import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface PhoneMapper {
    @Update("update phone set stock=stock-1 where pid=#{pId} && stock>0")
    int seckillPhone(String pId);

    @Select("select stock from phone where pid=#{pId}")
    int selectPhoneStock(String pId);
}
