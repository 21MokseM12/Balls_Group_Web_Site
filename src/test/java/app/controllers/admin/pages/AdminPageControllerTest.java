package app.controllers.admin.pages;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(controllers = AdminPageController.class)
public class AdminPageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(authorities = { "SYSTEM_ADMIN", "CONCERT_MANAGER", "MERCH_MANAGER", "ALBUMS_MANAGER"})
    void adminPanelAccessibleByAnyUser() throws Exception {
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_main_page"));
    }

    @Test
    @WithMockUser(authorities = "SYSTEM_ADMIN")
    void addUserPanelAccessibleBySystemAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/edit-users/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_edit_users"));
    }

    @Test
    void addUserPanelForbiddenForUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/edit-users/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = {"CONCERT_MANAGER", "SYSTEM_ADMIN"})
    void editConcertsPanelAccessibleByConcertManagerOrSystemAdmin() throws Exception {
        mockMvc.perform(get("/api/v1/edit-concerts/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_edit_concerts"));
    }

    @Test
    @WithMockUser(authorities = "MERCH_MANAGER")
    void editShopPanelAccessibleByMerchManager() throws Exception {
        mockMvc.perform(get("/api/v1/edit-shop/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_edit_products"));
    }

    @Test
    void editShopPanelForbiddenForUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/edit-shop/"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(authorities = "ALBUMS_MANAGER")
    void editAlbumsPanelAccessibleByAlbumsManager() throws Exception {
        mockMvc.perform(get("/api/v1/edit-music/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_edit_music"));
    }

    @Test
    @WithMockUser(authorities = "MERCH_MANAGER")
    void editOrdersPanelAccessibleByMerchManager() throws Exception {
        mockMvc.perform(get("/api/v1/edit-shop/orders/"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin_edit_orders"));
    }

    @Test
    void editOrdersPanelForbiddenForUnauthenticated() throws Exception {
        mockMvc.perform(get("/api/v1/edit-shop/orders/"))
                .andExpect(status().isUnauthorized());
    }
}
