package com.example.demo.services;

import com.example.demo.entities.LigaDaJustica;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class LigaDaJusticaService {

    public List<String> getCodinomes() throws IOException {
        try {
            LigaDaJustica ligaDaJustica = xmlParser();
            return ligaDaJustica.getCodinomes();
        } catch (IOException e) {
            throw new RuntimeException("Error fetching Vingadores data: " + e.getMessage());
        }
    }

    public static LigaDaJustica xmlParser() throws IOException {
        try {
            String url = "https://raw.githubusercontent.com/uolhost/test-backEnd-Java/master/referencias/liga_da_justica.xml";
            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.readValue(new URL(url), LigaDaJustica.class);
        } catch (MalformedURLException e) {
            throw new MalformedURLException("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error reading XML data: " + e.getMessage());
        }
    }
}
