package app.controllers.admin.api.shop;

import app.domain.entites.shop.Category;
import app.domain.entites.shop.ClothingSize;
import app.domain.entites.shop.Product;
import app.service.controllers.admin.shop.shop_management.ShopService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopGetController.class)
@Import(TestSecurityConfig.class)
public class ShopGetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    void shouldReturnProductWhenExists() throws Exception {
        // Подготовка тестовых данных
        Long productId = 1L;
        Product product1 = new Product(1L, "Title 1", "Product 1", 1200, 10,
                new Category(1L, "Category 1"), Set.of(new ClothingSize(1, "Size 1")), Set.of("PhotoLink 1"));
        Optional<Product> optionalProduct = Optional.of(product1);

        // Задаем поведение мока
        when(shopService.getProduct(productId)).thenReturn(optionalProduct);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "title": "Title 1",
                                      "description": "Product 1",
                                      "price": 1200,
                                      "quantityInStock": 10,
                                      "category": {
                                                    "id": 1,
                                                    "category": "Category 1"
                                                  },
                                      "clothingSize": [
                                                        {
                                                          "id": 1,
                                                          "size": "Size 1"
                                                        }
                                                      ],
                                      "productPhotoLinks": [
                                                              "PhotoLink 1"
                                                           ]
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getProduct(productId);
    }

    @Test
    void shouldReturnNotFoundWhenProductDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Long productId = 999L;

        // Задаем поведение мока
        when(shopService.getProduct(productId)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/product/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidProductIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-shop/get/product/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCategoryWhenExists() throws Exception {
        // Подготовка тестовых данных
        Long categoryId = 1L;
        Category category1 = new Category(1L, "Category 1");
        Optional<Category> optionalCategory = Optional.of(category1);

        // Задаем поведение мока
        when(shopService.getCategory(categoryId)).thenReturn(optionalCategory);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "category": "Category 1"
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getCategory(categoryId);
    }

    @Test
    void shouldReturnNotFoundWhenCategoryDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Long categoryId = 999L;

        // Задаем поведение мока
        when(shopService.getCategory(categoryId)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/category/{id}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidCategoryIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-shop/get/category/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnClothingSizeWhenExists() throws Exception {
        // Подготовка тестовых данных
        Integer sizeId = 1;
        ClothingSize size1 = new ClothingSize(1, "Size 1");
        Optional<ClothingSize> sizeOptional = Optional.of(size1);

        // Задаем поведение мока
        when(shopService.getClothingSize(sizeId)).thenReturn(sizeOptional);

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/clothing-size/{id}", sizeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                                    {
                                      "id": 1,
                                      "size": "Size 1"
                                    }
                                    """));
        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getClothingSize(sizeId);
    }

    @Test
    void shouldReturnNotFoundWhenClothingSizeDoesNotExist() throws Exception {
        // Подготовка тестовых данных
        Integer sizeId = 999;

        // Задаем поведение мока
        when(shopService.getClothingSize(sizeId)).thenReturn(Optional.empty());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get/clothing-size/{id}", sizeId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldHandleInvalidClothingSizeIdFormat() throws Exception {
        // Выполняем GET-запрос с некорректным ID
        mockMvc.perform(get("/api/v1/edit-shop/get/clothing-size/{id}", "invalidId")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
