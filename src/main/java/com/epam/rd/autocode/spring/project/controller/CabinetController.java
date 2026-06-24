package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/cabinet")
public class CabinetController {
    private final OrderService orderService;

    public CabinetController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showDashboard(Principal principal) {
        return "cabinet/dashboard";
    }

    @GetMapping("/orders")
    public String getMyOrders(Principal principal, Model model) {
        model.addAttribute("orders", orderService.getOrdersByClient(principal.getName()));
        return "cabinet/orders";
    }
}