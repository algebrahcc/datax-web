package com.wugui.datax.admin.tool.pojo;

import com.wugui.datax.admin.entity.JobDatasource;
import lombok.Data;

import java.util.List;
import java.util.Map;


@Data
public class DataxTxtFilePojo {

    private List<Map<String,Object>> columns;

    /**
     * 数据源信息
     */
    private JobDatasource jdbcDatasource;

    private String readerPath;


    private String readerFieldDelimiter;

    /* private String writerDefaultFS;

    private String writerFileType;

    private String writerPath;

    private String writerFileName;

    private String writeMode;

    private String writeFieldDelimiter; */

    private Boolean skipHeader;
}
