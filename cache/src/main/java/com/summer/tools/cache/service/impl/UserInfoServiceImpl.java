package com.summer.tools.cache.service.impl;

import com.summer.tools.cache.constant.Constants;
import com.summer.tools.cache.orm.dao.IUserInfoMapper;
import com.summer.tools.cache.orm.model.UserInfo;
import com.summer.tools.cache.service.IUserInfoService;
import com.summer.tools.cache.service.RedisService;
import com.summer.tools.common.utils.JsonUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserInfoServiceImpl implements IUserInfoService {

    @Resource
    private IUserInfoMapper userInfoMapper;
    @Resource
    private RedisService<String> redisService;

    @Override
    public void saveData(UserInfo userInfo) {
        boolean result = redisService.setIfAbsent(String.valueOf(userInfo.getId()), JsonUtil.stringify(userInfo), 30, TimeUnit.SECONDS);
        if (result) {
            userInfoMapper.insert(userInfo);
        }
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
