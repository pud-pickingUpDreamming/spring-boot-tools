package com.summer.tools.common;

import com.summer.tools.common.model.HutoolExcelTestModel;
import com.summer.tools.common.utils.ExcelUtil;
import com.summer.tools.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

@Slf4j
public class HutoolTest {

    @Test
    public void importExcel() {

        List<HutoolExcelTestModel> list = ExcelUtil.importExcel(new File("D:/data/部门角色配置.xlsx"), 1, HutoolExcelTestModel.class);

        System.out.println(JsonUtil.stringify(list));
    }

    @Test
    public void exportExcel() {

        List<HutoolExcelTestModel> list = ExcelUtil.importExcel(new File("D:/data/部门角色配置.xlsx"), 1, HutoolExcelTestModel.class);

        ExcelUtil.exportExcel(list, "D:/data/部门角色配置.download.xlsx", "测试123", HutoolExcelTestModel.class);
    }

}
