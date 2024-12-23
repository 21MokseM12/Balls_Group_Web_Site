package app.controllers.admin.api.shop;

import app.domain.entites.shop.Customer;
import app.domain.entites.shop.OrderDTO;
import app.domain.entites.shop.OrderedProduct;
import app.service.controllers.admin.shop.shop_management.ShopService;
import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = ShopOrderPostController.class)
@Import(TestSecurityConfig.class)
public class ShopOrderPostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShopService shopService; // Мокаем интерфейс

    @Test
    public void testAddOrder_Success() throws Exception {
        when(shopService.addOrder(any(OrderDTO.class))).thenReturn(ResponseEntity.ok("Order added successfully"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "orderedProductId": 1,
                                    "customerId": 1
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Order added successfully"));
    }

    @Test
    public void testAddCustomer_Success() throws Exception {
        when(shopService.addCustomer(any(Customer.class))).thenReturn(ResponseEntity.ok("Customer added successfully"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "Name",
                                    "phone": "Phone",
                                    "email": "Email",
                                    "address": "Address"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Customer added successfully"));
    }

    @Test
    public void testAddOrderedProduct_Success() throws Exception {
        when(shopService.addOrderedProduct(any(OrderedProduct.class))).thenReturn(ResponseEntity.ok("Product added successfully"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "id": 1,
                                    "name": "Product",
                                    "price": 100,
                                    "size": {
                                              "id": 1,
                                              "size": "Size"
                                            }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Product added successfully"));
    }

    @Test
    public void testAddOrder_Failure() throws Exception {
        when(shopService.addOrder(any(OrderDTO.class))).thenReturn(ResponseEntity.badRequest().body("Invalid order data"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid order data"));
    }

    @Test
    public void testAddCustomer_Failure() throws Exception {
        when(shopService.addCustomer(any(Customer.class))).thenReturn(ResponseEntity.badRequest().body("Invalid customer data"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid customer data"));
    }

    @Test
    public void testAddOrderedProduct_Failure() throws Exception {
        when(shopService.addOrderedProduct(any(OrderedProduct.class))).thenReturn(ResponseEntity.badRequest().body("Invalid product data"));

        mockMvc.perform(post("/api/v1/edit-shop/add/order/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid product data"));
    }
}
