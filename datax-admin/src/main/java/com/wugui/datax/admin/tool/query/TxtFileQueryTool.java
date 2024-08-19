package com.wugui.datax.admin.tool.query;

import com.wugui.datax.admin.entity.JobDatasource;
import java.sql.SQLException;

/**
 * @author ss
 */
public class TxtFileQueryTool extends BaseQueryTool {
    public TxtFileQueryTool(JobDatasource jobDatasource) throws SQLException {
        super(jobDatasource);
        // 这里可以添加初始化的逻辑
    }
}
