package com.wugui.datax.admin.tool.pojo;

import com.wugui.datax.admin.dto.UpsertInfo;
import com.wugui.datax.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DataxExcelPojo {

    private List<Map<String, Object>> columns;

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    /**
     * excel文件路径
     */
    private String excelFilepath;

    private  String readColumns;


    private UpsertInfo upsertInfo;
}
