package com.team3.camping.repository.userRepository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.team3.camping.domain.naverAndGoogle.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
    
}
