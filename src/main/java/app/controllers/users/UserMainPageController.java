package app.controllers.users;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/main/")
public class UserMainPageController {

    @GetMapping
    public String mainPage() {
        return "mainPage";
    }

}
