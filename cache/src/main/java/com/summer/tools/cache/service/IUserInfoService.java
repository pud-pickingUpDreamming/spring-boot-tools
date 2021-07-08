package com.summer.tools.cache.service;

import com.summer.tools.cache.orm.model.UserInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IUserInfoService {

    UserInfo saveData(UserInfo userInfo);

    UserInfo selectById(@Param("id") Integer id);

    List<UserInfo> selectList() ;
}
