package com.backbase.movie.controller;

import com.backbase.movie.models.AuthenticationRequest;
import com.backbase.movie.service.impl.BackbaseUserDetailsService;
import com.backbase.movie.util.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    BackbaseUserDetailsService userDetailsService;

    @MockBean
    JWTUtil jwtutil;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @AfterEach
    public void before(){
        Mockito.reset(userDetailsService);
        Mockito.reset(authenticationManager);
        Mockito.reset(jwtutil);
    }

    @Test
    void testAuthenticate() throws Exception {
        String url = "/authenticate";
        User user = new User("backbase","backbase123",new ArrayList<>());
        AuthenticationRequest authRequest = new AuthenticationRequest("backbase","backbase123");
        Mockito.when(userDetailsService.loadUserByUsername(Mockito.anyString()))
                .thenReturn(user);
        Mockito.doReturn(null).when(authenticationManager).authenticate(Mockito.any());
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        Mockito.verify(userDetailsService,times(1)).loadUserByUsername(Mockito.anyString());
        Mockito.verify(authenticationManager,times(1)).authenticate(Mockito.any());

    }

    @Test
    void testInvalidUserName() throws Exception {
        String url = "/authenticate";
        User user = new User("backbase","backbase123",new ArrayList<>());
        AuthenticationRequest authRequest = new AuthenticationRequest("backba","backbase123");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void testInvalidPassword() throws Exception {
        String url = "/authenticate";
        User user = new User("backbase","backbase123",new ArrayList<>());
        AuthenticationRequest authRequest = new AuthenticationRequest("backbase","backbase");
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void testBadCredentialsException() throws Exception {
        String url = "/authenticate";
        User user = new User("backbase","backbase123",new ArrayList<>());
        AuthenticationRequest authRequest = new AuthenticationRequest("backbase","backbase123");
        Mockito.when(authenticationManager.authenticate(Mockito.any()))
                .thenThrow(new BadCredentialsException("Invalid Credentials"));
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .content(objectMapper.writeValueAsString(authRequest))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isUnauthorized())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }
}