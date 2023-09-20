## [인프런] Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)

![image](https://github.com/choidongkuen/SpringCloud_MicroService/assets/96874318/ad46460f-d170-4945-8477-60925e5048ff)


<hr>

### 1. Users Service


|기능| URI(API Gateway 적용)              |URI(API Gateway 미적용) | Http Method |
|---|----------------------------------|---|---|
| 사용자 정보 등록 | /users-service/users              | /users | **POST** |
| 전체 사용자 조회 | /users-service/users              | /users | **GET** |
| 사용자 정보, 주문 내역 조회 | /users-service/users/{user-id}    | /users/{user_id} | **GET** |
| 작동 상태 확인 | /users-service/users/health_check | /users/health_check | **GET** |
| 환영 메세지 | /users-service/users/welcome | /users/welcome | **GET** |

<details>
<summary>Users Service API 명세서</summary>
<div markdown="1">
<br>
  
[1. 사용자 정보 등록]
<br>

요청
```json
POST /users-service/users
{
  "email": "danaver12@daum.net",
  "name": "최동근",
  "password": "ehdrms121212"
}
```

응답
```json
1
```

<hr>

[2. 사용자 정보 조회]
<br>

요청
```json
GET /users-service/users
```

응답
```json
{
  "email": "danaver12@daum.net",
  "name" : "최동근",
  "userId" : "sdewsdcx"
}
```


</div>
</details>
