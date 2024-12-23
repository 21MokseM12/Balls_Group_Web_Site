package app.controllers.admin.api.aws;

import app.service.aws.AwsManagementService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = AwsController.class)
@Import(TestSecurityConfig.class)
public class AwsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AwsManagementService awsManagementService; // Мокаем интерфейс ConcertsService

    // Тест для метода listFiles
    @Test
    public void testListFiles_Success() throws Exception {
        when(awsManagementService.listFiles("myBucket"))
                .thenReturn(new ResponseEntity<>(List.of("Files listed successfully"), HttpStatus.OK));

        mockMvc.perform(get("/api/v1/s3bucket-storage/myBucket/"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                                    [
                                                      "Files listed successfully"
                                                    ]
                                                    """));
    }

    // Тест для метода uploadFiles
    @Test
    public void testUploadFiles_Success() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
        when(awsManagementService.uploadFiles(any(), any()))
                .thenReturn(new ResponseEntity<>("Files uploaded successfully", HttpStatus.OK));

        mockMvc.perform(multipart("/api/v1/s3bucket-storage/myBucket/upload-all/")
                        .file("files", file.getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().string("Files uploaded successfully"));
    }

    // Тест для метода uploadFile
    @Test
    public void testUploadFile_Success() throws Exception {
        MultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
        when(awsManagementService.uploadFile(any(), any()))
                .thenReturn(new ResponseEntity<>("File uploaded successfully", HttpStatus.OK));

        mockMvc.perform(multipart("/api/v1/s3bucket-storage/myBucket/upload/")
                        .file("file", file.getBytes()))
                .andExpect(status().isOk())
                .andExpect(content().string("File uploaded successfully"));
    }

    // Тест для метода downloadFile
    @Test
    public void testDownloadFile_Success() throws Exception {
        when(awsManagementService.downloadFile("myBucket", "test.txt"))
                .thenReturn(ResponseEntity.ok("File downloaded successfully".getBytes(StandardCharsets.UTF_8)));

        mockMvc.perform(get("/api/v1/s3bucket-storage/myBucket/download/test.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("File downloaded successfully"));
    }

    // Тест для метода deleteFile
    @Test
    public void testDeleteFile_Success() throws Exception {
        when(awsManagementService.deleteFile("myBucket", "test.txt"))
                .thenReturn(new ResponseEntity<>("File deleted successfully", HttpStatus.OK));

        mockMvc.perform(delete("/api/v1/s3bucket-storage/myBucket/delete/test.txt"))
                .andExpect(status().isOk())
                .andExpect(content().string("File deleted successfully"));
    }

    // Тест для метода listFiles при неудаче (например, не найден бакет)
    @Test
    public void testListFiles_Failure() throws Exception {
        when(awsManagementService.listFiles("invalidBucket"))
                .thenReturn(new ResponseEntity<>(List.of("Bucket not found"), HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v1/s3bucket-storage/invalidBucket/"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("""
                                                    [
                                                      "Bucket not found"
                                                    ]
                                                    """));
    }

    // Тест для метода uploadFiles при неудаче (например, пустые файлы)
    @Test
    public void testUploadFiles_Failure() throws Exception {
        MultipartFile emptyFile = new MockMultipartFile("file", "empty.txt", "text/plain", new byte[0]);
        when(awsManagementService.uploadFiles(any(), any()))
                .thenReturn(new ResponseEntity<>("No files to upload", HttpStatus.BAD_REQUEST));

        mockMvc.perform(multipart("/api/v1/s3bucket-storage/myBucket/upload-all/")
                        .file("files", emptyFile.getBytes()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("No files to upload"));
    }

    // Тест для метода uploadFile при неудаче (например, неверный формат файла)
    @Test
    public void testUploadFile_Failure() throws Exception {
        MultipartFile invalidFile = new MockMultipartFile("file", "invalid.exe", "application/octet-stream", new byte[0]);
        when(awsManagementService.uploadFile(any(), any()))
                .thenReturn(new ResponseEntity<>("Invalid file format", HttpStatus.BAD_REQUEST));

        mockMvc.perform(multipart("/api/v1/s3bucket-storage/myBucket/upload/")
                        .file("file", invalidFile.getBytes()))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file format"));
    }

    // Тест для метода downloadFile при неудаче (например, файл не найден)
    @Test
    public void testDownloadFile_Failure() throws Exception {
        when(awsManagementService.downloadFile("myBucket", "nonExistentFile.txt"))
                .thenReturn(new ResponseEntity<>("File not found".getBytes(StandardCharsets.UTF_8), HttpStatus.NOT_FOUND));

        mockMvc.perform(get("/api/v1/s3bucket-storage/myBucket/download/nonExistentFile.txt"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("File not found"));
    }

    // Тест для метода deleteFile при неудаче (например, файл не найден)
    @Test
    public void testDeleteFile_Failure() throws Exception {
        when(awsManagementService.deleteFile("myBucket", "nonExistentFile.txt"))
                .thenReturn(new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND));

        mockMvc.perform(delete("/api/v1/s3bucket-storage/myBucket/delete/nonExistentFile.txt"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("File not found"));
    }
}
