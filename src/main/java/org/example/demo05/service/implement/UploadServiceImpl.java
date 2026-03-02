package org.example.demo05.service.implement;

import org.example.demo05.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class UploadServiceImpl implements UploadService {
    @Value("${upload.location}")
    private String location;

    @Value("${upload.url}")
    private String prefix;

    //返回图片访问路径
    @Override
    public String upload(MultipartFile file, String type) {
        if (!StringUtils.hasText(type)) {
            type = "common";
        }
        File dir = new File(location + "/" + type);
        if (!dir.exists()) {
            boolean _ = dir.mkdirs();
        }

        //1.获取上传的文件名
        String original = file.getOriginalFilename();
        int dotPos = original.lastIndexOf('.');
        //扩展名
        String ext = original.substring(dotPos + 1);

        //2.生成新的文件名。雪花算法：用于生成随机名称。
        LocalDateTime now = LocalDateTime.now();
        String fileName = now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        fileName = fileName + "." + ext;

        //3.创建文件
        File dest = new File(dir.getAbsolutePath()/*dir.getPath()*/ + "/" + fileName);

        //4.保存文件
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return prefix + type + "/" + fileName;
    }
}
