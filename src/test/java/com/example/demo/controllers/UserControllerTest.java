package com.example.demo.controllers;

import com.example.demo.ApplicationConfigTests;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.TestDataBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ApplicationConfigTests {

    private static final String PATH = "/users";

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    User user = TestDataBuilder.buildUser();
    List<User> userList = Collections.singletonList(user);

    @Test
    void findAll_givenUserList_shouldReturnUserList() throws Exception {
        when(userService.findAll()).thenReturn(userList);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders
                .get(PATH)
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userList)));

        verify(userService, times(1)).findAll();
    }


}