package com.backbase.movie.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class BackbaseUserDetailsServiceTest {

    @InjectMocks
    BackbaseUserDetailsService service;


    @Test
    public void testLoadByUserName() throws Exception{
        String userName = "backbase";
        ReflectionTestUtils.setField(service,"password","backbase123");
        final UserDetails userDetails =  service.loadUserByUsername(userName);
        assertEquals(userDetails.getUsername(),"backbase");
        assertEquals(userDetails.getPassword(),"backbase123");
    }
}