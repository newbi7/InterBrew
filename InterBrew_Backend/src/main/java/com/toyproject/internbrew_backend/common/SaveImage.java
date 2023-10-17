package com.toyproject.internbrew_backend.common;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Slf4j
@Component
@RequiredArgsConstructor
public class SaveImage {

    public String saveImage(MultipartFile file, String imageCategory) throws Exception {
        String fileName = null;
        String addPath;
        try {
            String uploadPath = "src/main/resources/static/images";
            addPath = "";

            if ("Coffee".equalsIgnoreCase(imageCategory)) {
                uploadPath = "src/main/resources/static/images/coffee";
                addPath = "coffee/";
            }

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            log.error(String.valueOf(uploadDir));

            fileName = file.getOriginalFilename();
            Path filePath = uploadDir.toPath().resolve(fileName);
            log.error(String.valueOf(filePath));

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            log.error("이미지 저장 중 오류 발생", e);
            throw new Exception("이미지를 저장할 수 없습니다.");
        }
        return "http://localhost/static/images/" + addPath + fileName;
    }
}
