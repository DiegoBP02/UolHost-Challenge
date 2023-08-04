package com.example.demo.services;

import com.example.demo.ApplicationConfigTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class VingadoresServiceTest extends ApplicationConfigTests {

    @Autowired
    private VingadoresService vingadoresService;

    private List<String> expectedCodinomes = Arrays.asList("Hulk", "Capitão América", "Pantera Negra", "Homem de Ferro", "Thor", "Feiticeira Escarlate", "Visão");

    @Test
    void getCodinomes_givenSuccesful_shouldReturnValidCodinomeList() {
        List<String> codinomes = vingadoresService.getCodinomes();

        assertEquals(expectedCodinomes, codinomes);
        assertThat(codinomes).size().isEqualTo(expectedCodinomes.size());
    }

}