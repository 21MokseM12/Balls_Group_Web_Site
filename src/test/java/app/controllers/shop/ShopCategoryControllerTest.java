package app.controllers.shop;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(controllers = ShopCategoryController.class)
@Import(TestSecurityConfig.class)
public class ShopCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCategoryPage_ReturnsCategoryPageWithCategoryId() throws Exception {
        Long testCategoryId = 1L;

        mockMvc.perform(get("/main/shop/category-page/" + testCategoryId))
                .andExpect(status().isOk())
                .andExpect(view().name("shop_category_page"))
                .andExpect(model().attribute("categoryId", testCategoryId));
    }

    @Test
    void getCategoryPage_InvalidCategoryId_Returns404() throws Exception {
        mockMvc.perform(get("/main/shop/category-page/invalid"))
                .andExpect(status().isBadRequest());
    }
}
