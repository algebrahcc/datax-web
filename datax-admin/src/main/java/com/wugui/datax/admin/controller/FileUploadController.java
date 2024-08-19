package com.wugui.datax.admin.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.wugui.datax.admin.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

/**
 * @author ss 文件上传相关接口
 */
@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload")
    public R<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        return fileUploadService.handleFileUpload(file);
    }

    @PostMapping("/uploadDir")
    public R<String> handleDirUpload(@RequestParam("files") List<MultipartFile> files) {
        return fileUploadService.handleDirUpload(files);
    }

    @GetMapping("/getFileList")
    public R<List<String>> getFileList(@RequestParam("folderPath") String folderPath) {
        return fileUploadService.getFileList(folderPath);
    }

    @PostMapping("/getCsvHeader")
    public R<String[]> getCsvHeader(@RequestBody Map<String, String> request) {
        String filePath = request.get("path");
        if (filePath != null && !filePath.isEmpty()) {
            try {
                String[] headers = fileUploadService.getCsvHeader(filePath);
                return R.ok(headers);
            } catch (Exception e) {
                return R.failed("文件处理失败: " + e.getMessage());
            }
        } else {
            return R.failed("文件路径不能为空");
        }
    }
}
