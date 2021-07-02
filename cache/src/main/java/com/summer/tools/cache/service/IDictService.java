package com.summer.tools.cache.service;

import com.summer.tools.cache.orm.model.Dict;

import java.util.List;

public interface IDictService {

    void saveData(Dict dict);

    Dict selectById(Integer id);

    List<Dict> selectList(String type);
}
