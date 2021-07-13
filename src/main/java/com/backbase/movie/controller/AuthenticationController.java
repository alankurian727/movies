package com.backbase.movie.controller;

import com.backbase.movie.exception.AuthenticationException;
import com.backbase.movie.models.AuthenticationRequest;
import com.backbase.movie.models.AuthenticationResponse;
import com.backbase.movie.service.impl.BackbaseUserDetailsService;
import com.backbase.movie.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for handling all authentication methods
 */
@RestController
public class AuthenticationController {

    /**
     * The Authentication manager.
     */
    AuthenticationManager authenticationManager;

    /**
     * The User details service.
     */
    BackbaseUserDetailsService userDetailsService;

    /**
     * The Jwtutil.
     */
    JWTUtil jwtutil;

    @Value("${userName}")
    private String userName;

    @Value("${password}")
    private String password;

    /**
     * Instantiates a new Authentication controller.
     *
     * @param authenticationManager the authentication manager
     * @param userDetailsService    the user details service
     * @param jwtutil               the jwtutil
     */
    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager, BackbaseUserDetailsService userDetailsService, JWTUtil jwtutil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtutil = jwtutil;
    }

    /**
     * Authentication controller which creates a jwt token with username.
     *
     * @param request the request AuthenticationRequest
     * @return the response entity AuthenticationResponse
     * @throws AuthenticationException the authentication exception
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request)
            throws AuthenticationException {
        System.out.println("inside AuthenticationController : authenticate");
        if(userName.equals(request.getUserName()) && password.equals(request.getPassword())) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUserName(), request.getPassword()
                ));
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("Invalid User Name or Password");
            }
            final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUserName());
            final String jwtToken = jwtutil.generateToken(userDetails);
            return new ResponseEntity<>(new AuthenticationResponse(jwtToken), HttpStatus.OK);
        }else{
            throw new AuthenticationException("Invalid Credentials");
        }
    }
}
