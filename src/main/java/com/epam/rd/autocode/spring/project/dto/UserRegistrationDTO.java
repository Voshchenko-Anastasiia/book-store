package com.epam.rd.autocode.spring.project.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegistrationDTO {

    private Long id;

    @NotBlank(message = "{registration.error.name.empty}")
    @Size(min = 2, max = 50, message = "{registration.error.name.size}")
    private String name;

    @NotBlank(message = "{registration.error.email.empty}")
    @Email(message = "{registration.error.email.invalid}")
    private String email;

    @NotBlank(message = "{registration.error.password.empty}")
    @Size(min = 8, message = "{registration.error.password.size}")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?=\\S+$).{8,}$",
            message = "{registration.error.password.pattern}")
    private String password;

    @NotBlank(message = "Please select an account type")
    private String role;
}
