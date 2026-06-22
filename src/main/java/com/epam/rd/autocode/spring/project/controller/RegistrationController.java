package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", new UserRegistrationDTO());
        return "register";
    }

    @PostMapping("/register")
    public String registerUserAccount(
            @Valid @ModelAttribute("userDto")
            UserRegistrationDTO userDto,
            BindingResult result)
    {

        if (result.hasErrors()) {
            return "register";
        }

        if (userService.getUserByEmail(userDto.getEmail()).isPresent()) {
            result.rejectValue("email", "registration.error.email.exists", "Email already registered");
            return "register";
        }

        userService.registerNewUser(userDto);

        return "redirect:/login?success";
    }
}