package com.zenith.blog.service.impl;

import com.zenith.blog.exception.ResourceNotFoundException;
import com.zenith.blog.model.User;
import com.zenith.blog.repository.UserRepository;
import com.zenith.blog.request.UserRequest;
import com.zenith.blog.response.UserResponse;
import com.zenith.blog.service.UserService;
import com.zenith.blog.util.PasswordEncoder;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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
    public UserResponse create(UserRequest userRequest) {
        userRequest.setPassword(passwordEncoder.apply(userRequest.getPassword()));
        User user = modelMapper.map(userRequest, User.class);

        return modelMapper.map(userRepository.save(user), UserResponse.class);
    }

    @Override
    public void delete(Long id) {
        userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id.toString()));
        userRepository.deleteById(id);
    }
}
