package com.cloud.web.controller;

import com.cloud.dto.FileInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

@RestController
@RequestMapping("/file")
public class FileController {
    @PostMapping("/upload")
    public FileInfo update(MultipartFile file) throws IOException {
        System.out.println(file.getContentType());
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
        String extension = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");

        File localFile = new File(".", UUID.randomUUID().toString() + "." + extension);

        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String filePath = "Spring-Dubbo-Distributed-REST-Service-Development-Practice/bookshop-admin/src/main/java/com/cloud/dto/BookCondition.java";
        try (InputStream inputStream = new FileInputStream(filePath);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=BookCondition.java");

            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
    }
}
