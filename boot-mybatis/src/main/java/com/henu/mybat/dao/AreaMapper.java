package com.henu.mybat.dao;

import com.henu.mybat.entity.Area;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AreaMapper {
    //@Select("select area_id as areaId,area_name as areaName from tb_area where area_id=1")//不开启驼峰命名就要使用和属性一样的别名
    //@Select("select * from tb_area limit 0,20")
    List<Area> selectArea(int areaId);

    @MapKey("areaId")
    Map<String, Area> selectMap(int areaId);

    int selectAll();

    void updateAreaNameByAreaId(@Param("areaId") int areaId, @Param("areaName") String areaName);
}

