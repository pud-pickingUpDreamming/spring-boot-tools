package com.summer.tools.common.model;

import com.summer.tools.common.annotations.ExcelHeader;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class HutoolExcelTestModel {
    @ExcelHeader("角色id")
    private String roleId;
    @ExcelHeader("部门名称")
    private String deptName;
    @ExcelHeader("角色名称")
    private String roleName;
    @ExcelHeader("备注")
    private String remark;
}
