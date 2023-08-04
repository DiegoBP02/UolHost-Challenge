package com.example.demo.services;

import com.example.demo.dtos.RegisterDto;
import com.example.demo.entities.User;
import com.example.demo.enums.HeroGroup;
import com.example.demo.exceptions.NoMoreCodinomesAvailableException;
import com.example.demo.repostiories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LigaDaJusticaService ligaDaJusticaService;

    @Autowired
    private VingadoresService vingadoresService;

    private Set<String> codinomeBlacklist = new HashSet<>();

    Set<String> getCodinomeBlacklist() {
        return codinomeBlacklist;
    }

    public User register(RegisterDto registerDto) {
        HeroGroup heroGroup = registerDto.getHeroGroup();
        String randomCodinome = getRandomCodinome(heroGroup);

        if (randomCodinome == null) {
            throw new NoMoreCodinomesAvailableException();
        }

        User user = User.builder()
                .email(registerDto.getEmail())
                .name(registerDto.getName())
                .phone(registerDto.getPhone())
                .codinome(randomCodinome)
                .heroGroup(heroGroup)
                .build();

        return userRepository.save(user);
    }

    private String getRandomCodinome(HeroGroup heroGroup) {
        List<String> codinomes = new ArrayList<>();
        switch (heroGroup) {
            case LIGA_DA_JUSTICA:
                codinomes = ligaDaJusticaService.getCodinomes();
                break;
            case VINGADORES:
                codinomes = vingadoresService.getCodinomes();
                break;
        }

        int maxAttemps = codinomes.size();
        int attemps = 0;

        while (attemps < maxAttemps) {
            int randomIndex = new Random().nextInt(codinomes.size());
            String randomCodinome = codinomes.get(randomIndex);

            if (!codinomeBlacklist.contains(randomCodinome)) {
                codinomeBlacklist.add(randomCodinome);
                return randomCodinome;
            }

            attemps++;
        }

        return null;
    }
}
