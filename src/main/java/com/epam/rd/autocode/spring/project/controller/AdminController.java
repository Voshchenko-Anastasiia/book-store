package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.service.OrderService;
import com.epam.rd.autocode.spring.project.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')") // Only Admins can enter
public class AdminController {

    private final OrderService orderService;
    private final UserService userService;

    public AdminController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "admin-orders";
    }

    @PostMapping("/orders/{id}/update")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }

    @GetMapping("/users")
    public String showUsers(Model model) {
        model.addAttribute("users", userService.findAllNonAdminUsers());
        return "admin-users";
    }

    @PostMapping("/users/create-employee")
    @PreAuthorize("hasRole('ADMIN')")
    public String createEmployee(@ModelAttribute("userDTO") UserRegistrationDTO userDTO) {
        userDTO.setRole("EMPLOYEE");
        userService.registerNewUser(userDTO);
        return "redirect:/admin/users";
    }

    @PostMapping("/users/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }
}