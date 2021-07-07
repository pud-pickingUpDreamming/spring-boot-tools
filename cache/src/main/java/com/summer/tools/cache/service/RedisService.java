package com.summer.tools.cache.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 * redis 自动配置默认注入了两个实例 redisTemplate 和 stringRedisTemplate, 一般比较常用的是 stringRedisTemplate
 * 如果用redisTemplate T 可以是任何对象,字节操作(入参/出参都是字节)
 */
@Service
@SuppressWarnings("unchecked")
public class RedisService<T> {

    @Resource
    private RedisTemplate<String, T> redisTemplate;

    public void set(String key, T value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public T get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public Boolean del(String key) {
        return redisTemplate.delete(key);
    }

    public Long del(List<String> keyList) {
        return redisTemplate.delete(keyList);
    }

    public Boolean hasKey(String key) { return redisTemplate.hasKey(key); }

    public Boolean setIfAbsent(String key, T value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public Boolean setIfAbsent(String key, T value, long time, TimeUnit timeUnit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
    }

    // set 操作
    @SafeVarargs
    public final void add(String redisKey, T... values) {
        redisTemplate.opsForSet().add(redisKey, values);
    }

    public Set<T> getSet(String redisKey) {
        return redisTemplate.opsForSet().members(redisKey);
    }

    public Boolean isMember(String redisKey, Object value) {
        return redisTemplate.opsForSet().isMember(redisKey, value);
    }

    public Long removeSet(String redisKey, Object... values) {
        if(values == null || values.length <= 0) return 0L;
        return redisTemplate.opsForSet().remove(redisKey, values);
    }

    // hash操作
    public void putAll(String redisKey, Map<String, T> values) {
        redisTemplate.opsForHash().putAll(redisKey, values);
    }

    public void put(String redisKey, String hKey, T hValue) {
        redisTemplate.opsForHash().put(redisKey, hKey, hValue);
    }

    public Map<Object, T> getHash(String redisKey) {
        return (Map<Object, T>) redisTemplate.opsForHash().entries(redisKey);
    }

    public Boolean hasHashKey(String redisKey, Object value) {
        return redisTemplate.opsForHash().hasKey(redisKey, value);
    }


    public T getHashValue(String redisKey, Object hashKey) {
        return (T) redisTemplate.opsForHash().get(redisKey, hashKey);
    }

    public final Long removeHash(String redisKey, Object... hashKeys) {
        if(hashKeys == null || hashKeys.length <= 0) return 0L;
        return redisTemplate.opsForHash().delete(redisKey, hashKeys);
    }

    // hyperLogLog
    public final Long addHyperLog(String redisKey, T... values) {
        return redisTemplate.opsForHyperLogLog().add(redisKey, values);
    }

    public final Long sizeHyperLog(String... keys) {
        return redisTemplate.opsForHyperLogLog().size(keys);
    }

    public final void removeHyperLog(String key) {
        redisTemplate.opsForHyperLogLog().delete(key);
    }
}
