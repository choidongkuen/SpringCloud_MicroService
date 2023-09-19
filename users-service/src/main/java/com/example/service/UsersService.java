package com.example.service;

import com.example.domain.repository.UsersRepository;
import com.example.dto.UsersCreateRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Transactional
    public Long createUser(UsersCreateRequestDto request) {
        String encryptedPassword = this.bcryptPasswordEncoder.encode(request.getPassword());
        return this.usersRepository.save(request.toEntity(encryptedPassword)).getId();
    }
}
