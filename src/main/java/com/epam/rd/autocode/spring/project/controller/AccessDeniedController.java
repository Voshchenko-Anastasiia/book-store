package com.epam.rd.autocode.spring.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        model.addAttribute("errorMessage", "Access Denied: You do not have permission to view this page.");
        model.addAttribute("status", 403);
        return "error";
    }
}