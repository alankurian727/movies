package com.backbase.movie.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Seervice class to get user details.
 */
@Service
public class BackbaseUserDetailsService implements UserDetailsService {

    @Value("${password}")
    private String password;

    /**
     * Method to get UserDetails for authentication manager in Authentication controller
     * @param userName
     * @return UserDetails with userName and password
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        return new User(userName,password,new ArrayList<>());
    }
}
