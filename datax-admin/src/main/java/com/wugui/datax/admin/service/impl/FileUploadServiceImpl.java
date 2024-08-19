package com.wugui.datax.admin.service.impl;

import com.wugui.datax.admin.service.FileUploadService;
import org.springframework.beans.factory.annotation.Value;
import com.baomidou.mybatisplus.extension.api.R;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ss  文件上传相关
 */
@Service
public class FileUploadServiceImpl implements FileUploadService {

    @Value("${file.upload-dir}")
    private String uploadDir;


    @Override
    public R<String> handleDirUpload(List<MultipartFile> files) {
        if (files.isEmpty()) {
            return R.failed("上传文件为空");
        }

        try {
            // 获取项目根目录
            String projectRootPath = new File("").getAbsolutePath();

            // 假设所有文件的 `webkitRelativePath` 有相同的根目录
            String rootFolderName = files.get(0).getOriginalFilename().split("/")[0];

            // 保存所有文件到指定目录并保持文件夹结构
            for (MultipartFile file : files) {
                // 获取相对路径
                String relativePath = file.getOriginalFilename();
                String fullFilePath = projectRootPath + "/" + uploadDir + "/" + relativePath;

                File targetFile = new File(fullFilePath);
                if (!targetFile.getParentFile().exists()) {
                    targetFile.getParentFile().mkdirs();
                }

                file.transferTo(targetFile);
            }

            String absoluteUploadDir = new File(projectRootPath + "/" + uploadDir + rootFolderName).getAbsolutePath();
            return R.ok(absoluteUploadDir);
        } catch (IOException e) {
            e.printStackTrace();
            return R.failed("文件上传失败");
        }
    }

    @Override
    public R<String> handleFileUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return R.failed("上传文件为空");
        }

        try {
            // 获取项目根目录
            String projectRootPath = new File("").getAbsolutePath();

            // 保存文件到指定目录
            String fullUploadDir = projectRootPath + "/" + uploadDir;
            File uploadDirFile = new File(fullUploadDir);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            String filePath = fullUploadDir + "/" + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            String absoluteFilePath = new File(filePath).getAbsolutePath();
            return R.ok(absoluteFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            return R.failed("文件上传失败");
        }
    }

    @Override
    public String[] getCsvHeader(String filePath) {
        Path path = Paths.get(filePath);
        if (Files.exists(path)) {
            try (BufferedReader br = Files.newBufferedReader(path)) {
                String headerLine = br.readLine();
                if (headerLine != null) {
                    return headerLine.split(",");
                } else {
                    throw new IOException("文件内容为空: " + filePath);
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件处理失败: " + e.getMessage());
            }
        } else {
            throw new RuntimeException("文件不存在: " + filePath);
        }
    }

    @Override
    public R<List<String>> getFileList(String folderPath) {
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return R.failed("指定的路径不是有效的文件夹");
        }

        List<String> fileNames = new ArrayList<>();
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return R.ok(fileNames);
    }
}
