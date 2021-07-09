package com.summer.tools.cache.config;

import com.summer.tools.cache.lock.RedissonDistributedLocker;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Configuration
public class RedissonConfig {

    @Resource
    private RedisProperties properties;

    /**
     * 单机模式自动装配
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.host")
    RedissonClient redissonSingle() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + properties.getHost() +":"+ properties.getPort())
                .setTimeout((int)properties.getTimeout().getSeconds())
                .setConnectionPoolSize(properties.getLettuce().getPool().getMaxActive())
                .setConnectionMinimumIdleSize(properties.getLettuce().getPool().getMinIdle())
                .setPassword(properties.getPassword());

        RedissonClient redissonClient = Redisson.create(config);
        RedissonDistributedLocker.setRedissonClient(redissonClient);

        return redissonClient;
    }

    /**
     * 哨兵模式自动装配
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.sentinel.master")
    RedissonClient redissonSentinel() {
        Config config = new Config();
        List<String> nodes = properties.getSentinel().getNodes();
        config.useSentinelServers().addSentinelAddress(nodes.toArray(new String[0]))
                .setMasterName(properties.getSentinel().getMaster())
                .setTimeout((int)properties.getTimeout().get(ChronoUnit.MINUTES))
                .setMasterConnectionPoolSize(properties.getLettuce().getPool().getMaxActive())
                .setSlaveConnectionPoolSize(properties.getLettuce().getPool().getMinIdle())
                .setPassword(properties.getPassword());

        RedissonClient redissonClient = Redisson.create(config);
        RedissonDistributedLocker.setRedissonClient(redissonClient);

        return redissonClient;
    }

    /**
     * 集群模式自动装配
     */
    @Bean
    @ConditionalOnProperty(name="spring.redis.cluster")
    RedissonClient redissonCluster() {
        Config config = new Config();
        List<String> nodes = properties.getCluster().getNodes();
        config.useClusterServers().addNodeAddress(nodes.toArray(new String[0]))
                .setTimeout((int)properties.getTimeout().get(ChronoUnit.MINUTES))
                .setMasterConnectionPoolSize(properties.getLettuce().getPool().getMaxActive())
                .setSlaveConnectionPoolSize(properties.getLettuce().getPool().getMinIdle())
                .setPassword(properties.getPassword());

        RedissonClient redissonClient = Redisson.create(config);
        RedissonDistributedLocker.setRedissonClient(redissonClient);

        return redissonClient;
    }
}
