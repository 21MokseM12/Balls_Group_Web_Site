package app.controllers.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/shop/")
public class ShopMainPageController {

    @GetMapping
    public String shopPage(Model model) {
        return "shop_main_page";
    }

}
