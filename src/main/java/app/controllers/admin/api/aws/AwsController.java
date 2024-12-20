package app.controllers.admin.api.aws;

import app.enumeration.FileType;
import app.service.aws.impl.AwsProviderImpl;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/s3bucket-storage/")
//todo extract logic into other class
public class AwsController {

    @Autowired
    private AwsProviderImpl service;

    // Endpoint to list files in a bucket
    @GetMapping("{bucketName}/")
    public ResponseEntity<?> listFiles(
            @PathVariable("bucketName") String bucketName
    ) {
        val body = service.listFiles(bucketName);
        return ResponseEntity.ok(body);
    }

    @PostMapping("{bucketName}/upload-all/")
    public ResponseEntity<?> uploadFiles(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("files")List<MultipartFile> files
    ) {
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

    // Endpoint to upload a file to a bucket
    @PostMapping("{bucketName}/upload/")
    @SneakyThrows(IOException.class)
    public ResponseEntity<?> uploadFile(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("file") MultipartFile file
    ) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String contentType = file.getContentType();
        long fileSize = file.getSize();
        InputStream inputStream = file.getInputStream();

        service.uploadFile(bucketName, fileName, fileSize, contentType, inputStream);

        return ResponseEntity.ok().body("File uploaded successfully");
    }

    // Endpoint to download a file from a bucket
    @SneakyThrows
    @GetMapping("{bucketName}/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        val body = service.downloadFile(bucketName, fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(FileType.fromFilename(fileName))
                .body(body.toByteArray());
    }

    // Endpoint to delete a file from a bucket
    @DeleteMapping("{bucketName}/delete/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        service.deleteFile(bucketName, fileName);
        return ResponseEntity.ok().build();
    }
}
