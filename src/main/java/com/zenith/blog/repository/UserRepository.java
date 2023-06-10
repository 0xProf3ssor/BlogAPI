package com.zenith.blog.repository;

import com.zenith.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //check email already exist or not
    boolean existsByEmail(String email);
}
