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

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ShopFilterGetCollectionController.class)
@Import(TestSecurityConfig.class)
public class ShopFilterGetCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService; // Мокаем интерфейс

    @Test
    public void testGetAllProductsByCategory_Success() throws Exception {
        Long categoryId = 1L;

        // Создаем список продуктов для тестирования
        List<Product> products = List.of(
                new Product(1L, "Title 1", "Product 1", 1200, 10,
                        new Category(1L, "Category 1"), Set.of(new ClothingSize(1, "Size 1")), Set.of("PhotoLink 1")),
                new Product(2L, "Title 2", "Product 2", 1201, 11,
                        new Category(2L, "Category 2"), Set.of(new ClothingSize(2, "Size 2")), Set.of("PhotoLink 2"))
        );

        // Мокируем сервис для возврата списка продуктов
        when(shopService.findAllProductsByCategory(categoryId)).thenReturn(products);

        // Выполняем GET-запрос и проверяем статус, а также содержимое ответа
        mockMvc.perform(get("/api/v1/edit-shop/get-all-by/product/category/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].title").value("Title 1"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].title").value("Title 2"));
    }

    @Test
    public void testGetAllProductsByCategory_NoProducts() throws Exception {
        Long categoryId = 2L;

        // Мокируем сервис для возврата пустого списка
        when(shopService.findAllProductsByCategory(categoryId)).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем, что ответ содержит пустой массив
        mockMvc.perform(get("/api/v1/edit-shop/get-all-by/product/category/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetAllProductsByCategory_CategoryNotFound() throws Exception {
        Long categoryId = 999L;

        // Мокируем ситуацию, когда категория не найдена (вероятно, возвращается пустой список или исключение)
        when(shopService.findAllProductsByCategory(categoryId))
                .thenReturn(List.of());

        // Выполняем GET-запрос и проверяем статус ошибки
        mockMvc.perform(get("/api/v1/edit-shop/get-all-by/product/category/{categoryId}", categoryId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
