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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(controllers = ShopUpdateController.class)
@Import(TestSecurityConfig.class)
public class ShopUpdateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    public void testUpdateProduct_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(shopService.updateProduct(any(Product.class)))
                .thenReturn(ResponseEntity.ok("Product updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/product/")
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
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Product updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateProduct_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(shopService.updateProduct(any(Product.class)))
                .thenReturn(new ResponseEntity<>("Error updating product", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/product/")
                        .contentType("application/json")
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
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating product"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateCategory_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(shopService.updateCategory(any(Category.class)))
                .thenReturn(ResponseEntity.ok("Category updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": 1,
                                      "category": "Category 1"
                                  }
                             """))
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Category updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateCategory_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(shopService.updateCategory(any(Category.class)))
                .thenReturn(new ResponseEntity<>("Error updating category", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/category/")
                        .contentType("application/json")
                        .content("""
                                  {
                                      "id": 1,
                                      "category": "Category 1"
                                  }
                             """))
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating category"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateClothingSize_Success() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал успешный ответ
        when(shopService.updateClothingSize(any(ClothingSize.class)))
                .thenReturn(ResponseEntity.ok("Size updated successfully"));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/clothing-size/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                  {
                                      "id": 1,
                                      "size": "Size 1"
                                  }
                             """))
                .andExpect(status().isOk())  // Проверка статус-кода 200 OK
                .andExpect(content().string("Size updated successfully"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testUpdateClothingSize_Failure() throws Exception {
        // Мокируем сервис, чтобы метод updateConcert возвращал ошибку
        when(shopService.updateClothingSize(any(ClothingSize.class)))
                .thenReturn(new ResponseEntity<>("Error updating size", HttpStatus.BAD_REQUEST));

        // Выполняем PUT-запрос и проверяем статус ответа и его содержимое
        mockMvc.perform(put("/api/v1/edit-shop/update/clothing-size/")
                        .contentType("application/json")
                        .content("""
                                  {
                                      "id": 1,
                                      "size": "Size 1"
                                  }
                             """))
                .andExpect(status().isBadRequest())  // Проверка статус-кода 400 Bad Request
                .andExpect(content().string("Error updating size"));  // Проверка содержимого тела ответа
    }

    @Test
    public void testDecrementStock_Success() throws Exception {
        Long productId = 1L;
        int decrementBy = 5;

        // Мокируем успешный ответ сервиса
        when(shopService.decrementStock(productId, decrementBy))
                .thenReturn(ResponseEntity.ok("Stock decremented successfully"));

        // Выполняем POST-запрос
        mockMvc.perform(post("/api/v1/edit-shop/update/product/{productId}/decrement/stock/", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "decrementBy": 5
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock decremented successfully"));
    }

    @Test
    public void testDecrementStock_DefaultDecrement() throws Exception {
        Long productId = 2L;

        // Мокируем успешный ответ сервиса с использованием значения по умолчанию
        when(shopService.decrementStock(productId, 1))
                .thenReturn(ResponseEntity.ok("Stock decremented by default value"));

        // Выполняем POST-запрос без параметра decrementBy
        mockMvc.perform(post("/api/v1/edit-shop/update/product/{productId}/decrement/stock/", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Stock decremented by default value"));
    }

    @Test
    public void testDecrementStock_ProductNotFound() throws Exception {
        Long productId = 999L;
        int decrementBy = 3;

        // Мокируем ответ сервиса для случая, когда продукт не найден
        when(shopService.decrementStock(productId, decrementBy))
                .thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found"));

        // Выполняем POST-запрос
        mockMvc.perform(post("/api/v1/edit-shop/update/product/{productId}/decrement/stock/", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "decrementBy": 3
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product not found"));
    }

    @Test
    public void testDecrementStock_InvalidRequest() throws Exception {
        Long productId = 1L;

        // Мокируем ответ сервиса для случая некорректного запроса
        when(shopService.decrementStock(productId, -5))
                .thenReturn(ResponseEntity.badRequest().body("Invalid decrement value"));

        // Выполняем POST-запрос с некорректным значением decrementBy
        mockMvc.perform(post("/api/v1/edit-shop/update/product/{productId}/decrement/stock/", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "decrementBy": -5
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid decrement value"));
    }
}
