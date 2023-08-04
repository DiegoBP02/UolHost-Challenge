package com.example.demo.controllers;

import com.example.demo.ApplicationConfigTests;
import com.example.demo.dtos.RegisterDto;
import com.example.demo.entities.User;
import com.example.demo.exceptions.NoMoreCodinomesAvailableException;
import com.example.demo.services.AuthenticationService;
import com.example.demo.utils.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthenticationControllerTest extends ApplicationConfigTests {

    private static final String PATH = "/auth";

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    RegisterDto registerDto = TestDataBuilder.buildRegisterDto();
    User user = TestDataBuilder.buildUser();

    private MockHttpServletRequestBuilder buildMockRequestPost
            (Object requestObject) throws Exception {
        return MockMvcRequestBuilders
                .post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestObject));
    }

    @Test
    void register_givenValidUser_shouldReturnUser() throws Exception {
        when(authenticationService.register(any(RegisterDto.class))).thenReturn(user);
        MockHttpServletRequestBuilder mockRequest = buildMockRequestPost(registerDto);

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));

        verify(authenticationService, times(1)).register(registerDto);
    }

    @Test
    void register_givenInvalidBody_shouldHandleMethodArgumentNotValidException() throws Exception {
        RegisterDto registerDTO = new RegisterDto();

        MockHttpServletRequestBuilder mockRequest = buildMockRequestPost(registerDTO);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof MethodArgumentNotValidException));

        verify(authenticationService, never()).register(registerDTO);
    }

    @Test
    void register_givenNoCodinomesAvailableException_shouldHandleNoMoreCodinomesAvailableException() throws Exception {
        when(authenticationService.register(registerDto)).thenThrow(NoMoreCodinomesAvailableException.class);

        MockHttpServletRequestBuilder mockRequest = buildMockRequestPost(registerDto);

        mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(result ->
                        assertTrue(result.getResolvedException()
                                instanceof NoMoreCodinomesAvailableException));

        verify(authenticationService, times(1)).register(registerDto);
    }

}