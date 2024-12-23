package com.movie.app.service;

import com.movie.app.model.AuthenticationReq;
import com.movie.app.model.AuthenticationRes;
import com.movie.app.model.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
    AuthenticationRes authenticateUser(AuthenticationReq authenticationReq);
}
