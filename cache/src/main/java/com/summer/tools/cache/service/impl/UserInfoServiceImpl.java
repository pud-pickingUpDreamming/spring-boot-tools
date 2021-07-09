package com.summer.tools.cache.service.impl;

import com.summer.tools.cache.constant.Constants;
import com.summer.tools.cache.lock.RedisLock;
import com.summer.tools.cache.lock.RedissonDistributedLocker;
import com.summer.tools.cache.orm.dao.IUserInfoMapper;
import com.summer.tools.cache.orm.model.UserInfo;
import com.summer.tools.cache.service.IUserInfoService;
import com.summer.tools.cache.service.RedisService;
import com.summer.tools.common.utils.JsonUtil;
import org.springframework.cache.annotation.CachePut;
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
    private RedisService<UserInfo> redisService;

    @Override
    @CachePut(value = Constants.CACHE_USER, key = "#result.id")
    public UserInfo saveData(UserInfo userInfo) {
        boolean result = redisService.setIfAbsent(String.valueOf(userInfo.getId()), userInfo, 60, TimeUnit.SECONDS);
        if (result) {
            userInfoMapper.insert(userInfo);
        }
        return userInfo;
    }

    /**
     * 数据不存在是触发 CacheLoader, 数据存在走缓存
     */
    @Override
    @Cacheable(value = Constants.CACHE_USER, key = "#id")
    public UserInfo selectById(Integer id) {
        return userInfoMapper.selectById(id);
    }

    @Override
    public List<UserInfo> selectList() {
        List<UserInfo> userInfos;
        if (RedisLock.tryLock("123", 180)) {
            userInfos = userInfoMapper.selectList();
        } else {
            RedissonDistributedLocker.lock("456", 180);
            userInfos = userInfoMapper.selectList();
            RedissonDistributedLocker.unlock("456");
            RedisLock.unlock("123");
        }
        return userInfos;
    }
}
