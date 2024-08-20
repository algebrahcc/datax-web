package com.wugui.datax.admin.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.google.common.collect.Lists;
import com.wugui.datax.admin.config.Global;
import com.wugui.datax.admin.entity.JobDatasource;
import com.wugui.datax.admin.service.DatasourceQueryService;
import com.wugui.datax.admin.service.JobDatasourceService;
import com.wugui.datax.admin.tool.query.*;
import com.wugui.datax.admin.util.JdbcConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * datasource query
 *
 * @author zhouhongfa@gz-yibo.com
 * @ClassName JdbcDatasourceQueryServiceImpl
 * @Version 1.0
 * @since 2019/7/31 20:51
 */
@Service
public class DatasourceQueryServiceImpl implements DatasourceQueryService {

    @Autowired
    private JobDatasourceService jobDatasourceService;

    @Value("${file.upload-path}")
    private String filePath;

    @Override
    public List<String> getDBs(Long id) throws IOException {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(id);
        return new MongoDBQueryTool(datasource).getDBNames();
    }


    @Override
    public List<String> getTables(Long id, String tableSchema) throws IOException {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(id);

        //如果是文件类型，则不查表
        if( datasource.getIsFile() != null && datasource.getIsFile() == 1) {
            String filesDir = datasource.getFilesDir();
            List<String> filePath = this.getExcelName(filesDir);
            return filePath;
        }
        //queryTool组装
        if (ObjectUtil.isNull(datasource)) {
            return Lists.newArrayList();
        }
        if (JdbcConstants.HBASE.equals(datasource.getDatasource())) {
            return new HBaseQueryTool(datasource).getTableNames();
        } else if (JdbcConstants.MONGODB.equals(datasource.getDatasource())) {
            return new MongoDBQueryTool(datasource).getCollectionNames(datasource.getDatabaseName());
        } else {
            BaseQueryTool qTool = QueryToolFactory.getByDbType(datasource);
            if(StringUtils.isBlank(tableSchema)){
                return qTool.getTableNames();
            }else{
                return qTool.getTableNames(tableSchema);
            }
        }
    }

    /**
     * 获取excel文件名称
     * @return
     */
    private List<String> getExcelName( String filePath) {
        List<String> fileList = new ArrayList<String>();
        String excelPath = this.filePath + "/" + filePath;
        File folder = new File(excelPath);
        Global.PATH = excelPath;
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files!= null) {
                for (File file : files) {
                    if (!file.isDirectory()){
                        fileList.add(file.getName());
                    }
                }
            }
        }
        return fileList;
    }

    @Override
    public List<String> getTableSchema(Long id) {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(id);
        //queryTool组装
        if (ObjectUtil.isNull(datasource)) {
            return Lists.newArrayList();
        }
        BaseQueryTool qTool = QueryToolFactory.getByDbType(datasource);
        return qTool.getTableSchema();
    }

    @Override
    public List<String> getCollectionNames(long id, String dbName) throws IOException {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(id);
        //queryTool组装
        if (ObjectUtil.isNull(datasource)) {
            return Lists.newArrayList();
        }
        return new MongoDBQueryTool(datasource).getCollectionNames(dbName);
    }


    @Override
    public List<String> getColumns(Long id, String tableName) throws IOException {
        //获取数据源对象
        JobDatasource datasource = jobDatasourceService.getById(id);
        //如果是文件类型，则查文件列表
        if( datasource.getIsFile() != null && datasource.getIsFile() == 1) {
            List<String> filePath = this.getExcelColumns(tableName,datasource.getFilesDir());
            return filePath;
        }
        //queryTool组装
        if (ObjectUtil.isNull(datasource)) {
            return Lists.newArrayList();
        }
        if (JdbcConstants.HBASE.equals(datasource.getDatasource())) {
            return new HBaseQueryTool(datasource).getColumns(tableName);
        } else if (JdbcConstants.MONGODB.equals(datasource.getDatasource())) {
            return new MongoDBQueryTool(datasource).getColumns(tableName);
        } else {
            BaseQueryTool queryTool = QueryToolFactory.getByDbType(datasource);
            return queryTool.getColumnNames(tableName, datasource.getDatasource());
        }
    }

    /**
     * 获取单个文件下的所有列名
     * @return
     */
    private List<String> getExcelColumns(String filename, String fileDir) {
        String filePath = this.filePath + File.separator + "/" + fileDir + "/" + filename;
        List<String> fileColumns = new ArrayList<String>();
        try (FileInputStream inputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row firstRow = sheet.getRow(0);
            if (firstRow!= null) {
                for (Cell cell : firstRow) {
                    if (cell!= null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
                        fileColumns.add(cell.getStringCellValue());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return fileColumns;

    }

    @Override
    public List<String> getColumnsByQuerySql(Long datasourceId, String querySql) throws SQLException {
        //获取数据源对象
        JobDatasource jdbcDatasource = jobDatasourceService.getById(datasourceId);
        //queryTool组装
        if (ObjectUtil.isNull(jdbcDatasource)) {
            return Lists.newArrayList();
        }
        BaseQueryTool queryTool = QueryToolFactory.getByDbType(jdbcDatasource);
        return queryTool.getColumnsByQuerySql(querySql);
    }

}
