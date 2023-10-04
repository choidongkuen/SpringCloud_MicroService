package com.example.service;

import com.example.domain.entity.Users;
import com.example.domain.repository.UsersRepository;
import com.example.dto.CreateUsersRequestDto;
import com.example.dto.GetUsersResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bcryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원이 존재하지 않습니다."));
    }

    @Transactional
    public Long createUser(CreateUsersRequestDto request) {
        String encryptedPassword = this.bcryptPasswordEncoder.encode(request.getPassword());
        return this.usersRepository.save(request.toEntity(encryptedPassword)).getId();
    }

    @Transactional(readOnly = true)
    public List<GetUsersResponseDto> getAllUsers() {
        return this.usersRepository.findAll()
                .stream()
                .map(Users::toGetUsersResponseEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public GetUsersResponseDto getUser(String userId) {
        return this.usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"))
                .toGetUsersResponseEntity();
    }
}
