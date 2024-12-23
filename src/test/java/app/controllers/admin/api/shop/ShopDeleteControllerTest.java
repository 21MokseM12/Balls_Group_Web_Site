package app.controllers.admin.api.shop;

import app.service.controllers.admin.shop.shop_management.ShopService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopDeleteController.class)
@Import(TestSecurityConfig.class)
public class ShopDeleteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService; // Мокаем интерфейс

    @Test
    public void testDeleteProductSuccess() throws Exception {
        Long productId = 1L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteProduct(productId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Product deleted"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/product/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testDeleteProductNotFound() throws Exception {
        Long productId = 2L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteProduct(productId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/product/{id}", productId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));

        verify(shopService, times(1)).deleteProduct(productId);
    }

    @Test
    public void testDeleteCategorySuccess() throws Exception {
        Long categoryId = 1L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteCategory(categoryId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Category deleted"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Category deleted"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testDeleteCategoryNotFound() throws Exception {
        Long categoryId = 2L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteCategory(categoryId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/category/{id}", categoryId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Category not found"));

        verify(shopService, times(1)).deleteCategory(categoryId);
    }

    @Test
    public void testDeleteClothingSizeSuccess() throws Exception {
        Integer sizeId = 1;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteClothingSize(sizeId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Size deleted"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/clothing-size/{id}", sizeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Size deleted"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).deleteClothingSize(sizeId);
    }

    @Test
    public void testDeleteClothingSizeNotFound() throws Exception {
        Integer sizeId = 2;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteClothingSize(sizeId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Size not found"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/clothing-size/{id}", sizeId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Size not found"));

        verify(shopService, times(1)).deleteClothingSize(sizeId);
    }

    @Test
    public void testDeleteOrderSuccess() throws Exception {
        Long orderId = 1L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteOrder(orderId)).thenReturn(ResponseEntity.status(HttpStatus.OK).body("Order deleted"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/order/{id}", orderId))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).deleteOrder(orderId);
    }

    @Test
    public void testDeleteOrderNotFound() throws Exception {
        Long orderId = 2L;

        // Задаем поведение мока для интерфейса
        when(shopService.deleteOrder(orderId)).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order not found"));

        mockMvc.perform(delete("/api/v1/edit-shop/delete/order/{id}", orderId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Order not found"));

        verify(shopService, times(1)).deleteOrder(orderId);
    }
}

