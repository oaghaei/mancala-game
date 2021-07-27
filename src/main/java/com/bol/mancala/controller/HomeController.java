package com.bol.mancala.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/swagger")
    public String swagger() {
        return "redirect:swagger-ui.html";
    }

    @RequestMapping("/")
    public String index() {
        return "index.html";
    }

}
