package com.summer.tools.cache.loader;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.summer.tools.cache.orm.dao.IDictMapper;
import com.summer.tools.cache.orm.model.Dict;
import org.apache.commons.lang3.ObjectUtils;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class DictCacheLoader implements CacheLoader<Object, Object> {

    @Resource
    private IDictMapper dictMapper;

    @Nullable
    @Override
    public Object load(@NonNull Object key) {
        return dictMapper.selectList(new QueryWrapper<Dict>().eq(ObjectUtils.isNotEmpty(key),"type", key));
    }
}
