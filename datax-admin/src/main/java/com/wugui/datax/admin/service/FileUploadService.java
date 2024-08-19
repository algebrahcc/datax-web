package com.wugui.datax.admin.service;

import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author ss csv文件上传
 */

@Service
public interface FileUploadService {
    R<String> handleFileUpload(MultipartFile file);

    String[] getCsvHeader(String filePath);

    R<String> handleDirUpload(List<MultipartFile> files);

    R<List<String>> getFileList(String folderPath);
}
