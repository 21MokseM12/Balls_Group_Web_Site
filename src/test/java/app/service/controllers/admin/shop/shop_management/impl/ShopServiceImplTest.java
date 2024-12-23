package app.service.controllers.admin.shop.shop_management.impl;

import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.category.CategoryManagementService;
import app.service.controllers.admin.shop.clothing.ClothingSizeManagementService;
import app.service.controllers.admin.shop.customer.CustomerManagementService;
import app.service.controllers.admin.shop.order.OrderManagementService;
import app.service.controllers.admin.shop.product.ProductManagementService;
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

public class ShopServiceImplTest {

    @Mock
    private CategoryManagementService categoryService;

    @Mock
    private ClothingSizeManagementService clothingSizeService;

    @Mock
    private ProductManagementService productService;

    @Mock
    private OrderManagementService orderService;

    @Mock
    private CustomerManagementService customerService;

    @InjectMocks
    private ShopServiceImpl shopService;

    private Product product;
    private Category category;
    private ClothingSize clothingSize;
    private Customer customer;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создание тестового товара, категории, размера одежды, клиента и заказа
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

        customer = new Customer();
        customer.setId(1L);
        customer.setName("John Doe");

        orderDTO = new OrderDTO();
        orderDTO.setCustomerId(1L);
        orderDTO.setOrderedProductId(1L);
    }

    @Test
    void testGetAllProducts() {
        // Arrange
        when(productService.findAllProducts()).thenReturn(List.of(product));

        // Act
        List<Product> products = shopService.getAllProducts();

        // Assert
        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(1, products.size());
        assertEquals("T-Shirt", products.get(0).getTitle());
    }

    @Test
    void testGetProduct() {
        // Arrange
        when(productService.findProductById(1L)).thenReturn(Optional.of(product));

        // Act
        Optional<Product> foundProduct = shopService.getProduct(1L);

        // Assert
        assertTrue(foundProduct.isPresent());
        assertEquals("T-Shirt", foundProduct.get().getTitle());
    }

    @Test
    void testAddProduct() {
        // Arrange
        when(productService.addProduct(product)).thenReturn(ResponseEntity.ok("Товар успешно добавлен!"));

        // Act
        ResponseEntity<String> response = shopService.addProduct(product);

        // Assert
        assertEquals("Товар успешно добавлен!", response.getBody());
    }

    @Test
    void testUpdateProduct() {
        // Arrange
        when(productService.updateProduct(product)).thenReturn(ResponseEntity.ok("Товар был успешно обновлен!"));

        // Act
        ResponseEntity<String> response = shopService.updateProduct(product);

        // Assert
        assertEquals("Товар был успешно обновлен!", response.getBody());
    }

    @Test
    void testDeleteProduct() {
        // Arrange
        when(productService.deleteProduct(1L)).thenReturn(ResponseEntity.ok("Товар был успешно удален!"));

        // Act
        ResponseEntity<String> response = shopService.deleteProduct(1L);

        // Assert
        assertEquals("Товар был успешно удален!", response.getBody());
    }

    @Test
    void testGetAllCategories() {
        // Arrange
        when(categoryService.findAllCategories()).thenReturn(List.of(category));

        // Act
        List<Category> categories = shopService.getAllCategories();

        // Assert
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
        assertEquals("Clothing", categories.get(0).getCategory());
    }

    @Test
    void testGetCategory() {
        // Arrange
        when(categoryService.findCategoryById(1L)).thenReturn(Optional.of(category));

        // Act
        Optional<Category> foundCategory = shopService.getCategory(1L);

        // Assert
        assertTrue(foundCategory.isPresent());
        assertEquals("Clothing", foundCategory.get().getCategory());
    }

    @Test
    void testAddCategory() {
        // Arrange
        when(categoryService.addCategory(category)).thenReturn(ResponseEntity.ok("Категория успешно добавлена!"));

        // Act
        ResponseEntity<String> response = shopService.addCategory(category);

        // Assert
        assertEquals("Категория успешно добавлена!", response.getBody());
    }

    @Test
    void testDeleteCategory() {
        // Arrange
        when(categoryService.deleteCategory(1L)).thenReturn(ResponseEntity.ok("Категория успешно удалена!"));

        // Act
        ResponseEntity<String> response = shopService.deleteCategory(1L);

        // Assert
        assertEquals("Категория успешно удалена!", response.getBody());
    }

    @Test
    void testAddClothingSize() {
        // Arrange
        when(clothingSizeService.addClothingSize(clothingSize)).thenReturn(ResponseEntity.ok("Размер успешно добавлен!"));

        // Act
        ResponseEntity<String> response = shopService.addClothingSize(clothingSize);

        // Assert
        assertEquals("Размер успешно добавлен!", response.getBody());
    }

    @Test
    void testDeleteClothingSize() {
        // Arrange
        when(clothingSizeService.deleteClothingSize(1)).thenReturn(ResponseEntity.ok("Размер успешно удален!"));

        // Act
        ResponseEntity<String> response = shopService.deleteClothingSize(1);

        // Assert
        assertEquals("Размер успешно удален!", response.getBody());
    }

    @Test
    void testAddCustomer() {
        // Arrange
        when(customerService.addCustomer(customer)).thenReturn(ResponseEntity.ok("Клиент успешно добавлен!"));

        // Act
        ResponseEntity<String> response = shopService.addCustomer(customer);

        // Assert
        assertEquals("Клиент успешно добавлен!", response.getBody());
    }

    @Test
    void testAddOrder() {
        // Arrange
        when(orderService.addOrder(orderDTO)).thenReturn(ResponseEntity.ok("Заказ успешно оформлен!"));

        // Act
        ResponseEntity<String> response = shopService.addOrder(orderDTO);

        // Assert
        assertEquals("Заказ успешно оформлен!", response.getBody());
    }

    @Test
    void testDeleteOrder() {
        // Arrange
        when(orderService.deleteOrder(1L)).thenReturn(ResponseEntity.ok("Заказ успешно удален!"));

        // Act
        ResponseEntity<String> response = shopService.deleteOrder(1L);

        // Assert
        assertEquals("Заказ успешно удален!", response.getBody());
    }
}
