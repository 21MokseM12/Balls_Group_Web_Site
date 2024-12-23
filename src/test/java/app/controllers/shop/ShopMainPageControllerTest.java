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

@WebMvcTest(controllers = ShopMainPageController.class)
@Import(TestSecurityConfig.class)
public class ShopMainPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMainShopPage_ReturnsMainPageOfNikonshop() throws Exception {
        mockMvc.perform(get("/main/shop/"))
                .andExpect(status().isOk())
                .andExpect(view().name("shop_main_page"));
    }
}
