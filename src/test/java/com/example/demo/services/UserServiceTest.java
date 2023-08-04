package com.example.demo.services;

import com.example.demo.ApplicationConfigTests;
import com.example.demo.entities.User;
import com.example.demo.repostiories.UserRepository;
import com.example.demo.utils.TestDataBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserServiceTest extends ApplicationConfigTests {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    User user = TestDataBuilder.buildUser();
    List<User> userList = Collections.singletonList(user);

    @Test
    void findAll_givenUserList_shouldReturnUserList() {
        when(userRepository.findAll()).thenReturn(userList);

        List<User> users = userService.findAll();

        assertEquals(userList, users);

        verify(userRepository, times(1)).findAll();
    }

}