## [인프런] Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA)

![image](https://github.com/choidongkuen/SpringCloud_MicroService/assets/96874318/ad46460f-d170-4945-8477-60925e5048ff)


#### 1. 학습 목표 : Spring framework의 Spring Cloud 제품군을 이용하여 마이크로서비스 애플리케이션을 개발해 보는 과정입니다. Cloud Native Application으로써의 Spring Cloud를 어떻게 사용하는지, 구성을 어떻게 하는지에 대해 배울 수 있는 강의입니다.

#### 2. 사용 기술 : Spring Boot 2.7.15, Neflix Zuul, Neflix Ribbon, Neflix Eureka, Spring Cloud API Gateway, Spring Data JPA, Spring Cloud Config, Spring Cloud Bus, H2 DB, RabbitMQ
<hr>

### 1. Users Service


|기능| URI(API Gateway 적용)              |URI(API Gateway 미적용) | Http Method |
|---|----------------------------------|---|---|
| 사용자 정보 등록 | /users-service/users              | /users | **POST** |
| 전체 사용자 조회 | /users-service/users              | /users | **GET** |
| 사용자 정보, 주문 내역 조회 | /users-service/users/{userId}    | /users/{userId} | **GET** |
| 작동 상태 확인 | /users-service/users/health_check | /users/health_check | **GET** |
| 환영 메세지 | /users-service/users/welcome | /users/welcome | **GET** |
| 회원 로그인 | /users-service/users/login | /users/login | **POST** |


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
// 201 Created
1
```

<hr>

[2. 사용저 전체 정보 조회]
<br>

요청
```json
GET /users-service/users
```

응답
```json
// 200 Ok
[
    {
        "email": "danaver12@daum.net",
        "name": "choidongkuen",
        "userId": "882ffb70-cea8-479c-b3b6-92b347d983dd"
    },
    {
        "email": "hello12@daum.net",
        "name": "박건구",
        "userId": "a42a3d69-7495-4129-9e31-9b4e3b3578cf"
    }
]
```

<hr>

[3. 특정 사용자 정보 조회]
<br>

요청
```json
GET /users-service/users/{userId}
```

응답
```json
// 200 Ok
{
  "email": "danaver12@daum.net",
  "name" : "최동근",
  "userId" : "sdewsdcx"
}
```
<hr>

[4. 애플리케이션 상태 정보 확인]
<br>

요청
```json
GET /users-service/users/health-check
```

응답
```json
// 200 OK
It's Working in User Service on PORT 57702
```

</div>
</details>

<hr>

### 2. Catalogs Service

|기능| URI(API Gateway 적용)              |URI(API Gateway 미적용) | Http Method |
|---|----------------------------------|---|---|
| 카탈로그 조회 | /catalogs-service/catalogs              | /catalogs | **GET** |
| 카탈로그 삭제 | /catalogs-service/catalogs/id              | /catalogs/id | **DELETE** |
| 카탈로그 등록 | /catalogs-service/catalogs    | /catalogs | **POST** |
| 작동 상태 확인 | /catalogs-service/catalogs/health_check | /catalogs/health_check | **GET** |

<details>
<summary>Catalogs Service API 명세서</summary>
<div markdown="1">
<br>

[1. 카탈로그 등록]
<br>

요청
```json
POST /catalogs-service/catalogs
{
    "productId": "CATALOGS-02",
    "productName": "제품2",
    "stock": 12,
    "unitPrice": 1002
}
```

응답
```json
// 201 Created
1
```

<br>

[2. 카탈로그 전체 조회]
<br>

요청
```json
GET /catalogs-service/catalogs
```

응답
```json
// 200 Ok
[
    {
        "id": 2,
        "productId": "CATALOGS-01",
        "productName": "제품",
        "stock": 12,
        "unitPrice": 100
    },
    {
        "id": 3,
        "productId": "CATALOGS-02",
        "productName": "제품2",
        "stock": 12,
        "unitPrice": 1002
    }
]
```

<br>

[3. 카탈로그 삭제]
<br>

요청
```json
DELETE /catalogs-service/catalogs/1
```

응답
```json
// 200 Ok
```


</div>
</details>

<hr>

### 3. Orders Service

|기능| URI(API Gateway 적용)              |URI(API Gateway 미적용) | Http Method |
|---|----------------------------------|---|---|
| 사용자별 주문 등록 | /orders-service/{user_id}/orders              | /{user_id}/orders | **POST** |
| 사용자별 주문 내역 조회 | /orders-service/{user_id}/orders              | /{user_id}/orders | **GET** |
| 작동 상태 확인 | /orders-service/health_check | /orders-service/health_check | **GET** |



<details>
<summary>Orders Service API 명세서</summary>
<div markdown="1">
<br>

[1. 사용자별 주문 등록]
<br>

요청
```json
// POST /orders-service/{userId}/orders
{
  "productId": "CATALOGS-01",
  "productName": "제품1",
  "stock": 20,
  "unitPrice": 1000
}
```

응답
```json
// 201 Created
1
```

<br> 



[2. 사용자 대한 모든 주문 조회]
<br>

요청
```json
// GET /orders-service/{userId}/orders
```
<br>

응답
```json
// 200 Ok
[
    {
        "productId": "CATALOGS-02",
        "productName": "제품2",
        "stock": 50,
        "unitPrice": 1000,
        "totalPrice": 50000,
        "orderId": "d5da68f7-f805-4c36-aa73-1da3b2650c61",
        "orderedAt": "2023-09-25T09:58:17.476955"
    },
    {
        "productId": "CATALOGS-01",
        "productName": "제품1",
        "stock": 50,
        "unitPrice": 1000,
        "totalPrice": 50000,
        "orderId": "a63cb1e8-edd0-415b-8836-8f8210173ece",
        "orderedAt": "2023-09-25T10:05:45.941925"
    }
]
```

<br>

[3. 주문 아이디에 해당하는 주문 조회]
<br>

요청
```json
GET /orders-service/{orderId}/order
```

<br>

응답
```json
// 200 Ok
{
    "productId": "CATALOGS-01",
    "productName": "제품1",
    "stock": 50,
    "unitPrice": 1000,
    "totalPrice": 50000,
    "orderId": "0960b7c8-1411-4071-b2e1-d2fb2af7e773",
    "orderedAt": "2023-09-25T10:13:10.429338"
}

```
