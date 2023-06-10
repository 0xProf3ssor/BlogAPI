package com.zenith.blog.service;

import com.zenith.blog.request.UserRequest;
import com.zenith.blog.response.UserResponse;

import java.util.List;

public interface UserService {
    //Get all users
    List<UserResponse> getAll(Integer pageNumber, Integer pageSize);

    //Get single user
    UserResponse getById(Long id);

    //Create a new user
    UserResponse create(UserRequest userRequest);

    //Delete user by id
    void delete(Long id);
}
