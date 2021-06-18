package com.summer.tools.common.controller;

import com.summer.tools.common.validation.Add;
import com.summer.tools.common.validation.Edit;
import com.summer.tools.common.validation.EnumValid;
import com.summer.tools.common.validation.ValidTestEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@ApiModel(description = "swagger测试接口入参")
public class RequestParams {

    @ApiModelProperty(value = "键")
    @NotNull(message = "key 不能为空", groups = Add.class)
    private String key;
    @ApiModelProperty(value = "值")
    @NotBlank(message = "value 不能为空", groups = {Edit.class})
    private String value;
    @PastOrPresent(message = "date 格式错误", groups = {Add.class, Edit.class})
    // 解决输入字符串时间问题
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private LocalDate date;
    @Email(message = "email 格式错误")
    private String email;
    @Pattern(regexp = "1[3-8][0-9]\\d{8}", message = "手机号格式错误")
    private String mobile;
    @Range(max = 100, min = 1, message = "age 超出范围")
    @Max(value = 50, message = "最大值不能超过100")
    @Min(value = 10, message = "最小值不能小于1")
    private Integer age;
    @EnumValid(clazz = ValidTestEnum.class, message = "没有这个枚举值", groups = {Add.class, Edit.class})
    private Integer enumValue;

}
