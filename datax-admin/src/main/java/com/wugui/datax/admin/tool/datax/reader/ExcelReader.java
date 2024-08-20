package com.wugui.datax.admin.tool.datax.reader;

import com.google.common.collect.Maps;
import com.mysql.jdbc.log.Log;
import com.wugui.datax.admin.config.Global;
import com.wugui.datax.admin.entity.JobDatasource;
import com.wugui.datax.admin.tool.pojo.DataxExcelPojo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Map;

/**
 * 返回表格reader插件
 */

@Service
public class ExcelReader extends BaseReaderPlugin implements DataxReaderInterface{

    private String filePath;


    @Override
    public String getName() {
        return "excelfilereader";
    }


    /**
     * 构建 Excel reader 插件
     * @param dataxExcelPojo
     * @return
     */
    @Override
    public Map<String, Object> buildExcelFile(DataxExcelPojo dataxExcelPojo) {
        Map<String, Object> readerObj = Maps.newLinkedHashMap();

        //临时文件夹
//        String tmpName = dataxExcelPojo.getReadColumns().split("\\.")[0];

        // 获取文件路径
        String filePath = dataxExcelPojo.getExcelFilepath() + "/";

//        String tmpPath = filePath ;
//        File file = new File(tmpPath);
//        //没有文件则创建
//        if (!file.exists()) {
//            if(file.mkdirs()){
//                System.out.println("文件夹创建成功！");
//            }else {
//                System.out.println("构建失败！");
//            }
//        }
//        try {
//            Files.copy(FileSystems.getDefault().getPath(filePath + dataxExcelPojo.getReadColumns()),
//                        FileSystems.getDefault().getPath(tmpPath + "/" + dataxExcelPojo.getReadColumns()));
//        } catch (IOException e) {
//            System.out.println("文件格式有误：" + e.getMessage());
//        }
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();
        parameterObj.put("path", filePath);
        parameterObj.put("skipHeader", "1");
        parameterObj.put("column", dataxExcelPojo.getColumns());
        parameterObj.put("fieldDelimiter", ",");
        readerObj.put("parameter", parameterObj);
        return readerObj;
    }




    @Override
    public Map<String, Object> sample() {
        return null;
    }
}
