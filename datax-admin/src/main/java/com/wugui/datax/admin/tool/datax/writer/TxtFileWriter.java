package com.wugui.datax.admin.tool.datax.writer;

import com.google.common.collect.Maps;
import com.wugui.datax.admin.tool.pojo.DataxTxtFilePojo;

import java.util.Map;

public class TxtFileWriter extends BaseWriterPlugin implements DataxWriterInterface {
    @Override
    public String getName() { return "txtfilewriter"; }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    @Override
    public Map<String, Object> buildTxtFile(DataxTxtFilePojo plugin) {
        Map<String, Object> writerObj = Maps.newLinkedHashMap();
        writerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();

//        parameterObj.put("path", plugin.getPath());
//        parameterObj.put("fileName", plugin.getFileName());
//        parameterObj.put("writeMode", plugin.getWriteMode());
//        parameterObj.put("fieldDelimiter", plugin.getFieldDelimiter());

        writerObj.put("parameter", parameterObj);
        return writerObj;
    }
}
