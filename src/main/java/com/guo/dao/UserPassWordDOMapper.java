package com.guo.dao;

import com.guo.pojo.UserPassWordDO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPassWordDOMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserPassWordDO record);

    int insertSelective(UserPassWordDO record);

    UserPassWordDO selectByPrimaryKey(Integer id);
    UserPassWordDO selectByUserId(Integer userId);

    int updateByPrimaryKeySelective(UserPassWordDO record);

    int updateByPrimaryKey(UserPassWordDO record);
}