package com.zenith.blog.controller;

import com.zenith.blog.request.LoginRequest;
import com.zenith.blog.request.UserRequest;
import com.zenith.blog.response.TokenResponse;
import com.zenith.blog.response.UserResponse;
import com.zenith.blog.service.impl.UserServiceImpl;
import com.zenith.blog.util.APIResponse;
import com.zenith.blog.util.GetAuthenticatedUserDetails;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class UserController {
    private final GetAuthenticatedUserDetails getAuthenticatedUserDetails;
    private final UserServiceImpl userService;

    public UserController(GetAuthenticatedUserDetails getAuthenticatedUserDetails, UserServiceImpl userService) {
        this.getAuthenticatedUserDetails = getAuthenticatedUserDetails;
        this.userService = userService;
    }


    //Create a new user
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> createUser(@Valid @RequestBody UserRequest userRequest){
        return new ResponseEntity<>(userService.create(userRequest), HttpStatus.CREATED);
    }

    //Get all users
    @GetMapping("/users")
    public  ResponseEntity<List<UserResponse>> getAllUsers(@RequestParam(value = "page", defaultValue = "1", required = false) Integer page, @RequestParam(value = "size", defaultValue = "10", required = false) Integer size){

        return  ResponseEntity.ok(userService.getAll(page < 1 ? 0: page - 1, size < 1 ? 1 : size));
    }

    //Get single User by id
    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id){
        return ResponseEntity.ok(userService.getById(id));
    }

    //Delete user
    @DeleteMapping("/users/{id}")
    public  ResponseEntity<APIResponse> deleteById(@PathVariable("id") Long id){

        return ResponseEntity.ok(userService.delete(id));
    }
    //Login
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

}
