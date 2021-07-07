package com.summer.tools.cache.service;

import com.summer.tools.common.utils.JsonUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @see org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 * redis 自动配置默认注入了两个实例 redisTemplate 和 stringRedisTemplate, 一般比较常用的是 stringRedisTemplate
 * 如果用stringRedisTemplate (入参/出参都是序列化字符串)
 */
@Service
@SuppressWarnings("unchecked")
public class StringRedisService<T> {

    /**
     * 切换到 redisTemplate 只需要把变量名称改为 redisTemplate 即可
     */
    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;

    public void set(String key, T value, long time, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, Objects.requireNonNull(JsonUtil.stringify(value)), time, timeUnit);
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Boolean del(String key) {
        return stringRedisTemplate.delete(key);
    }

    public Long del(List<String> keyList) {
        return stringRedisTemplate.delete(keyList);
    }

    public Boolean hasKey(String key) { return stringRedisTemplate.hasKey(key); }

    public Boolean setIfAbsent(String key, T value) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, Objects.requireNonNull(JsonUtil.stringify(value)));
    }

    public Boolean setIfAbsent(String key, T value, long time, TimeUnit timeUnit) {
        return stringRedisTemplate.opsForValue().setIfAbsent(key, Objects.requireNonNull(JsonUtil.stringify(value)), time, timeUnit);
    }

    // set 操作
    public final void add(String redisKey, String... values) {
        stringRedisTemplate.opsForSet().add(redisKey, values);
    }

    public Set<String> getSet(String redisKey) {
        return stringRedisTemplate.opsForSet().members(redisKey);
    }

    public Boolean isMember(String redisKey, Object value) {
        return stringRedisTemplate.opsForSet().isMember(redisKey, value);
    }

    public Long removeSet(String redisKey, Object... values) {
        if(values == null || values.length <= 0) return 0L;
        return stringRedisTemplate.opsForSet().remove(redisKey, values);
    }

    // hash操作
    public void putAll(String redisKey, Map<String, T> values) {
        stringRedisTemplate.opsForHash().putAll(redisKey, values);
    }

    public void put(String redisKey, String hKey, T hValue) {
        stringRedisTemplate.opsForHash().put(redisKey, hKey, hValue);
    }

    public Map<Object, T> getHash(String redisKey) {
        return (Map<Object, T>) stringRedisTemplate.opsForHash().entries(redisKey);
    }

    public Boolean hasHashKey(String redisKey, Object value) {
        return stringRedisTemplate.opsForHash().hasKey(redisKey, value);
    }


    public T getHashValue(String redisKey, Object hashKey) {
        return (T) stringRedisTemplate.opsForHash().get(redisKey, hashKey);
    }

    public final Long removeHash(String redisKey, Object... hashKeys) {
        if(hashKeys == null || hashKeys.length <= 0) return 0L;
        return stringRedisTemplate.opsForHash().delete(redisKey, hashKeys);
    }

    // hyperLogLog
    public final Long addHyperLog(String redisKey, String... values) {
        return stringRedisTemplate.opsForHyperLogLog().add(redisKey, values);
    }

    public final Long sizeHyperLog(String... keys) {
        return stringRedisTemplate.opsForHyperLogLog().size(keys);
    }

    public final void removeHyperLog(String key) {
        stringRedisTemplate.opsForHyperLogLog().delete(key);
    }

}
