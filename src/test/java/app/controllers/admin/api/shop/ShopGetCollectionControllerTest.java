package app.controllers.admin.api.shop;

import app.domain.entites.shop.*;
import app.service.controllers.admin.shop.shop_management.ShopService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopGetCollectionController.class)
@Import(TestSecurityConfig.class)
public class ShopGetCollectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService;

    @Test
    public void testGetCollectionProductSuccess() throws Exception {
        Product product1 = new Product(1L, "Title 1", "Product 1", 1200, 10,
                new Category(1L, "Category 1"), Set.of(new ClothingSize(1, "Size 1")), Set.of("PhotoLink 1"));
        Product product2 = new Product(2L, "Title 2", "Product 2", 1201, 11,
                new Category(2L, "Category 2"), Set.of(new ClothingSize(2, "Size 2")), Set.of("PhotoLink 2"));
        List<Product> products = Arrays.asList(product1, product2);

        // Задаем поведение мока для интерфейса
        when(shopService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/api/v1/edit-shop/get-all/products/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                      """
                                [
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
                                  },
                                  {
                                  "id": 2,
                                  "title": "Title 2",
                                  "description": "Product 2",
                                  "price": 1201,
                                  "quantityInStock": 11,
                                  "category": {
                                                "id": 2,
                                                "category": "Category 2"
                                              },
                                  "clothingSize": [
                                                    {
                                                      "id": 2,
                                                      "size": "Size 2"
                                                    }
                                                  ],
                                  "productPhotoLinks": [
                                                          "PhotoLink 2"
                                                       ]
                                  }
                                ]
                                """));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getAllProducts();
    }

    @Test
    public void testGetCollectionProductNotFound() throws Exception {
        // Задаем поведение мока
        when(shopService.getAllProducts()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get-all/products/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetCollectionCategorySuccess() throws Exception {
        Category category1 = new Category(1L, "Category 1");
        Category category2 = new Category(2L, "Category 2");
        List<Category> categories = Arrays.asList(category1, category2);

        // Задаем поведение мока для интерфейса
        when(shopService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/v1/edit-shop/get-all/categories/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                  [
                                    {
                                       "id": 1,
                                       "category": "Category 1"
                                    },
                                    {
                                       "id": 2,
                                       "category": "Category 2"
                                    }
                                  ]
                                  """));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getAllCategories();
    }

    @Test
    public void testGetCollectionCategoryNotFound() throws Exception {
        // Задаем поведение мока
        when(shopService.getAllCategories()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get-all/categories/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetCollectionClothingSizeSuccess() throws Exception {
        ClothingSize size1 = new ClothingSize(1, "Size 1");
        ClothingSize size2 = new ClothingSize(2, "Size 2");
        List<ClothingSize> clothingSizes = Arrays.asList(size1, size2);

        // Задаем поведение мока для интерфейса
        when(shopService.getAllClothingSizes()).thenReturn(clothingSizes);

        mockMvc.perform(get("/api/v1/edit-shop/get-all/clothing-sizes/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                  [
                                    {
                                       "id": 1,
                                       "size": "Size 1"
                                    },
                                    {
                                       "id": 2,
                                       "size": "Size 2"
                                    }
                                  ]
                                  """));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getAllClothingSizes();
    }

    @Test
    public void testGetCollectionClothingSizeNotFound() throws Exception {
        // Задаем поведение мока
        when(shopService.getAllClothingSizes()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get-all/clothing-sizes/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetCollectionOrderSuccess() throws Exception {
        Order order1 = new Order(1L,
                new Customer(
                        1L,
                        "Name 1",
                        "Phone 1",
                        "Email 1",
                        "Address 1"
                ),
                new OrderedProduct(
                        1L,
                        "Title 1",
                        1,
                        new ClothingSize(1, "Size 1")
                )
        );
        Order order2 = new Order(2L,
                new Customer(
                        2L,
                        "Name 2",
                        "Phone 2",
                        "Email 2",
                        "Address 2"
                ),
                new OrderedProduct(
                        2L,
                        "Title 2",
                        2,
                        new ClothingSize(2, "Size 2")
                )
        );
        List<Order> orders = Arrays.asList(order1, order2);

        // Задаем поведение мока для интерфейса
        when(shopService.getAllOrders()).thenReturn(orders);

        mockMvc.perform(get("/api/v1/edit-shop/get-all/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                  [
                                    {
                                    "id": 1,
                                    "customer": {
                                                  "id": 1,
                                                  "name": "Name 1",
                                                  "phone": "Phone 1",
                                                  "email": "Email 1",
                                                  "address": "Address 1"
                                                },
                                    "orderedProduct": {
                                                        "id": 1,
                                                        "title": "Title 1",
                                                        "price": 1,
                                                        "size": {
                                                                  "id": 1,
                                                                  "size": "Size 1"
                                                                }
                                                      }
                                    },
                                    {
                                    "id": 2,
                                    "customer": {
                                                  "id": 2,
                                                  "name": "Name 2",
                                                  "phone": "Phone 2",
                                                  "email": "Email 2",
                                                  "address": "Address 2"
                                                },
                                    "orderedProduct": {
                                                        "id": 2,
                                                        "title": "Title 2",
                                                        "price": 2,
                                                        "size": {
                                                                  "id": 2,
                                                                  "size": "Size 2"
                                                                }
                                                      }
                                    }
                                  ]
                                  """));

        // Проверяем, что метод был вызван
        verify(shopService, times(1)).getAllOrders();
    }

    @Test
    public void testGetCollectionOrderNotFound() throws Exception {
        // Задаем поведение мока
        when(shopService.getAllOrders()).thenReturn(List.of());

        // Выполняем GET-запрос и проверяем результат
        mockMvc.perform(get("/api/v1/edit-shop/get-all/orders/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
