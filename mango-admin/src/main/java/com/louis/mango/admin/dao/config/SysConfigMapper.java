package com.louis.mango.admin.dao.config;


import com.louis.mango.admin.model.config.SysConfig;

import java.util.List;

public interface SysConfigMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysConfig record);

    SysConfig selectByPrimaryKey(Long id);

    List<SysConfig> selectAll();

    int updateByPrimaryKey(SysConfig record);
}