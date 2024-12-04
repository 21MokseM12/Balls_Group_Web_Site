package app.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
public class AdminPanelController {

    @GetMapping
    public String adminPanel() {
        return "admin_main_panel";
    }

    @GetMapping("add-user/")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public String addUserPanel() {
        return "admin_add_user";
    }

    @GetMapping("/edit-concerts")
    @PreAuthorize("hasAuthority('CONCERT_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editConcertsPanel() {
        //todo
        return "";
    }

    @GetMapping("/edit-shop")
    @PreAuthorize("hasAuthority('MERCH_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editShopPanel() {
        //todo
        return "";
    }

    @GetMapping("/edit-albums")
    @PreAuthorize("hasAuthority('ALBUMS_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editAlbumsPanel() {
        //todo
        return "";
    }
}
