package com.louis.mango.admin.dao.log;

import com.louis.mango.admin.model.log.SysLog;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysLog record);

    SysLog selectByPrimaryKey(Long id);

    List<SysLog> selectAll();

    int updateByPrimaryKey(SysLog record);
}