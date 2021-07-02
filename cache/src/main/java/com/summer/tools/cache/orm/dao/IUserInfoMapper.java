package com.summer.tools.cache.orm.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.summer.tools.cache.orm.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserInfoMapper extends BaseMapper<UserInfo> {

    void saveData(UserInfo userInfo) ;

    UserInfo selectById(@Param("id") Integer id) ;

    List<UserInfo> selectList() ;
}
