package com.wugui.datax.admin.tool.datax.writer;

import com.wugui.datax.admin.tool.pojo.DataxExcelPojo;

import java.util.Map;

/**
 * Excel writer构建类
 * 功能暂未实现
 */
public class ExcelWriter extends BaseWriterPlugin implements DataxWriterInterface{
    @Override
    public String getName() {
        return "excelwriter";
    }

    @Override
    public Map<String, Object> buildExcelFile(DataxExcelPojo dataxExcelPojo) {
        return null;
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
