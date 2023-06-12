package com.zenith.blog.repository;

import com.zenith.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //check email already exist or not
    boolean existsByEmail(String email);
    //Get by email
    Optional<User> findByEmail(String email);
}
