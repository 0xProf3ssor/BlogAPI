package com.zenith.blog.util;

import com.zenith.blog.exception.UnAuthorizedException;
import com.zenith.blog.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetAuthenticatedUserDetails {

    public User getDetails(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) throw new UnAuthorizedException("User is not authenticated!");

        Object principal = authentication.getPrincipal();

        if(!(principal instanceof User)) throw new UnAuthorizedException("User is not authenticated!");

        return (User) principal;
    }
}
