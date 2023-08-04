package com.example.demo.dtos;

import com.example.demo.enums.HeroGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;
    @NotBlank
    @Email
    private String email;
    @Size(min = 11, max = 11)
    private String phone;
    @Enumerated(EnumType.STRING)
    private HeroGroup heroGroup;
}
