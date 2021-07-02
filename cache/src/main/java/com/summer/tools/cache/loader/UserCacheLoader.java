package com.summer.tools.cache.loader;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.summer.tools.cache.orm.dao.IUserInfoMapper;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class UserCacheLoader implements CacheLoader<Object, Object> {

    @Resource
    private IUserInfoMapper userInfoMapper;

    @Nullable
    @Override
    public Object load(@NonNull Object key) {
        return userInfoMapper.selectById((int)key);
    }
}
