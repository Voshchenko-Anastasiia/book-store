package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.BookItemDTO;
import com.epam.rd.autocode.spring.project.dto.OrderDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('CLIENT')")
    public String getMyOrders(Principal principal, Model model) {
        model.addAttribute("orders", orderService.findAllByClient(principal.getName()));
        return "orders/list";
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('EMPLOYEE') or hasAuthority('ADMIN')")
    public String showOrders(Model model, Principal principal) {
        model.addAttribute("orders", orderService.getOrdersByEmployee(principal.getName()));
        return "orders";
    }
}