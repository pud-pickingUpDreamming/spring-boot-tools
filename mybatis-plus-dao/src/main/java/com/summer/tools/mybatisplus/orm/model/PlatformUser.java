package com.summer.tools.mybatisplus.orm.model;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.summer.tools.mybatisplus.orm.model.AbstractModel;
import com.baomidou.mybatisplus.annotation.Version;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 平台用户，用于登录系统
 * </p>
 *
 * @author john.wang
 * @since 2021-06-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("summer_platform_user")
@ApiModel(value="PlatformUser对象", description="平台用户，用于登录系统")
public class PlatformUser extends AbstractModel<PlatformUser> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "上次登录时间")
    private String lastLoginTime;

    @ApiModelProperty(value = "登录错误次数")
    private Integer loginErrorCount;

    @ApiModelProperty(value = "拥有的租户")
    private Integer tenantIds;

    @ApiModelProperty(value = "用户类型")
    private String userType;

    @ApiModelProperty(value = "状态")
    private String status;

    @ApiModelProperty(value = "用户头像")
    private String profilePhoto;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String gender;


    public static final String ID = "id";

    public static final String USERNAME = "username";

    public static final String PASSWORD = "password";

    public static final String LAST_LOGIN_TIME = "last_login_time";

    public static final String LOGIN_ERROR_COUNT = "login_error_count";

    public static final String TENANT_IDS = "tenant_ids";

    public static final String USER_TYPE = "user_type";

    public static final String STATUS = "status";

    public static final String PROFILE_PHOTO = "profile_photo";

    public static final String NICKNAME = "nickname";

    public static final String BIRTHDAY = "birthday";

    public static final String ADDRESS = "address";

    public static final String MOBILE = "mobile";

    public static final String EMAIL = "email";

    public static final String GENDER = "gender";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
