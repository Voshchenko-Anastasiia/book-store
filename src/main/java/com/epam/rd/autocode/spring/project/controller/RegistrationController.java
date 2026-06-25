package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.aspect.LoggingAspect;
import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.exception.UserNotFoundException;
import com.epam.rd.autocode.spring.project.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
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

        try {
            userService.getUserByEmail(userDto.getEmail());
            log.warn("Registration attempt failed: Email {} is already registered.", userDto.getEmail());            result.rejectValue("email", "registration.error.email.exists", "Email already registered");
            return "register";
        } catch (UserNotFoundException e) {
            log.debug("Email {} is available for new registration.", userDto.getEmail());        }

        userService.registerNewUser(userDto);
        return "redirect:/login?success";
    }
}