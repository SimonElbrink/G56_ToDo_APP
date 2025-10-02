package se.lexicon.todo_app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

     //localhost:9090/home
    @RequestMapping("/home")
    public String home() {
        return "Home.html";
    }


}
