package com.gyuzero.userservice.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gyuzero.userservice.dto.RequestLogin;
import com.gyuzero.userservice.dto.UserDto;
import com.gyuzero.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final UserService userService;

    private final JwtService jwtService;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            RequestLogin requestLogin = new ObjectMapper().readValue(request.getInputStream(), RequestLogin.class);

            UsernamePasswordAuthenticationToken authRequest = UsernamePasswordAuthenticationToken.authenticated(
                    requestLogin.getUserId(),
                    requestLogin.getPassword(),
                    new ArrayList<>());

            return getAuthenticationManager().authenticate(authRequest);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        String username = ((User) authResult.getPrincipal()).getUsername();

        UserDto userDto = userService.findByUserId(username);

        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("userId", userDto.getUserId());
        extraClaims.put("email", userDto.getEmail());
        extraClaims.put("name", userDto.getName());

        String jwt = jwtService.generateToken(extraClaims, username);

        response.addHeader("jwt", jwt);

    }
}
