package com.summer.tools.cache.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("dict")
public class Dict extends Model<Dict> {

    private Integer id;
    private String type;
    private String key;
    private String value;
    private String createTime;
}
