package app.service.aws;

import com.amazonaws.AmazonClientException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface AwsProvider {

    void uploadFile(
            final String bucketName,
            final String keyName,
            final Long contentLength,
            final String contentType,
            final InputStream value
    ) throws AmazonClientException;

    ByteArrayOutputStream downloadFile(
            final String bucketName,
            final String keyName
    ) throws IOException, AmazonClientException;

    List<String> listFiles(final String bucketName) throws AmazonClientException;

    void deleteFile(
            final String bucketName,
            final String keyName
    ) throws AmazonClientException;
}
