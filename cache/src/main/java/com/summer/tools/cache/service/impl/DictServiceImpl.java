package com.summer.tools.cache.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summer.tools.cache.constant.CacheType;
import com.summer.tools.cache.constant.Constants;
import com.summer.tools.cache.orm.dao.IDictMapper;
import com.summer.tools.cache.orm.model.Dict;
import com.summer.tools.cache.service.IDictService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class DictServiceImpl implements IDictService {

    @Resource
    private IDictMapper dictMapper;

    @Resource(name = Constants.CAFFEINE)
    private CacheManager cacheManager;

    public void saveData(Dict dict) {
        dictMapper.insert(dict);
    }

    @Override
    public Dict selectById(Integer id) {
        return dictMapper.selectById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Dict> selectList(String type) {

        // 缓存未启用就读库
        try {
            // 字典内容添加到本地缓存,每次都会触发 CacheLoader
            return (List<Dict>) Objects.requireNonNull(Objects.requireNonNull(cacheManager.getCache(CacheType.DICT.name())).get(type)).get();

        } catch (NoSuchBeanDefinitionException ex) {
            return dictMapper.selectList(new QueryWrapper<Dict>().eq(ObjectUtils.isNotEmpty(type),"type", type));
        }
    }
}
