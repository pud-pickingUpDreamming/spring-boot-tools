package com.summer.tools.cache.config;

import cn.hutool.extra.spring.SpringUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.summer.tools.cache.constant.CacheType;
import com.summer.tools.cache.constant.Constants;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * 二级缓存管理
     */
    @ConditionalOnProperty(name = "cache.enable", havingValue = "true")
    @Bean(name = Constants.CAFFEINE)
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        for (CacheType cacheType : CacheType.values()) {
            CacheLoader<Object, Object> cacheLoader =  SpringUtil.getBean(cacheType.getClazz());

            cacheManager.registerCustomCache(cacheType.name(),
                    Caffeine.newBuilder()
                            .initialCapacity(cacheType.getCapacity())
                            .maximumSize(2*cacheType.getCapacity())
                            .expireAfterWrite(cacheType.getExpires(), cacheType.getTimeUnit())
                            .refreshAfterWrite(cacheType.getExpires()/2, cacheType.getTimeUnit())
                            .weakKeys()
                            .recordStats()
                            .build(cacheLoader));
        }

        return cacheManager;
    }
}