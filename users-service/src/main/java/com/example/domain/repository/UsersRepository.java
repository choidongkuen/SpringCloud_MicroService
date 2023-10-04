package com.example.domain.repository;

import com.example.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUserId(String userId);

    Optional<Users> findByEmail(String email);
}
