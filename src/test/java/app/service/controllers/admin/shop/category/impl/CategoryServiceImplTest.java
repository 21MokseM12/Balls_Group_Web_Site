package app.service.controllers.admin.shop.category.impl;

import app.domain.entites.shop.Category;
import app.repository.shop.CategoryRepository;
import app.service.controllers.admin.shop.category.CategoryException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void testFindById_CategoryExists() {
        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category foundCategory = categoryService.findById(1L);

        assertNotNull(foundCategory);
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_CategoryNotFound() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        CategoryException exception = assertThrows(CategoryException.class, () -> categoryService.findById(1L));

        assertEquals("Категория не была найдена", exception.getMessage());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testFindAll() {
        Category category1 = new Category();
        Category category2 = new Category();
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category1, category2));

        var categories = categoryService.findAll();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testExistsById_True() {
        when(categoryRepository.existsById(1L)).thenReturn(true);

        boolean exists = categoryService.existsById(1L);

        assertTrue(exists);
        verify(categoryRepository, times(1)).existsById(1L);
    }

    @Test
    void testExistsById_False() {
        when(categoryRepository.existsById(1L)).thenReturn(false);

        boolean exists = categoryService.existsById(1L);

        assertFalse(exists);
        verify(categoryRepository, times(1)).existsById(1L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.deleteById(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void testSave() {
        Category category = new Category();
        categoryService.save(category);

        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testExistsByCategory_True() {
        Category category = new Category();
        category.setCategory("Electronics");
        when(categoryRepository.existsByCategory("Electronics")).thenReturn(true);

        boolean exists = categoryService.existsByCategory(category);

        assertTrue(exists);
        verify(categoryRepository, times(1)).existsByCategory("Electronics");
    }

    @Test
    void testExistsByCategory_False() {
        Category category = new Category();
        category.setCategory("Electronics");
        when(categoryRepository.existsByCategory("Electronics")).thenReturn(false);

        boolean exists = categoryService.existsByCategory(category);

        assertFalse(exists);
        verify(categoryRepository, times(1)).existsByCategory("Electronics");
    }

    @Test
    void testFindByCategory() {
        Category category = new Category();
        category.setCategory("Electronics");
        when(categoryRepository.findByCategory("Electronics")).thenReturn(category);

        Category foundCategory = categoryService.findByCategory(category);

        assertNotNull(foundCategory);
        assertEquals("Electronics", foundCategory.getCategory());
        verify(categoryRepository, times(1)).findByCategory("Electronics");
    }
}
