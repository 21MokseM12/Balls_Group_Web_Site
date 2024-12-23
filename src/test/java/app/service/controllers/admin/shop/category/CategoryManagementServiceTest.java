package app.service.controllers.admin.shop.category;

import app.domain.entites.shop.Category;
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
public class CategoryManagementServiceTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryManagementService categoryManagementService;

    @Test
    void testFindAllCategories() {
        Category category1 = new Category();
        Category category2 = new Category();
        when(categoryService.findAll()).thenReturn(Arrays.asList(category1, category2));

        var categories = categoryManagementService.findAllCategories();

        assertNotNull(categories);
        assertEquals(2, categories.size());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testFindCategoryById_Found() {
        Category category = new Category();
        when(categoryService.findById(1L)).thenReturn(category);

        Optional<Category> foundCategory = categoryManagementService.findCategoryById(1L);

        assertTrue(foundCategory.isPresent());
        assertEquals(category, foundCategory.get());
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void testFindCategoryById_NotFound() {
        when(categoryService.findById(1L)).thenReturn(null);

        Optional<Category> foundCategory = categoryManagementService.findCategoryById(1L);

        assertFalse(foundCategory.isPresent());
        verify(categoryService, times(1)).findById(1L);
    }

    @Test
    void testAddCategory_CategoryExists() {
        Category category = new Category();
        when(categoryService.existsByCategory(category)).thenReturn(true);

        ResponseEntity<String> response = categoryManagementService.addCategory(category);

        assertEquals("Категория с таким названием уже существует", response.getBody());
        verify(categoryService, times(1)).existsByCategory(category);
        verify(categoryService, never()).save(category);
    }

    @Test
    void testAddCategory_CategoryAdded() {
        Category category = new Category();
        when(categoryService.existsByCategory(category)).thenReturn(false);

        ResponseEntity<String> response = categoryManagementService.addCategory(category);

        assertEquals("Категория успешно добавлена!", response.getBody());
        verify(categoryService, times(1)).existsByCategory(category);
        verify(categoryService, times(1)).save(category);
    }

    @Test
    void testUpdateCategory_CategoryExists() {
        Category category = new Category();
        category.setId(1L);
        when(categoryService.existsById(category.getId())).thenReturn(true);

        ResponseEntity<String> response = categoryManagementService.updateCategory(category);

        assertEquals("Категория успешно обновлена", response.getBody());
        verify(categoryService, times(1)).existsById(category.getId());
        verify(categoryService, times(1)).save(category);
    }

    @Test
    void testUpdateCategory_CategoryNotFound() {
        Category category = new Category();
        category.setId(1L);
        when(categoryService.existsById(category.getId())).thenReturn(false);

        ResponseEntity<String> response = categoryManagementService.updateCategory(category);

        assertEquals("Категория не был найдена", response.getBody());
        verify(categoryService, times(1)).existsById(category.getId());
        verify(categoryService, never()).save(category);
    }

    @Test
    void testDeleteCategory_CategoryExists() {
        Long categoryId = 1L;
        when(categoryService.existsById(categoryId)).thenReturn(true);

        ResponseEntity<String> response = categoryManagementService.deleteCategory(categoryId);

        assertEquals("Категория была успешно удалена!", response.getBody());
        verify(categoryService, times(1)).existsById(categoryId);
        verify(categoryService, times(1)).deleteById(categoryId);
    }

    @Test
    void testDeleteCategory_CategoryNotFound() {
        Long categoryId = 1L;
        when(categoryService.existsById(categoryId)).thenReturn(false);

        ResponseEntity<String> response = categoryManagementService.deleteCategory(categoryId);

        assertEquals("Категория не была найдена", response.getBody());
        verify(categoryService, times(1)).existsById(categoryId);
        verify(categoryService, never()).deleteById(categoryId);
    }
}
