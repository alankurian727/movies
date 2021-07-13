package com.backbase.movie.filter;

import com.backbase.movie.service.impl.BackbaseUserDetailsService;
import com.backbase.movie.util.JWTUtil;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Jwt request filter class
 * This will check for jwt token for all rest calls and throw 403 forbidden if jwt token doesn' match.
 */
@Component
@Generated
public class JWTRequestFilter extends OncePerRequestFilter {

    /**
     * The User details service.
     */
    @Autowired
    BackbaseUserDetailsService userDetailsService;

    /**
     * The Jwt util.
     */
    @Autowired
    JWTUtil jwtUtil;

    /**
     * Method used for jwt token validation returns 403 forbidden if it doesn't match.
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String jwtToken = httpServletRequest.getHeader("Authorization");
        String userName =null;
        String jwt=null;
        if(jwtToken != null && jwtToken.startsWith("Bearer ")){
        jwt = jwtToken.substring(7);
        userName=jwtUtil.extractUsername(jwt);
        }
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
            if(jwtUtil.validateToken(jwt,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

    /**
     * Method make sure authentication end point is not validated for JWT token
     * @param request
     * @return
     * @throws ServletException
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return "/authenticate".equals(request.getRequestURI());
    }
}
