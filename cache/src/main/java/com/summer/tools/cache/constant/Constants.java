package com.summer.tools.cache.constant;

public interface Constants {
    /**
     * 缓存管理器类型
     * CAFFEINE : caffeine 缓存管理
     */
    String CAFFEINE = "caffeineCacheManager";
    String REDIS = "redisCacheManager";


    /**
     * 选哟缓存的数据
     */
    String CACHE_USER = "USER";
    String CACHE_DICT = "DICT";

}
