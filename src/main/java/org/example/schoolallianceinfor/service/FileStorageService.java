package org.example.schoolallianceinfor.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path root;

    public FileStorageService(@Value("${file.upload-dir:./uploads}") String uploadDir) {
        try {
            this.root = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(this.root);
        } catch (IOException e) {
            throw new IllegalStateException("업로드 디렉토리를 생성하지 못했습니다: " + uploadDir, e);
        }
    }

    /** 단일 파일 저장 → 공개 URL(/files/yyyy/MM/uuid_원본명) 반환 */
    public String saveOne(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) return null;

        // (선택) 이미지 타입만 허용
        String ct = file.getContentType();
        if (ct == null || !ct.startsWith("image/")) {
            throw new IOException("이미지 파일만 업로드할 수 있습니다. contentType=" + ct);
        }

        String original = StringUtils.cleanPath(Objects.requireNonNullElse(file.getOriginalFilename(), "file"));
        if (original.contains("..")) throw new IOException("유효하지 않은 파일명입니다: " + original);

        LocalDate now = LocalDate.now();
        Path dir = root.resolve(String.valueOf(now.getYear()))
                .resolve(String.format("%02d", now.getMonthValue()));
        Files.createDirectories(dir);

        String filename = UUID.randomUUID() + "_" + original;
        Path target = dir.resolve(filename);

        try (InputStream in = file.getInputStream()) {
            Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
        }

        // 정적 매핑(/files/** → uploads/)과 짝을 이루는 공개 URL
        return "/files/" + now.getYear() + "/" + String.format("%02d", now.getMonthValue()) + "/" + filename;
    }

    /** 여러 파일 저장 → 각 공개 URL 리스트 반환(빈/널 파일은 건너뜀) */
    public List<String> saveAll(List<MultipartFile> files) throws IOException {
        if (files == null || files.isEmpty()) return List.of();
        List<String> urls = new ArrayList<>();
        for (MultipartFile f : files) {
            if (f != null && !f.isEmpty()) {
                urls.add(saveOne(f));
            }
        }
        return urls;
    }
}
