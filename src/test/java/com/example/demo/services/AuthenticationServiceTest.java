package com.example.demo.services;

import com.example.demo.ApplicationConfigTests;
import com.example.demo.dtos.RegisterDto;
import com.example.demo.entities.User;
import com.example.demo.enums.HeroGroup;
import com.example.demo.exceptions.NoMoreCodinomesAvailableException;
import com.example.demo.repostiories.UserRepository;
import com.example.demo.utils.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


class AuthenticationServiceTest extends ApplicationConfigTests {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private LigaDaJusticaService ligaDaJusticaService;

    @MockBean
    private VingadoresService vingadoresService;

    RegisterDto registerDto = TestDataBuilder.buildRegisterDto();
    User user = TestDataBuilder.buildUser();
    List<String> codinomes = TestDataBuilder.buildCodinomes();

    @BeforeEach
    void setup() {
        authenticationService.getCodinomeBlacklist().clear();
    }

    @Test
    void register_givenValidDataLigaDaJustica_thenReturnUserCreated() {
        registerDto.setHeroGroup(HeroGroup.LIGA_DA_JUSTICA);
        when(ligaDaJusticaService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.register(registerDto);

        assertEquals(user, result);

        verify(vingadoresService, never()).getCodinomes();
        verify(ligaDaJusticaService, times(1)).getCodinomes();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_givenValidDataLigaDaJustica_thenUserCreatedShouldHaveCodinomeA() {
        registerDto.setHeroGroup(HeroGroup.LIGA_DA_JUSTICA);
        when(ligaDaJusticaService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.register(registerDto);

        user.setCodinome(codinomes.get(0));
        user.setHeroGroup(HeroGroup.LIGA_DA_JUSTICA);
        assertEquals(user, result);
        assertThat(codinomes).size().isEqualTo(1);

        verify(vingadoresService, never()).getCodinomes();
        verify(ligaDaJusticaService, times(1)).getCodinomes();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void register_givenValidDataVingadores_thenReturnUserCreated() {
        registerDto.setHeroGroup(HeroGroup.VINGADORES);
        when(vingadoresService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.register(registerDto);

        assertEquals(user, result);

        verify(vingadoresService, times(1)).getCodinomes();
        verify(ligaDaJusticaService, never()).getCodinomes();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_givenValidDataVingadores_thenUserCreatedShouldHaveCodinomeA() {
        registerDto.setHeroGroup(HeroGroup.VINGADORES);
        when(vingadoresService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = authenticationService.register(registerDto);

        user.setCodinome(codinomes.get(0));
        assertEquals(user, result);
        assertThat(codinomes).size().isEqualTo(1);

        verify(vingadoresService, times(1)).getCodinomes();
        verify(ligaDaJusticaService, never()).getCodinomes();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void register_givenNoMoreCodinomesAvailableForVingadores_thenThrowNoMoreCodinomesAvailableException() {
        registerDto.setHeroGroup(HeroGroup.VINGADORES);
        when(vingadoresService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        authenticationService.register(registerDto);
        assertThrows(NoMoreCodinomesAvailableException.class, () ->
                authenticationService.register(registerDto));

        verify(vingadoresService, times(2)).getCodinomes();
        verify(ligaDaJusticaService, never()).getCodinomes();
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void register_givenNoMoreCodinomesAvailableForLigaDaJustica_thenThrowNoMoreCodinomesAvailableException() {
        registerDto.setHeroGroup(HeroGroup.LIGA_DA_JUSTICA);
        when(ligaDaJusticaService.getCodinomes()).thenReturn(codinomes);
        when(userRepository.save(any(User.class))).thenReturn(user);

        authenticationService.register(registerDto);
        assertThrows(NoMoreCodinomesAvailableException.class, () ->
                authenticationService.register(registerDto));

        verify(vingadoresService, never()).getCodinomes();
        verify(ligaDaJusticaService, times(2)).getCodinomes();
        verify(userRepository, times(1)).save(any(User.class));
    }

}