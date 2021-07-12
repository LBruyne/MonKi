package com.hinsliu.monki.dal;

import com.hinsliu.monki.domain.model.UserDO;

public interface UserDao {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);

    UserDO selectByEmail(String email);
}