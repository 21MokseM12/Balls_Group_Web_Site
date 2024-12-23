package app.service.controllers.admin.shop.product;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.category.CategoryService;
import app.service.controllers.admin.shop.clothing.ClothingSizeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProductManagementServiceTest {

    @Mock
    private ProductService productService;

    @Mock
    private CategoryService categoryService;

    @Mock
    private ClothingSizeService clothingSizeService;

    @InjectMocks
    private ProductManagementService productManagementService;

    private Product product;
    private Category category;
    private ClothingSize clothingSize;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестового товара, категории и размера одежды
        category = new Category();
        category.setId(1L);
        category.setCategory("Clothing");

        clothingSize = new ClothingSize();
        clothingSize.setSize("M");

        product = new Product();
        product.setId(1L);
        product.setTitle("T-Shirt");
        product.setCategory(category);
        product.setClothingSize(Collections.singleton(clothingSize));
        product.setQuantityInStock(10);
    }

    @Test
    void testFindAllProducts() {
        // Arrange
        when(productService.findAll()).thenReturn(List.of(product));

        // Act
        List<Product> products = productManagementService.findAllProducts();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getTitle());
    }

    @Test
    void testFindProductById() {
        // Arrange
        when(productService.findById(1L)).thenReturn(product);

        // Act
        Optional<Product> foundProduct = productManagementService.findProductById(1L);

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals("T-Shirt", foundProduct.get().getTitle());
    }

    @Test
    void testAddProduct_ProductExists() {
        // Arrange
        when(productService.existsByTitle("T-Shirt")).thenReturn(true);

        // Act
        ResponseEntity<String> response = productManagementService.addProduct(product);

        // Assert
        assertEquals("Товар с таким названием уже существует", response.getBody());
    }

    @Test
    void testAddProduct_CategoryNotFound() {
        // Arrange
        when(productService.existsByTitle("T-Shirt")).thenReturn(false);
        when(categoryService.existsByCategory(product.getCategory())).thenReturn(false);

        // Act
        ResponseEntity<String> response = productManagementService.addProduct(product);

        // Assert
        assertEquals("Выбранная категория не найдена", response.getBody());
    }

    @Test
    void testAddProduct_Success() {
        // Arrange
        when(productService.existsByTitle("T-Shirt")).thenReturn(false);
        when(categoryService.existsByCategory(product.getCategory())).thenReturn(true);
        when(categoryService.findByCategory(product.getCategory())).thenReturn(category);
        when(clothingSizeService.findBySize("M")).thenReturn(clothingSize);

        // Act
        ResponseEntity<String> response = productManagementService.addProduct(product);

        // Assert
        assertEquals("Товар успешно добавлен!", response.getBody());
    }

    @Test
    void testUpdateProduct_ProductNotFound() {
        // Arrange
        when(productService.existsById(1L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = productManagementService.updateProduct(product);

        // Assert
        assertEquals("Товар не был найден", response.getBody());
    }

    @Test
    void testUpdateProduct_Success() {
        // Arrange
        when(productService.existsById(1L)).thenReturn(true);
        when(productService.findById(1L)).thenReturn(product);

        // Act
        ResponseEntity<String> response = productManagementService.updateProduct(product);

        // Assert
        assertEquals("Товар был успешно обновлен!", response.getBody());
    }

    @Test
    void testDeleteProduct_ProductNotFound() {
        // Arrange
        when(productService.existsById(1L)).thenReturn(false);

        // Act
        ResponseEntity<String> response = productManagementService.deleteProduct(1L);

        // Assert
        assertEquals("Товар не найден", response.getBody());
    }

    @Test
    void testDeleteProduct_Success() {
        // Arrange
        when(productService.existsById(1L)).thenReturn(true);

        // Act
        ResponseEntity<String> response = productManagementService.deleteProduct(1L);

        // Assert
        assertEquals("Товар был успешно удален!", response.getBody());
    }

    @Test
    void testFindAllProductsByCategory() {
        // Arrange
        when(categoryService.findById(1L)).thenReturn(category);
        when(productService.findAllByCategory(category)).thenReturn(List.of(product));

        // Act
        List<Product> products = productManagementService.findAllProductsByCategory(1L);

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getTitle());
    }

    @Test
    void testDecrementStock() {
        // Arrange
        when(productService.findById(1L)).thenReturn(product);

        // Act
        ResponseEntity<String> response = productManagementService.decrementStock(1L, 5);

        // Assert
        assertEquals("Операция выполнена успешно", response.getBody());
        assertEquals(5, product.getQuantityInStock());
    }
}
