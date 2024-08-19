package com.wugui.datax.admin.tool.datax.reader;

import com.wugui.datax.admin.tool.pojo.DataxTxtFilePojo;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * @author ss
 */
public class TxtFileReader extends BaseReaderPlugin implements DataxReaderInterface {
    @Override
    public String getName() {
        return "txtfilereader";
    }

    @Override
    public Map<String, Object> sample() {
        return null;
    }

    @Override
    public Map<String, Object> buildTxtFile(DataxTxtFilePojo plugin) {
        Map<String, Object> readerObj = Maps.newLinkedHashMap();
        readerObj.put("name", getName());
        Map<String, Object> parameterObj = Maps.newLinkedHashMap();

        parameterObj.put("path", plugin.getReaderPath());
        parameterObj.put("column", plugin.getColumns());
        parameterObj.put("fieldDelimiter", plugin.getReaderFieldDelimiter());
        parameterObj.put("skipHeader", plugin.getSkipHeader());

        readerObj.put("parameter", parameterObj);
        return readerObj;
    }
}
