package com.movie.app.service.impl;

import com.movie.app.entities.SecurityUser;
import com.movie.app.entities.User;
import com.movie.app.model.AuthenticationReq;
import com.movie.app.model.AuthenticationRes;
import com.movie.app.model.UserDto;
import com.movie.app.repository.UserRepository;
import com.movie.app.service.JwtService;
import com.movie.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserServiceImpl implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("Creating user {}", userDto);
        if(userDto.getId() != null) {
            throw  new RuntimeException("Id must be null!");
        }
        if(userRepository.findByUsername(userDto.getUsername()).isPresent()) {
            throw  new RuntimeException("Username already exists!");
        }

        User entity = toEntity(userDto);
        User save = userRepository.save(entity);

        return toDto(save);
    }

    @Override
    public AuthenticationRes authenticateUser(AuthenticationReq request) {
        log.debug("LOGIN REQUSET, {}", request.getUsername());
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

        UserDetails userDetails = new SecurityUser(user);
        String jwtToken =  jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        AuthenticationRes res = new AuthenticationRes();
        res.setAccessToken(jwtToken);
        res.setRefreshToken(refreshToken);
        return res;
    }

    private User toEntity(UserDto userDto) {
        return User.builder()
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .isAdmin(false).isActive(true).build();
    }

    private UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .isAdmin(false).isActive(true).build();
    }
}
