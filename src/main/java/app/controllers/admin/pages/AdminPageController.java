package app.controllers.admin.pages;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
public class AdminPageController {

    @GetMapping
    public String adminPanel() {
        return "admin_main_page";
    }

    @GetMapping("edit-users/")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public String addUserPanel() {
        return "admin_edit_users";
    }

    @GetMapping("edit-concerts/")
    @PreAuthorize("hasAuthority('CONCERT_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editConcertsPanel() {
        return "admin_edit_concerts";
    }

    @GetMapping("edit-shop/")
    @PreAuthorize("hasAuthority('MERCH_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editShopPanel() {
        return "admin_edit_products";
    }

    @GetMapping("edit-music/")
    @PreAuthorize("hasAuthority('ALBUMS_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editAlbumsPanel() {
        return "admin_edit_music";
    }
}
