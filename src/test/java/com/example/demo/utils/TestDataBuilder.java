package com.example.demo.utils;

import com.example.demo.dtos.RegisterDto;
import com.example.demo.entities.User;
import com.example.demo.enums.HeroGroup;

import java.util.Arrays;
import java.util.List;

public class TestDataBuilder {

    public static User buildUser(){
        return User.builder()
                .email("email@email.com")
                .name("name")
                .phone("01234567890")
                .codinome("codinome")
                .heroGroup(HeroGroup.VINGADORES)
                .build();
    }

    public static RegisterDto buildRegisterDto(){
        return RegisterDto.builder()
                .name("name")
                .email("email@email.com")
                .phone("01234567890")
                .heroGroup(HeroGroup.VINGADORES)
                .build();
    }

    public static List<String> buildCodinomes() {
        return Arrays.asList("");
    }
}
