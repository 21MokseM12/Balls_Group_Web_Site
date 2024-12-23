package app.service.controllers.admin.shop.clothing.impl;

import app.domain.entites.shop.ClothingSize;
import app.repository.shop.ClothingSizeRepository;
import app.service.controllers.admin.shop.clothing.ClothingSizeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClothingServiceImplTest {

    @Mock
    private ClothingSizeRepository clothingSizeRepository;

    @InjectMocks
    private ClothingSizeServiceImpl clothingSizeService;

    @Test
    void testFindById_NotFound() {
        when(clothingSizeRepository.findById(1)).thenReturn(Optional.empty());

        ClothingSizeException exception = assertThrows(ClothingSizeException.class, () -> clothingSizeService.findById(1));

        assertEquals("Размер не был найден", exception.getMessage());
        verify(clothingSizeRepository, times(1)).findById(1);
    }

    @Test
    void testFindAll() {
        ClothingSize size1 = new ClothingSize();
        ClothingSize size2 = new ClothingSize();
        when(clothingSizeRepository.findAll()).thenReturn(Arrays.asList(size1, size2));

        List<ClothingSize> sizes = clothingSizeService.findAll();

        assertNotNull(sizes);
        assertEquals(2, sizes.size());
        verify(clothingSizeRepository, times(1)).findAll();
    }

    @Test
    void testExistsById_True() {
        when(clothingSizeRepository.existsById(1)).thenReturn(true);

        boolean exists = clothingSizeService.existsById(1);

        assertTrue(exists);
        verify(clothingSizeRepository, times(1)).existsById(1);
    }

    @Test
    void testExistsById_False() {
        when(clothingSizeRepository.existsById(1)).thenReturn(false);

        boolean exists = clothingSizeService.existsById(1);

        assertFalse(exists);
        verify(clothingSizeRepository, times(1)).existsById(1);
    }

    @Test
    void testDeleteById() {
        Integer sizeId = 1;
        when(clothingSizeRepository.existsById(sizeId)).thenReturn(true);

        clothingSizeService.deleteById(sizeId);

        verify(clothingSizeRepository, times(1)).deleteById(sizeId);
    }

    @Test
    void testSave() {
        ClothingSize size = new ClothingSize();

        clothingSizeService.save(size);

        verify(clothingSizeRepository, times(1)).save(size);
    }

    @Test
    void testExistsBySize_True() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeRepository.existsBySize(size.getSize())).thenReturn(true);

        boolean exists = clothingSizeService.existsBySize(size);

        assertTrue(exists);
        verify(clothingSizeRepository, times(1)).existsBySize(size.getSize());
    }

    @Test
    void testExistsBySize_False() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeRepository.existsBySize(size.getSize())).thenReturn(false);

        boolean exists = clothingSizeService.existsBySize(size);

        assertFalse(exists);
        verify(clothingSizeRepository, times(1)).existsBySize(size.getSize());
    }

    @Test
    void testFindBySize_Found() {
        ClothingSize size = new ClothingSize();
        when(clothingSizeRepository.findBySize("L")).thenReturn(size);

        ClothingSize foundSize = clothingSizeService.findBySize("L");

        assertNotNull(foundSize);
        assertEquals(size, foundSize);
        verify(clothingSizeRepository, times(1)).findBySize("L");
    }

    @Test
    void testFindBySize_NotFound() {
        when(clothingSizeRepository.findBySize("L")).thenReturn(null);

        ClothingSize foundSize = clothingSizeService.findBySize("L");

        assertNull(foundSize);
        verify(clothingSizeRepository, times(1)).findBySize("L");
    }
}
