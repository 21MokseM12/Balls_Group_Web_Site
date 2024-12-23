package app.enumeration;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

public class FileTypeTest {

    @Test
    void fromFilename_ReturnsCorrectMediaType_WhenExtensionIsKnown() {
        assertEquals(MediaType.IMAGE_JPEG, FileType.fromFilename("example.jpg"));
        assertEquals(MediaType.IMAGE_JPEG, FileType.fromFilename("example.jpeg"));
        assertEquals(MediaType.TEXT_PLAIN, FileType.fromFilename("example.txt"));
        assertEquals(MediaType.IMAGE_PNG, FileType.fromFilename("example.png"));
        assertEquals(MediaType.APPLICATION_PDF, FileType.fromFilename("example.pdf"));
    }

    @Test
    void fromFilename_ReturnsOctetStream_WhenExtensionIsUnknown() {
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, FileType.fromFilename("example.unknown"));
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, FileType.fromFilename("example."));
    }

    @Test
    void fromFilename_ReturnsOctetStream_WhenNoExtensionPresent() {
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, FileType.fromFilename("example"));
    }

    @Test
    void fromFilename_ReturnsOctetStream_WhenFilenameIsNullOrEmpty() {
        assertEquals(MediaType.APPLICATION_OCTET_STREAM, FileType.fromFilename(""));
    }

    @Test
    void allEnumValues_HaveNonNullFields() {
        for (FileType fileType : FileType.values()) {
            assertNotNull(fileType.getExtension(), "Extension should not be null for: " + fileType);
            assertNotNull(fileType.getMediaType(), "MediaType should not be null for: " + fileType);
        }
    }
}
