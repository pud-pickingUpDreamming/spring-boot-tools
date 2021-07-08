package com.summer.tools.cache.constant;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.summer.tools.cache.loader.DictCacheLoader;
import com.summer.tools.cache.loader.UserCacheLoader;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
public enum CacheType {
    DEFAULT(60, TimeUnit.SECONDS, 1000, null),
    USER(60, TimeUnit.SECONDS, 1000, UserCacheLoader.class),
    DICT(365, TimeUnit.DAYS, 1000, DictCacheLoader.class);

    /**
     * 过期时间
     */
    private long expires;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 缓存容量
     */
    private int capacity;
    /**
     * 缓存重新加载实现类
     */
    private Class<? extends CacheLoader<Object, Object>> clazz;

    CacheType(long expires,TimeUnit timeUnit, int capacity, Class<? extends CacheLoader<Object, Object>> clazz) {
        this.expires = expires;
        this.timeUnit = timeUnit;
        this.capacity = capacity;
        this.clazz = clazz;
    }
}
