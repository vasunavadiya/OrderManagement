package example.ordermanagement.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping("/")
    private String homePage(){
        return "redirect:/order/create";
    }
}
