package app.controllers.admin;

import app.domain.entites.users.Account;
import app.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/v1/")
public class AdminPageController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public String adminPanel() {
        return "admin_main_page";
    }

    @GetMapping("edit-users/")
    @PreAuthorize("hasAuthority('SYSTEM_ADMIN')")
    public String addUserPanel(Model model) {
        Iterable<Account> accounts = accountRepository.findAll();
        model.addAttribute("accounts", accounts);
        return "admin_edit_users";
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
        return "admin_edit_products";
    }

    @GetMapping("/edit-albums")
    @PreAuthorize("hasAuthority('ALBUMS_MANAGER') || hasAuthority('SYSTEM_ADMIN')")
    public String editAlbumsPanel() {
        //todo
        return "";
    }
}
