package com.movie.app.controller;

import com.movie.app.model.ApiResponse;
import com.movie.app.model.AuthenticationReq;
import com.movie.app.model.AuthenticationRes;
import com.movie.app.model.UserDto;
import com.movie.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/auth")
public class UserController {
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody UserDto userDto) throws URISyntaxException {
        log.debug("REGISTER USER: {}", userDto.getUsername());

        try {
            UserDto user = userService.createUser(userDto);
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.CREATED.getReasonPhrase(), "Successes register", HttpStatus.CREATED.value());
            ApiResponse<UserDto> response = new ApiResponse<>(meta, user);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse<?>> authenticate(
             @RequestBody AuthenticationReq request
    ) {
        log.debug("LOGIN REQUSET, {}", request.getUsername());

        try {
            AuthenticationRes authenticationRes = userService.authenticateUser(request);
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.OK.getReasonPhrase(), "Successes login", HttpStatus.OK.value());
            ApiResponse<AuthenticationRes> response = new ApiResponse<>(meta, authenticationRes);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage(), HttpStatus.BAD_REQUEST.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            ApiResponse.Meta meta = new ApiResponse.Meta(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
            ApiResponse<String> response = new ApiResponse<>(meta, null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
