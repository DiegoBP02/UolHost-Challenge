package com.example.demo.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Vingadores {
    private List<Hero> vingadores;

    @Getter
    @Setter
    public static class Hero {
        private String codinome;
    }
}
