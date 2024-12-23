package app.service.aws.impl;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import org.apache.http.client.methods.HttpGet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AwsProviderImplTest {

    @InjectMocks
    private AwsProviderImpl awsProvider;

    @Mock
    private AmazonS3 s3Client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testUploadFile() {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";
        Long contentLength = 123L;
        String contentType = "text/plain";
        InputStream inputStream = new ByteArrayInputStream("Test content".getBytes());

        // Act
        awsProvider.uploadFile(bucketName, keyName, contentLength, contentType, inputStream);

        // Assert
        verify(s3Client, times(1)).putObject(eq(bucketName), eq(keyName), eq(inputStream), any(ObjectMetadata.class));
    }

    @Test
    void testUploadFileThrowsException() {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";
        Long contentLength = 123L;
        String contentType = "text/plain";
        InputStream inputStream = new ByteArrayInputStream("Test content".getBytes());

        doThrow(AmazonClientException.class).when(s3Client).putObject(anyString(), anyString(), any(InputStream.class), any(ObjectMetadata.class));

        // Act & Assert
        assertThrows(AmazonClientException.class, () ->
                awsProvider.uploadFile(bucketName, keyName, contentLength, contentType, inputStream)
        );
    }

    @Test
    void testDownloadFile() throws IOException {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";
        S3ObjectInputStream inputStream = new S3ObjectInputStream(new ByteArrayInputStream("Test content".getBytes()), new HttpGet());
        S3Object s3Object = mock(S3Object.class);

        when(s3Client.getObject(bucketName, keyName)).thenReturn(s3Object);
        when(s3Object.getObjectContent()).thenReturn(inputStream);

        // Act
        ByteArrayOutputStream result = awsProvider.downloadFile(bucketName, keyName);

        // Assert
        assertNotNull(result);
        assertEquals("Test content", result.toString());
        verify(s3Client, times(1)).getObject(bucketName, keyName);
    }

    @Test
    void testDownloadFileThrowsException() {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";

        when(s3Client.getObject(bucketName, keyName)).thenThrow(AmazonClientException.class);

        // Act & Assert
        assertThrows(AmazonClientException.class, () -> awsProvider.downloadFile(bucketName, keyName));
    }

    @Test
    void testListFiles() {
        // Arrange
        String bucketName = "test-bucket";
        ObjectListing objectListing = mock(ObjectListing.class);
        ObjectListing nextBatch = mock(ObjectListing.class);
        List<S3ObjectSummary> summaries = new ArrayList<>();
        S3ObjectSummary summary = new S3ObjectSummary();
        summary.setKey("file1.txt");
        summaries.add(summary);


        when(s3Client.listObjects(bucketName)).thenReturn(objectListing);
        when(s3Client.listNextBatchOfObjects(objectListing)).thenReturn(nextBatch);
        when(objectListing.getObjectSummaries()).thenReturn(summaries);
        when(objectListing.isTruncated()).thenReturn(false);

        // Act
        List<String> result = awsProvider.listFiles(bucketName);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("file1.txt", result.get(0));
        verify(s3Client, times(1)).listObjects(bucketName);
    }

    @Test
    void testDeleteFile() {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";

        // Act
        awsProvider.deleteFile(bucketName, keyName);

        // Assert
        verify(s3Client, times(1)).deleteObject(bucketName, keyName);
    }

    @Test
    void testDeleteFileThrowsException() {
        // Arrange
        String bucketName = "test-bucket";
        String keyName = "test-file.txt";

        doThrow(AmazonClientException.class).when(s3Client).deleteObject(bucketName, keyName);

        // Act & Assert
        assertThrows(AmazonClientException.class, () -> awsProvider.deleteFile(bucketName, keyName));
    }
}

