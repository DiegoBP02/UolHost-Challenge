package com.example.demo.services;

import com.example.demo.entities.Vingadores;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class VingadoresService {

    public List<String> getCodinomes() {
        try {
            Vingadores vingadores = jsonParser();
            return vingadores.getVingadores().stream().map(hero -> hero.getCodinome()).toList();
        } catch (IOException e) {
            throw new RuntimeException("Error fetching Vingadores data: " + e.getMessage());
        }
    }

    private static Vingadores jsonParser() throws IOException {
        try {
            final String url = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/vingadores.json";
            ObjectMapper jsonMapper = new ObjectMapper();
            return jsonMapper.readValue(new URL(url), Vingadores.class);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error reading JSON data: " + e.getMessage());
        }
    }

}
