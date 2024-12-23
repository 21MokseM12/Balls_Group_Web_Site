package app.service.controllers.admin.shop.clothing;

import app.domain.entites.shop.ClothingSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClothingSizeManagementServiceTest {

    @Mock
    private ClothingSizeService clothingSizeService;

    @InjectMocks
    private ClothingSizeManagementService clothingSizeManagementService;

    @Test
    void testFindAllClothingSizes() {
        ClothingSize size1 = new ClothingSize();
        ClothingSize size2 = new ClothingSize();
        when(clothingSizeService.findAll()).thenReturn(Arrays.asList(size1, size2));

        var sizes = clothingSizeManagementService.findAllClothingSizes();

        assertNotNull(sizes);
        assertEquals(2, sizes.size());
        verify(clothingSizeService, times(1)).findAll();
    }

    @Test
    void testFindClothingSizeById_Found() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeService.findById(1)).thenReturn(size);

        Optional<ClothingSize> foundSize = clothingSizeManagementService.findClothingSizeById(1);

        assertTrue(foundSize.isPresent());
        assertEquals(size, foundSize.get());
        verify(clothingSizeService, times(1)).findById(1);
    }

    @Test
    void testFindClothingSizeById_NotFound() {
        when(clothingSizeService.findById(1)).thenReturn(null);

        Optional<ClothingSize> foundSize = clothingSizeManagementService.findClothingSizeById(1);

        assertFalse(foundSize.isPresent());
        verify(clothingSizeService, times(1)).findById(1);
    }

    @Test
    void testAddClothingSize_SizeExists() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeService.existsBySize(size)).thenReturn(true);

        ResponseEntity<String> response = clothingSizeManagementService.addClothingSize(size);

        assertEquals("Такой размер уже существует", response.getBody());
        verify(clothingSizeService, times(1)).existsBySize(size);
        verify(clothingSizeService, never()).save(size);
    }

    @Test
    void testAddClothingSize_SizeAdded() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeService.existsBySize(size)).thenReturn(false);

        ResponseEntity<String> response = clothingSizeManagementService.addClothingSize(size);

        assertEquals("Размер одежды успешно добавлен!", response.getBody());
        verify(clothingSizeService, times(1)).existsBySize(size);
        verify(clothingSizeService, times(1)).save(size);
    }

    @Test
    void testDeleteClothingSize_SizeExists() {
        Integer sizeId = 1;
        when(clothingSizeService.existsById(sizeId)).thenReturn(true);

        ResponseEntity<String> response = clothingSizeManagementService.deleteClothingSize(sizeId);

        assertEquals("Размер был успешно удален!", response.getBody());
        verify(clothingSizeService, times(1)).existsById(sizeId);
        verify(clothingSizeService, times(1)).deleteById(sizeId);
    }

    @Test
    void testDeleteClothingSize_SizeNotFound() {
        Integer sizeId = 1;
        when(clothingSizeService.existsById(sizeId)).thenReturn(false);

        ResponseEntity<String> response = clothingSizeManagementService.deleteClothingSize(sizeId);

        assertEquals("Размер не был найден", response.getBody());
        verify(clothingSizeService, times(1)).existsById(sizeId);
        verify(clothingSizeService, never()).deleteById(sizeId);
    }

    @Test
    void testUpdateClothingSize_SizeExists() {
        ClothingSize size = new ClothingSize();
        size.setId(1);
        when(clothingSizeService.existsById(size.getId())).thenReturn(true);

        ResponseEntity<String> response = clothingSizeManagementService.updateClothingSize(size);

        assertEquals("Размер одежды успешно обновлен", response.getBody());
        verify(clothingSizeService, times(1)).existsById(size.getId());
        verify(clothingSizeService, times(1)).save(size);
    }

    @Test
    void testUpdateClothingSize_SizeNotFound() {
        ClothingSize size = new ClothingSize();
        size.setId(1);
        when(clothingSizeService.existsById(size.getId())).thenReturn(false);

        ResponseEntity<String> response = clothingSizeManagementService.updateClothingSize(size);

        assertEquals("Размер не был найден", response.getBody());
        verify(clothingSizeService, times(1)).existsById(size.getId());
        verify(clothingSizeService, never()).save(size);
    }
}
