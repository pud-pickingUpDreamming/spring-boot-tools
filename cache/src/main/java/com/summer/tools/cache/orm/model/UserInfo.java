package com.summer.tools.cache.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@TableName("user_info")
public class UserInfo extends Model<UserInfo> {

    private Integer id;
    private String userName;
    private String password;
    private String phone;
    private String email;
    private String createTime;
}
