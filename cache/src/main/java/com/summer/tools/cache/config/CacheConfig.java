package com.summer.tools.cache.config;

import cn.hutool.extra.spring.SpringUtil;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.summer.tools.cache.constant.CacheType;
import com.summer.tools.cache.constant.Constants;
import com.summer.tools.cache.lock.RedisLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private RedisConnectionFactory connectionFactory;

    /**
     * 修改序列号
     * 默认jdk自带序列化, 可视化很差
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setHashKeySerializer(new Jackson2JsonRedisSerializer<>(String.class));
        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setConnectionFactory(connectionFactory);

        return template;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(connectionFactory);
        RedisLock.init(stringRedisTemplate);
        return stringRedisTemplate;
    }

    /**
     * 默认是jdk序列化,调整为jackson序列化
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.of(1, ChronoUnit.MINUTES))
                .serializeKeysWith((RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer<>(String.class)).getKeySerializationPair()))
                .serializeValuesWith(RedisSerializationContext.fromSerializer(new Jackson2JsonRedisSerializer<>(String.class)).getValueSerializationPair());
    }

    /**
     * redis缓存管理
     */
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "redis")
    @Bean(name = Constants.REDIS)
    @Primary
    public CacheManager redisCacheManager() {

        return RedisCacheManager.builder().enableStatistics().cacheDefaults(redisCacheConfiguration())
                .cacheWriter(RedisCacheWriter.lockingRedisCacheWriter(connectionFactory))
                .build();
    }

    /**
     * 个性话配置
     * 二级缓存管理
     */
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "caffeine")
    @ConditionalOnMissingBean(name = Constants.CAFFEINE)
    @Bean(name = Constants.CAFFEINE)
    public CacheManager caffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        for (CacheType cacheType : CacheType.values()) {

            if (cacheType.equals(CacheType.DEFAULT)) {
                continue;
            }

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

    /**
     * 所有缓存相同配置
     * 二级缓存管理
     */
    @ConditionalOnProperty(name = "spring.cache.type", havingValue = "caffeine")
    @ConditionalOnMissingBean(name = Constants.CAFFEINE)
    @Bean(name = Constants.CAFFEINE)
    @Primary
    public CacheManager defaultCaffeineCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        cacheManager.registerCustomCache(CacheType.DEFAULT.name(),
                Caffeine.newBuilder()
                        .initialCapacity(CacheType.DEFAULT.getCapacity())
                        .maximumSize(2*CacheType.DEFAULT.getCapacity())
                        .expireAfterWrite(CacheType.DEFAULT.getExpires(), CacheType.DEFAULT.getTimeUnit())
                        .weakKeys()
                        .recordStats()
                        .build());

        return cacheManager;
    }
}