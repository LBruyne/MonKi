package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.SearchActionDO;

import java.util.List;

public interface SearchActionDao {
    int deleteByPrimaryKey(Long id);

    int insert(SearchActionDO record);

    int insertSelective(SearchActionDO record);

    SearchActionDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SearchActionDO record);

    int updateByPrimaryKey(SearchActionDO record);

    List<SearchActionDO> selectByUserID(Long id);
}