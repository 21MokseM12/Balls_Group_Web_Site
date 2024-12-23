package app.service.controllers.admin.shop.product.impl;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.Product;
import app.repository.shop.ProductRepository;
import app.service.controllers.admin.shop.product.ProductException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестового товара и категории
        category = new Category();
        category.setId(1L);
        category.setCategory("Clothing");

        product = new Product();
        product.setId(1L);
        product.setTitle("T-Shirt");
        product.setCategory(category);
    }

    @Test
    void testFindById_ProductNotFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProductException.class, () -> productService.findById(1L));
    }

    @Test
    void testFindById_ProductFound() {
        // Arrange
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        Product foundProduct = productService.findById(1L);

        // Assert
        assertNotNull(foundProduct);
        assertEquals("T-Shirt", foundProduct.getTitle());
        assertEquals(1L, foundProduct.getId());
    }

    @Test
    void testFindAll() {
        // Arrange
        when(productRepository.findAll()).thenReturn(List.of(product));

        // Act
        List<Product> products = productService.findAll();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getTitle());
    }

    @Test
    void testExistsById_ProductExists() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(true);

        // Act
        boolean exists = productService.existsById(1L);

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsById_ProductNotExists() {
        // Arrange
        when(productRepository.existsById(1L)).thenReturn(false);

        // Act
        boolean exists = productService.existsById(1L);

        // Assert
        assertFalse(exists);
    }

    @Test
    void testSave() {
        // Arrange
        when(productRepository.save(product)).thenReturn(product);

        // Act
        productService.save(product);

        // Assert
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDeleteById() {
        // Act
        productService.deleteById(1L);

        // Assert
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testExistsByTitle_True() {
        // Arrange
        when(productRepository.existsByTitle("T-Shirt")).thenReturn(true);

        // Act
        boolean exists = productService.existsByTitle("T-Shirt");

        // Assert
        assertTrue(exists);
    }

    @Test
    void testExistsByTitle_False() {
        // Arrange
        when(productRepository.existsByTitle("T-Shirt")).thenReturn(false);

        // Act
        boolean exists = productService.existsByTitle("T-Shirt");

        // Assert
        assertFalse(exists);
    }

    @Test
    void testFindAllByCategory() {
        // Arrange
        when(productRepository.findAllByCategory(category)).thenReturn(List.of(product));

        // Act
        List<Product> products = productService.findAllByCategory(category);

        // Assert
        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getTitle());
    }
}
