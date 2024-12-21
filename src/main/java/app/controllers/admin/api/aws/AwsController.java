package app.controllers.admin.api.aws;

import app.service.aws.AwsManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/s3bucket-storage/")
public class AwsController {

    @Autowired
    private AwsManagementService service;

    // Endpoint to list files in a bucket
    @GetMapping("{bucketName}/")
    public ResponseEntity<?> listFiles(
            @PathVariable("bucketName") String bucketName
    ) {
        return service.listFiles(bucketName);
    }

    @PostMapping("{bucketName}/upload-all/")
    public ResponseEntity<?> uploadFiles(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("files")List<MultipartFile> files
    ) {
        return service.uploadFiles(bucketName, files);
    }

    // Endpoint to upload a file to a bucket
    @PostMapping("{bucketName}/upload/")
    public ResponseEntity<?> uploadFile(
            @PathVariable("bucketName") String bucketName,
            @RequestParam("file") MultipartFile file
    ) {
        return service.uploadFile(bucketName, file);
    }

    // Endpoint to download a file from a bucket
    @GetMapping("{bucketName}/download/{fileName}")
    public ResponseEntity<?> downloadFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        return service.downloadFile(bucketName, fileName);
    }

    // Endpoint to delete a file from a bucket
    @DeleteMapping("{bucketName}/delete/{fileName}")
    public ResponseEntity<?> deleteFile(
            @PathVariable("bucketName") String bucketName,
            @PathVariable("fileName") String fileName
    ) {
        return service.deleteFile(bucketName, fileName);
    }
}
