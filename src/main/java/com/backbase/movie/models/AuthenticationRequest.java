package com.backbase.movie.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

/**
 * Authentication request Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class AuthenticationRequest {
    private String userName;
    private String password;
}
