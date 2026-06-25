package com.epam.rd.autocode.spring.project.controller;

import com.epam.rd.autocode.spring.project.model.User;
import com.epam.rd.autocode.spring.project.security.JwtUtils;
import com.epam.rd.autocode.spring.project.service.OrderService;
import com.epam.rd.autocode.spring.project.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CabinetController.class)
class CabinetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // --- Controller Core Dependencies ---
    @MockBean
    private OrderService orderService;

    @MockBean
    private UserService userService;

    // --- Global Filter/Security Dependencies ---
    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private com.epam.rd.autocode.spring.project.repo.UserRepository userRepository;

    private User mockUser;
    private final String testEmail = "user@test.com"; // Matches @WithMockUser(username = "user") default

    @BeforeEach
    void setUp() {
        // Fixes the abstract class instantiation problem by using a Mockito mock instance
        mockUser = Mockito.mock(User.class);
        when(mockUser.getEmail()).thenReturn(testEmail);
        when(mockUser.getBalance()).thenReturn(BigDecimal.valueOf(100.00));

        // Wire globally required mockUser return structure for safety
        when(userService.getUserByEmail(any())).thenReturn(mockUser);
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void showDashboard_ShouldReturnDashboardViewAndUserAttribute() throws Exception {
        mockMvc.perform(get("/cabinet"))
                .andExpect(status().isOk())
                .andExpect(view().name("cabinet/dashboard"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attribute("user", mockUser));

        verify(userService).getUserByEmail(testEmail);
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void getMyOrders_ShouldReturnOrdersViewAndData() throws Exception {
        when(orderService.getOrdersByClient(testEmail)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/cabinet/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("cabinet/orders"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("orders"));

        verify(orderService).getOrdersByClient(testEmail);
    }

    @Test
    @WithMockUser(username = "user@test.com")
    void topUpBalance_WithValidAmount_ShouldRedirectSuccessfully() throws Exception {
        BigDecimal topUpAmount = new BigDecimal("50.00");

        mockMvc.perform(post("/cabinet/top-up")
                        .param("amount", topUpAmount.toString())
                        .with(csrf())) // Required to pass Spring Security's CSRF guard
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/cabinet?topupSuccess"));

        verify(userService).topUpBalance(eq(testEmail), eq(topUpAmount));
    }
}