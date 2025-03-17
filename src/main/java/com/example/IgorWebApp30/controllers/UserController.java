package com.example.IgorWebApp30.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserController {

    @GetMapping("/user")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user", principal.getName());
        return "user";
    }


}