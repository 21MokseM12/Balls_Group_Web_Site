package app.controllers.admin.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/shop/category-page/")
public class ShopCategoryController {

    @GetMapping("{categoryId}")
    public String getCategoryPage(
            @PathVariable Long categoryId,
            Model model
    ) {
        model.addAttribute("categoryId", categoryId);
        return "shop_category_page";
    }
}
