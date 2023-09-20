package com.example.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


// @JsonInclude 을 사용함으로서, null 인 필드 null로 노출 x
// 회원 조회와 주문 목록 조회 응답 dto 클래스로 동시 사용 O
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUsersResponseDto {
    private String email;
    private String name;
    private String userId;
    private List<GetOrdersResponseDto> orders;
}
