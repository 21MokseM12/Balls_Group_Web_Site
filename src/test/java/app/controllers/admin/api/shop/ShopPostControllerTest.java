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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopPostController.class)
@Import(TestSecurityConfig.class)
public class ShopPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    public void testAddProduct_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(shopService.addProduct(any(Product.class)))
                .thenReturn(ResponseEntity.ok("Product added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
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
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).addProduct(any(Product.class));
    }

    @Test
    public void testAddProduct_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(shopService.addProduct(any(Product.class)))
                .thenReturn(new ResponseEntity<>("Error adding product", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/product/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding product"));
    }

    @Test
    public void testAddCategory_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(shopService.addCategory(any(Category.class)))
                .thenReturn(ResponseEntity.ok("Category added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                   "id": 1,
                                   "category": "Category 1"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Category added successfully"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).addCategory(any(Category.class));
    }

    @Test
    public void testAddCategory_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(shopService.addCategory(any(Category.class)))
                .thenReturn(new ResponseEntity<>("Error adding category", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/category/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding category"));
    }

    @Test
    public void testAddClothingSize_Success() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал успешный ответ
        when(shopService.addClothingSize(any(ClothingSize.class)))
                .thenReturn(ResponseEntity.ok("Size added successfully"));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/clothing-size/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                 {
                                    "id": 1,
                                    "size": "Size 1"
                                 }
                                 """))
                .andExpect(status().isOk())
                .andExpect(content().string("Size added successfully"));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).addClothingSize(any(ClothingSize.class));
    }

    @Test
    public void testAddClothingSize_Failure() throws Exception {
        // Мокируем сервис, чтобы метод addConcert возвращал ошибку
        when(shopService.addClothingSize(any(ClothingSize.class)))
                .thenReturn(new ResponseEntity<>("Error adding size", HttpStatus.BAD_REQUEST));

        // Выполняем POST-запрос и проверяем статус ответа
        mockMvc.perform(post("/api/v1/edit-shop/add/clothing-size/")
                        .contentType("application/json")
                        .content("{\"name\": \"Invalid Concert\", \"date\": \"2024-12-25\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error adding size"));
    }
}
