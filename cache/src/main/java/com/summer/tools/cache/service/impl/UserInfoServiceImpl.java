package com.summer.tools.cache.service.impl;

import com.summer.tools.cache.constant.Constants;
import com.summer.tools.cache.orm.dao.IUserInfoMapper;
import com.summer.tools.cache.orm.model.UserInfo;
import com.summer.tools.cache.service.IUserInfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private IUserInfoMapper userInfoMapper;

    @Override
    public void saveData(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
    }

    /**
     * 数据不存在是触发 CacheLoader, 数据存在走缓存
     */
    @Override
    @Cacheable(value = Constants.CACHE_USER, key = "#id", cacheManager = Constants.CAFFEINE)
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public List<UserInfo> selectList() {
        return userInfoMapper.selectList();
    }
}
