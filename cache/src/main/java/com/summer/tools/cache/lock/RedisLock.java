package com.summer.tools.cache.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RedisLock {

    private static StringRedisTemplate REDIS_TEMPLATE;
    private static final DefaultRedisScript<Integer> UNLOCK_SCRIPT = new DefaultRedisScript<>();
    private static final String LOCK_VALUE = "1";
    private static final long DEFAULT_TIMEOUT = 10;
    private static final long DEFAULT_WAIT_TIME = 0;
    private static final int DEFAULT_TRY_COUNT = 3;

    static {
        UNLOCK_SCRIPT.setResultType(Integer.class);
//        unlockScript.setScriptText(
//                "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
//                "    return redis.call(\"del\",KEYS[1])\n" +
//                "else\n" +
//                "    return 0\n" +
//                "end"
//        );
        UNLOCK_SCRIPT.setScriptSource(new ResourceScriptSource(new ClassPathResource("redis/unlock.lua")));
    }

    public static void init(StringRedisTemplate stringRedisTemplate) {
        RedisLock.REDIS_TEMPLATE = stringRedisTemplate;
    }

    public static boolean lock(String lockKey) {
        return lock(lockKey, DEFAULT_TIMEOUT);
    }

    public static boolean lock(String lockKey, long timeout) {
            return lock(lockKey, timeout, TimeUnit.SECONDS);
    }

    public static boolean lock(String lockKey, long timeout, TimeUnit unit ) {
        Boolean success = REDIS_TEMPLATE.opsForValue().setIfAbsent(lockKey, LOCK_VALUE, timeout, unit);
        return success != null && success;
    }

    public static boolean tryLock(String lockKey) {
        return tryLock(lockKey, DEFAULT_TIMEOUT);
    }

    public static boolean tryLock(String lockKey, long timeout) {
        return tryLock(lockKey, timeout, DEFAULT_WAIT_TIME, TimeUnit.SECONDS, DEFAULT_TRY_COUNT);
    }

    public static boolean tryLock(String lockKey, long timeout, long waitTime, TimeUnit unit, int count) {
        long nowTime = System.nanoTime();
        // 如果锁没有过期就一直
        while ((System.nanoTime() - nowTime) < unit.toNanos(timeout) && count > 0) {
            Boolean success = REDIS_TEMPLATE.opsForValue().setIfAbsent(lockKey, LOCK_VALUE, timeout, unit);
            if (success != null && success) {
                ThreadHolder.set(Thread.currentThread());
                return true;
            } else {
                // 每次请求等待一段时间
                try {
                    Thread.sleep(unit.toMillis(waitTime));
                    waitTime = 2 * waitTime;
                } catch (InterruptedException ex) {
                    log.error("线程[{}]尝试获取锁[{}]失败", Thread.currentThread().getName(), lockKey, ex);
                }
            }
            count --;
        }
        return false;
    }

    /**
     * redis可以保证lua中的键的原子操作 unlock:lock调用完之后需unlock,否则需等待lock自动过期
     * 只有当前线程可以解锁
     * @param lockKey 分布式锁 key
     */
    public static void unlock( String lockKey) {
        if (ThreadHolder.get() != Thread.currentThread()) {
            return;
        }

        REDIS_TEMPLATE.execute(UNLOCK_SCRIPT, Collections.singletonList(lockKey), LOCK_VALUE);

        ThreadHolder.remove();
    }
}
