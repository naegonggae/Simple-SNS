# ✉️ Simple SNS Project
<br/>
<br/>

### 💁 Introduce

----------------------
Simple SNS는 회원가입, 로그인, 글 작성, 댓글, 좋아요, 마이피드조회 기능을 구현한 SNS API입니다.

<br/>

### ⚒️ Tool

------------------
JavaSpringBoot, SpringSecurity, MySQL, Docker, AmazonEC2

<br/>

### 🖇️ Swagger 배포 주소

---------

> http://ec2-3-37-36-91.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

<br/>

### 📋 기술 스택

-------
| 언어 | Java 11 |
|:-------------------:|:------------------------:|
|   **SpringBoot**    |        **2.7.5**         |
|       **DB**        |       **MySQL 8.0.31**       |
|       **빌드**        |        **Gradle 7.4**        |
|       **서버**        |        **Amazon EC2**        |

<br/>

### 📝 기술명세서

------

|              | 기능 | 주소 |
|:------------:|:-------------------:|:-----------------------------------------|
|   **Post**   |      **회원가입**       | **api/v1/users/join**                    |
|   **Post**   |       **로그인**       | **api/v1/users/login**                   |
|   **Post**   |     **Post 작성**     | **api/v1/posts**                         |
|   **Get**    |     **Post 조회**     | **api/v1/posts**                         |
|   **Get**    |   **Post 1개 조회**    | **api/v1/posts/{postId}**                |
|   **Put**    |     **Post 수정**     | **api/v1/posts/{id}**                    |
|  **Delete**  |     **Post 삭제**     | **api/v1/posts/{postId}**                |
|   **Post**   |      **댓글 작성**      | **api/v1/posts/{postsId}/comments**      |
|   **Get**    |      **댓글 조회**      | **api/v1/posts/{postsId}/comments**      |
|   **Put**    |      **댓글 수정**      | **api/v1/posts/{postsId}/comments/{id}** |
|  **Delete**  |      **댓글 삭제**      | **api/v1/posts/{postsId}/comments/{id}** |
|   **Post**   |     **좋아요 누르기**     | **api/v1/posts/{postId}/likes**          |
|   **Get**    |     **좋아요 조회**      | **api/v1/posts/{postId}/likes**          |
|   **Get**    |     **마이피드 조회**     | **api/v1/posts/my**                      |

<br/>

### ❗️ Error Code

------
| **에러 코드** | 설명 |
|:------------------------------|:-----------------------|
| **DUPLICATED_USER_NAME**      | **UserName이 중복됩니다.**   |
| **USERNAME_NOT_FOUND**        | **해당 UserName이 없습니다.** |
| **INVALID_PASSWORD**          | **패스워드가 잘못되었습니다.**     |
| **POST_NOT_FOUND**            | **해당 포스트가 없습니다.**      |
| **INVALID_PERMISSION**        | **사용자가 권한이 없습니다.**     |
| **DATABASE_ERROR**            | **해당 정보에 문제가 생겼습니다.**  |
| **INVALID_TOKEN**             | **잘못된 토큰입니다.**         |
| **COMMENT_NOT_FOUND**         | **해당 댓글이 없습니다.**       |
| **ALREADY_LIKED**             | **이미 like를 눌렀습니다.**    |

<br/>

### ⚙️ ERD

--------------
![SNS_ERD](https://user-images.githubusercontent.com/99169063/211485788-ea2a16d4-4296-4ecc-b581-99eed881c674.png)

 