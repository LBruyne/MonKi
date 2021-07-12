package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.ClickActionDO;

public interface ClickActionDao {
    int deleteByPrimaryKey(Long id);

    int insert(ClickActionDO record);

    int insertSelective(ClickActionDO record);

    ClickActionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ClickActionDO record);

    int updateByPrimaryKey(ClickActionDO record);
}