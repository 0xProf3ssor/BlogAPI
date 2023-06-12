package com.zenith.blog.service;

import com.zenith.blog.request.LoginRequest;
import com.zenith.blog.request.UserRequest;
import com.zenith.blog.response.TokenResponse;
import com.zenith.blog.response.UserResponse;
import com.zenith.blog.util.APIResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService{
    //Get all users
    List<UserResponse> getAll(Integer pageNumber, Integer pageSize);

    //Get single user
    UserResponse getById(Long id);

    //Create a new user
    TokenResponse create(UserRequest userRequest);

    //Delete user by id
    APIResponse delete(Long id);

    //Login User
    TokenResponse login(LoginRequest loginRequest);
}
