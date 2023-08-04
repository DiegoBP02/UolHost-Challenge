package com.example.demo.services;

import com.example.demo.ApplicationConfigTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class LigaDaJusticaServiceTest extends ApplicationConfigTests {

    @Autowired
    private LigaDaJusticaService ligaDaJusticaService;

    private List<String> expectedCodinomes = Arrays.asList("Lanterna Verde", "Flash", "Aquaman", "Batman", "Superman","Mulher Maravilha");

    @Test
    void getCodinomes_givenSuccesful_shouldReturnValidCodinomeList(){
        List<String> codinomes = ligaDaJusticaService.getCodinomes();

        assertEquals(expectedCodinomes,codinomes);
        assertThat(codinomes).size().isEqualTo(expectedCodinomes.size());
    }

}