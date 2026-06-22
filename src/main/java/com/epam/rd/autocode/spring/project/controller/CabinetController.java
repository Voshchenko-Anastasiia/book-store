package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class CabinetController {
    private final OrderService orderService;

    public CabinetController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/my-orders")
    public String getMyOrders(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        String email = principal.getName();
        model.addAttribute("orders", orderService.getOrdersByClient(email));

        return "cabinet";
    }
}