package app.controllers.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/shop/product-page/")
public class ShopProductController {

    @GetMapping("{productId}")
    public String getProductPage(
            @PathVariable Long productId, Model model
    ) {
        model.addAttribute("productId", productId);
        return "shop_product_page";
    }
}
