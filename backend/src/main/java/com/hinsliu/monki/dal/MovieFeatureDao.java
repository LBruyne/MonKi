package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.MovieFeatureDO;

public interface MovieFeatureDao {
    int deleteByPrimaryKey(Integer sid);

    int insert(MovieFeatureDO record);

    int insertSelective(MovieFeatureDO record);

    MovieFeatureDO selectByPrimaryKey(Integer sid);

    int updateByPrimaryKeySelective(MovieFeatureDO record);

    int updateByPrimaryKey(MovieFeatureDO record);
}