package com.zenith.blog.service.impl;

import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.Role;
import com.zenith.blog.model.User;
import com.zenith.blog.repository.UserRepository;
import com.zenith.blog.request.LoginRequest;
import com.zenith.blog.request.UserRequest;
import com.zenith.blog.response.TokenResponse;
import com.zenith.blog.response.UserResponse;
import com.zenith.blog.service.UserService;
import com.zenith.blog.util.APIResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, JwtServiceImpl jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public List<UserResponse> getAll(Integer pageNumber, Integer pageSize) {
        Page<User> userPage = userRepository.findAll(PageRequest.of(pageNumber, pageSize));
        return userPage
                .getContent()
                .stream()
                .map(user -> modelMapper.map(user, UserResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public TokenResponse create(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        User user = modelMapper.map(userRequest, User.class);
        user.setRole(Role.ADMIN);
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new TokenResponse(jwtToken);
    }

    @Override
    public APIResponse delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        userRepository.deleteById(id);
        return new APIResponse(true, "Deleted", "User deleted successfully!");
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );
        User user = userRepository.findByEmail(loginRequest.email()).orElseThrow();
        return new TokenResponse(jwtService.generateToken(user));
    }

}
