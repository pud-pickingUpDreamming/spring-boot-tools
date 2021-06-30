package com.summer.tools.mybatisplus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.summer.tools.mybatisplus.properties.DaoMybatisPlusProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

@EnableTransactionManagement
@Configuration
@MapperScan(basePackages = {"com.summer.tools.mybatisplus.orm.dao"})
public class MybatisPlusConfig {

    @Resource
    private DaoMybatisPlusProperties daoMybatisPlusProperties;

    @Bean
    @ConditionalOnProperty(name = "template.enable", havingValue = "true")
    public void template() {
        System.out.println(daoMybatisPlusProperties.getEnable());
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.POSTGRE_SQL));
        return interceptor;
    }

    @Bean
    public DataSourceProvider dataSourceProvider() {
        return new DataSourceProvider();
    }
}