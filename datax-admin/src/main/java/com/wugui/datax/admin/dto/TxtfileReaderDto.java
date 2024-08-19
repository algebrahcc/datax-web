package com.wugui.datax.admin.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author ss
 */
@Data
public class TxtfileReaderDto implements Serializable {

    private String readerPath;

    private List<String> readerPaths;

    private String readerFieldDelimiter;

    private Boolean readerSkipHeader;

}
