package com.summer.tools.common.utils;

import com.summer.tools.common.annotations.ExcelHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);

    public static <T> List<T> importExcel(File file, int sheetIndex, Class<T> clazz) {
        Map<String, String> headerAlias = getHeaderAlias(clazz, false);

        return cn.hutool.poi.excel.ExcelUtil.getReader(file, sheetIndex)
                .setIgnoreEmptyRow(true).setHeaderAlias(headerAlias)
                .read(0, 1, clazz);
    }

    public static <T> List<T> importExcel(InputStream inputStream, int sheetIndex, Class<T> clazz) {
        Map<String, String> headerAlias = getHeaderAlias(clazz, false);

        return cn.hutool.poi.excel.ExcelUtil.getReader(inputStream, sheetIndex)
                .setIgnoreEmptyRow(true).setHeaderAlias(headerAlias)
                .read(0, 1, clazz);
    }

    public static <T> void exportExcel(List<T> data, String file, String sheetName, Class<T> clazz) {

        Map<String, String> outputHeaderAlias = getHeaderAlias(clazz, true);
        cn.hutool.poi.excel.ExcelUtil.getWriter(file).setHeaderAlias(outputHeaderAlias)
                .renameSheet(sheetName)
                .write(data).flush();
    }

    public static <T> void exportExcel(List<T> data, OutputStream os, String sheetName, Class<T> clazz) {

        Map<String, String> outputHeaderAlias = getHeaderAlias(clazz, true);
        cn.hutool.poi.excel.ExcelUtil.getWriter().setHeaderAlias(outputHeaderAlias)
                .renameSheet(sheetName)
                .write(data).flush(os, true);
    }

    public static <T> void exportExcel(List<T> data, HttpServletResponse response, String fileName, String sheetName, Class<T> clazz) throws IOException {

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Content-Disposition","attachment;filename="+ fileName +".xlsx");

        exportExcel(data, response.getOutputStream(), sheetName, clazz);
    }


    private static  <T> Map<String, String> getHeaderAlias(Class<T> clazz, boolean isExport) {
        Map<String, String> headerAlias = new HashMap<>(16);

        Field[] declaredFields = clazz.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            //获取注解对象
            ExcelHeader declaredAnnotation = declaredField.getDeclaredAnnotation(ExcelHeader.class);
            if (declaredAnnotation != null) {
                if (isExport) {
                    headerAlias.put(declaredField.getName(), declaredAnnotation.value());
                } else {
                    headerAlias.put(declaredAnnotation.value(), declaredField.getName());
                }
            }
        }
        return headerAlias;
    }
}
