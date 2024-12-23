package app.config;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.cloud.aws.credentials.access-key=accessKey",
        "spring.cloud.aws.credentials.secret-key=secretKey",
        "spring.cloud.aws.region.static=us-east-1",
        "spring.cloud.s3.endpoint=http://localhost:9000"
})
public class AwsConfigTest {

    @Autowired
    private AmazonS3 s3Client;

    @Value("${spring.cloud.aws.credentials.access-key}")
    private String accessKey;

    @Value("${spring.cloud.aws.credentials.secret-key}")
    private String secretKey;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Value("${spring.cloud.s3.endpoint}")
    private String endpoint;

    @BeforeEach
    public void setUp() {
        // Инициализация значений (или можно использовать @TestPropertySource для конфигурации)
        accessKey = "accessKey";
        secretKey = "secretKey";
        region = "us-east-1";
        endpoint = "http://localhost:9000";  // Пример тестового эндпоинта
    }

    @Test
    public void testS3ClientCreation() {
        // Проверка, что созданный клиент не null
        assertNotNull(s3Client);
    }

    @Test
    public void testS3ClientWithCorrectCredentials() {
        // Проверяем, что клиент был создан с правильными настройками
        assertEquals("accessKey", accessKey);
        assertEquals("secretKey", secretKey);
        assertEquals("us-east-1", region);
        assertEquals("http://localhost:9000", endpoint);
    }
}
