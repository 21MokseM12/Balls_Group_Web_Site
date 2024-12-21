package app.service.aws;

import app.enumeration.FileType;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class AwsManagementService {

    @Autowired
    private AwsProvider provider;

    public ResponseEntity<?> listFiles(String bucketName) {
        val body = provider.listFiles(bucketName);
        return ResponseEntity.ok(body);
    }

    public ResponseEntity<?> uploadFiles(String bucketName, List<MultipartFile> files) {
        int indexUploadError = -1;
        for (int i = 0; i < files.size(); i++) {
            ResponseEntity<?> response = uploadFile(bucketName, files.get(i));
            if (response.getStatusCode() != HttpStatus.OK) {
                indexUploadError = i;
                break;
            }
        }
        if (indexUploadError != -1) {
            for (int i = 0; i < indexUploadError; i++)
                deleteFile(bucketName, files.get(i).getOriginalFilename());
            return ResponseEntity.internalServerError().body("Uploading files was failed");
        } else {
            return ResponseEntity.ok().body("Files uploaded successfully");
        }
    }

    @SneakyThrows(IOException.class)
    public ResponseEntity<?> uploadFile(String bucketName, MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        InputStream inputStream = file.getInputStream();

        provider.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);

        return ResponseEntity.ok().body("File uploaded successfully");
    }

    @SneakyThrows
    public ResponseEntity<?> downloadFile(String bucketName, String fileName) {
        val body = provider.downloadFile(bucketName, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(FileType.fromFilename(fileName))
                .body(body.toByteArray());
    }

    public ResponseEntity<?> deleteFile(String bucketName, String fileName) {
        provider.deleteFile(bucketName, fileName);
        return ResponseEntity.ok().build();
    }
}
