package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.service.OrderService;
import com.epam.rd.autocode.spring.project.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.security.Principal;

@Slf4j
@Controller
@RequestMapping("/cabinet")
public class CabinetController {
    private final OrderService orderService;
    private final UserService userService;

    public CabinetController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public String showDashboard(Principal principal, Model model) {
        User currentUser = userService.getUserByEmail(principal.getName());
        log.debug("Preparing dashboard for {} with balance: {}", currentUser.getEmail(), currentUser.getBalance());
        model.addAttribute("user", currentUser);
        System.out.println("DEBUG: User object hash code: " + System.identityHashCode(currentUser));
        return "cabinet/dashboard";
    }

    @GetMapping("/orders")
    public String getMyOrders(Principal principal, Model model) {
        User currentUser = userService.getUserByEmail(principal.getName());
        model.addAttribute("user", currentUser);
        model.addAttribute("orders", orderService.getOrdersByClient(principal.getName()));
        return "cabinet/orders";
    }

    @PostMapping("/top-up")
    public String topUpBalance(@RequestParam("amount") BigDecimal amount, Principal principal) {
        String email = (principal != null) ? principal.getName() : "NULL_PRINCIPAL";

        try {
            User user = userService.getUserByEmail(email);
            log.info("BUSINESS EVENT: Top-up of {} successful for user: {}", amount, email);
            userService.topUpBalance(email, amount);
        } catch (Exception e) {
            log.error("CRITICAL ERROR during top-up for {}: {}", email, e.getMessage(), e);
        }

        return "redirect:/cabinet?topupSuccess";
    }
}