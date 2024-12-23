package app.service.aws;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class AwsManagementServiceTest {

    @InjectMocks
    private AwsManagementService awsManagementService;

    @Mock
    private AwsProvider awsProvider;

    @Mock
    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testListFiles() {
        // Arrange
        String bucketName = "test-bucket";
        List<String> files = List.of("file1.txt", "file2.txt");
        when(awsProvider.listFiles(bucketName)).thenReturn(files);

        // Act
        ResponseEntity<List<String>> response = awsManagementService.listFiles(bucketName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(files, response.getBody());
        verify(awsProvider, times(1)).listFiles(bucketName);
    }

    @Test
    void testUploadFileSuccess() throws Exception {
        // Arrange
        String bucketName = "test-bucket";
        String fileName = "test.txt";
        InputStream inputStream = new ByteArrayInputStream("Test content".getBytes());
        when(multipartFile.isEmpty()).thenReturn(false);
        when(multipartFile.getOriginalFilename()).thenReturn(fileName);
        when(multipartFile.getContentType()).thenReturn("text/plain");
        when(multipartFile.getSize()).thenReturn(11L);
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        // Act
        ResponseEntity<String> response = awsManagementService.uploadFile(bucketName, multipartFile);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded successfully", response.getBody());
        verify(awsProvider, times(1)).uploadFile(eq(bucketName), eq(fileName), eq(11L), eq("text/plain"), eq(inputStream));
    }

    @Test
    void testUploadFileEmpty() {
        // Arrange
        String bucketName = "test-bucket";
        when(multipartFile.isEmpty()).thenReturn(true);

        // Act
        ResponseEntity<String> response = awsManagementService.uploadFile(bucketName, multipartFile);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("File is empty", response.getBody());
        verify(awsProvider, never()).uploadFile(any(), any(), anyLong(), any(), any());
    }

    @Test
    void testDeleteFile() {
        // Arrange
        String bucketName = "test-bucket";
        String fileName = "test.txt";

        // Act
        ResponseEntity<String> response = awsManagementService.deleteFile(bucketName, fileName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File test.txt was deleted successfully", response.getBody());
        verify(awsProvider, times(1)).deleteFile(bucketName, fileName);
    }

    @Test
    void testDownloadFile() throws Exception {
        // Arrange
        String bucketName = "test-bucket";
        String fileName = "test.txt";
//        val fileContent = new ByteArrayInputStream("File content".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream(5);
        out.writeBytes("File content".getBytes());
        when(awsProvider.downloadFile(bucketName, fileName)).thenReturn(out);

        // Act
        ResponseEntity<byte[]> response = awsManagementService.downloadFile(bucketName, fileName);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("File content", new String(response.getBody()));
        verify(awsProvider, times(1)).downloadFile(bucketName, fileName);
    }

    @Test
    void testUploadFilesSuccess() throws Exception {
        // Arrange
        String bucketName = "test-bucket";
        MultipartFile file1 = mock(MultipartFile.class);
        MultipartFile file2 = mock(MultipartFile.class);
        when(file1.isEmpty()).thenReturn(false);
        when(file2.isEmpty()).thenReturn(false);
        when(file1.getOriginalFilename()).thenReturn("file1.txt");
        when(file2.getOriginalFilename()).thenReturn("file2.txt");
        when(file1.getInputStream()).thenReturn(new ByteArrayInputStream("content1".getBytes()));
        when(file2.getInputStream()).thenReturn(new ByteArrayInputStream("content2".getBytes()));

        // Act
        ResponseEntity<String> response = awsManagementService.uploadFiles(bucketName, List.of(file1, file2));

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Files uploaded successfully", response.getBody());
    }
}
