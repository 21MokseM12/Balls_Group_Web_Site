package app.controllers.users;

import config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserMainPageController.class)
@Import(TestSecurityConfig.class)
public class UserMainPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getMainWebSitePage_ReturnsMainPageOfWebSite() throws Exception {
        mockMvc.perform(get("/main/"))
                .andExpect(status().isOk())
                .andExpect(view().name("mainPage"));
    }
}
