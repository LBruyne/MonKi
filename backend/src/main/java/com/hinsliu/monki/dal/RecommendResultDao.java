package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.RecommendResultDO;

public interface RecommendResultDao {
    int deleteByPrimaryKey(Long id);

    int insert(RecommendResultDO record);

    int insertSelective(RecommendResultDO record);

    RecommendResultDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RecommendResultDO record);

    int updateByPrimaryKey(RecommendResultDO record);
}