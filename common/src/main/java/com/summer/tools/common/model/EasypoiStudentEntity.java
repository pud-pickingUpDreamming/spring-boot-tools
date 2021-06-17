package com.summer.tools.common.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Getter
@Setter
@Accessors(chain = true)
public class EasypoiStudentEntity implements java.io.Serializable {

    private String id;

    @Excel(name = "学生姓名", width = 30, isImportField = "true")
    private String name;

    @Excel(name = "学生性别", replace = { "男_1", "女_2" }, suffix = "生", isImportField = "true")
    private int sex;

    @Excel(name = "出生日期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd", isImportField = "true", width = 20)
    private LocalDateTime birthday;

    @Excel(name = "进校日期", databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd")
    private LocalDateTime registrationDate;
}
