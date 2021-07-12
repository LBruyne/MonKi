package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.RecommendDO;

public interface RecommendDao {
    int deleteByPrimaryKey(Long id);

    int insert(RecommendDO record);

    int insertSelective(RecommendDO record);

    RecommendDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecommendDO record);

    int updateByPrimaryKey(RecommendDO record);
}