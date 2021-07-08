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
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Service
public class DictServiceImpl implements IDictService {

    @Resource
    private IDictMapper dictMapper;

    public void saveData(Dict dict) {
        dictMapper.insert(dict);
    }

    @Override
    @Cacheable(value = Constants.CACHE_DICT, key = "#id")
    public Dict selectById(Integer id) {
        return dictMapper.selectById(id);
    }

    @Override
    @SuppressWarnings("unchecked")
    @Cacheable(value = Constants.CACHE_DICT, key = "#id")
    public List<Dict> selectList(String type) {
        return dictMapper.selectList(new QueryWrapper<Dict>().eq(ObjectUtils.isNotEmpty(type),"type", type));
    }
}
