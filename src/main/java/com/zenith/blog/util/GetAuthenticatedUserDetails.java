package com.zenith.blog.util;

import com.zenith.blog.exception.UnAuthorizedException;
import com.zenith.blog.model.User;
import com.zenith.blog.response.UserResponse;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetAuthenticatedUserDetails {
    private final ModelMapper modelMapper;

    public GetAuthenticatedUserDetails(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserResponse getDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) throw new UnAuthorizedException("User is not authenticated!");

        Object principal = authentication.getPrincipal();

        if(!(principal instanceof User)) throw new UnAuthorizedException("User is not authenticated!");

        return modelMapper.map((User) principal, UserResponse.class);
    }
}
