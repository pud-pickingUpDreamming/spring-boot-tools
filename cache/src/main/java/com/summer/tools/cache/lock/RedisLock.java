package com.summer.tools.cache.lock;

import org.redisson.api.RLock;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisLock {
    private static RedisTemplate<Object, Object> redisClient;

    public static boolean lock(String lockKey) {
        return lock(lockKey, 0);
    }

    public static boolean lock(String lockKey, int leaseTime) {
        return lock(lockKey, leaseTime, );
    }

    public static boolean lock(String lockKey, TimeUnit unit ,int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    public static void unlock(String lockKey) {
        Object lock = redisClient.opsForValue().get(lockKey);
    }

    public static void unlock(Lock lock) {
        lock.unlock();
    }

    public static void setRedisClient(RedisTemplate<Object, Object> redissonClient) {
        RedisLock.redisClient = redissonClient;
    }

    public static class Lock {

        private String key;
        private int leaseTime;
        private int waitTime;
        private TimeUnit timeUnit;

        Lock(String key) {
            this.key = key;
        }

        Lock(String key, int leaseTime) {
            this.key = key;
            this.leaseTime = leaseTime;
        }

        Lock(String key, int leaseTime, TimeUnit timeUnit) {
            this.key = key;
            this.leaseTime = leaseTime;
            this.timeUnit = timeUnit;
        }


        Lock(String key, int leaseTime, int waitTime, TimeUnit timeUnit) {
            this.key = key;
            this.leaseTime = leaseTime;
            this.waitTime = waitTime;
            this.timeUnit = timeUnit;
        }

        public static boolean lock() {}
        public static boolean tryLock() {}
        public static boolean unlock() {}

    }
}
