package com.example.service;

import com.example.domain.entity.Users;
import com.example.domain.repository.UsersRepository;
import com.example.dto.CreateUsersRequestDto;
import com.example.dto.GetOrdersResponseDto;
import com.example.dto.GetUsersResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class UsersService implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final RestTemplate restTemplate;
    private final Environment environment;

    @Value("${orders-service.url}")
    private  String ordersServiceUrl;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = this.usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("일치하는 회원이 존재하지 않습니다."));
        return new User(users.getEmail(),
                users.getEncryptedPassword(),
                true, true, true, true,
                users.getAuthorities());
    }

    @Transactional
    public Long createUser(CreateUsersRequestDto request) {
        return this.usersRepository.save(request.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public List<GetUsersResponseDto> getAllUsers() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return this.usersRepository.findAll()
                .stream()
                .map(Users -> Users.toGetUsersResponseEntity(null))
                .collect(Collectors.toList());
    }

    @Transactional
    public GetUsersResponseDto getUserDetailsByEmail(String email) {

        // %s => userId, userId 가 주문한 모든 orders 내역 조회
        return this.usersRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"))
                .toGetUsersResponseEntity(null);
    }



    /** - GetUsersResponseDto
     * private String email;
     * private String name;
     * private String userId;
     * private List<GetOrdersResponseDto> orders;
    **/
    @Transactional(readOnly = true)
    public GetUsersResponseDto getUserByUserId(String userId) {
        // %s => userId, userId 가 주문한 모든 orders 내역 조회
        // Rest Template 을 이용한 방법
        ResponseEntity<List<GetOrdersResponseDto>> getOrdersResponseDto = this.getListResponseEntity(userId);

        return this.usersRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"))
                .toGetUsersResponseEntity(getOrdersResponseDto.getBody());
    }

    private ResponseEntity<List<GetOrdersResponseDto>> getListResponseEntity(String userId) {
        // %s => userId, userId 가 주문한 모든 orders 내역 조회
        return this.restTemplate.exchange(String.format(ordersServiceUrl,userId), HttpMethod.GET, null, new ParameterizedTypeReference<List<GetOrdersResponseDto>>() {
        });
    }
}
