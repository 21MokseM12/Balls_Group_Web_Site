package app.controllers.shop;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ShopProductController.class)
@Import(TestSecurityConfig.class)
public class ShopProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getProductPage_ReturnsProductPageWithProductId() throws Exception {
        Long testProductId = 1L;

        mockMvc.perform(get("/main/shop/product-page/" + testProductId))
                .andExpect(status().isOk())
                .andExpect(view().name("shop_product_page"))
                .andExpect(model().attribute("productId", testProductId));
    }

    @Test
    void getCategoryPage_InvalidCategoryId_Returns404() throws Exception {
        mockMvc.perform(get("/main/shop/product-page/invalid"))
                .andExpect(status().isBadRequest());
    }
}
