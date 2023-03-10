# βοΈ Simple SNS Project

<br/>
<br/>

### π Introduce

----------------------
Simple SNSλ νμκ°μ, λ‘κ·ΈμΈ, κΈ μμ±, λκΈ, μ’μμ, λ§μ΄νΌλμ‘°ν κΈ°λ₯μ κ΅¬νν SNS APIμλλ€.

<br/>

### βοΈ Tool

------------------
<div>
    <img src="https://img.shields.io/badge/Java-007396?style=flat&logo=Java&logoColor=white" />
    <img src="https://img.shields.io/badge/spring-6DB33F?style=flat&logo=spring&logoColor=white" />
    <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat&logo=springboot&logoColor=white" />
    <img src="https://img.shields.io/badge/SpringSecurity-6DB33F?style=flat&logo=springsecurity&logoColor=white" />
    <img src="https://img.shields.io/badge/Docker-2496ED?style=flat&logo=Docker&logoColor=white" />
    <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=MySQL&logoColor=white" />
    <img src="https://img.shields.io/badge/AmazonAWS-232F3E?style=flat&logo=AmazonAWS&logoColor=white" />
    <img src="https://img.shields.io/badge/JUnit5-25A162?style=flat&logo=JUnit5&logoColor=white" />
    <img src="https://img.shields.io/badge/GitLab-FC6D26?style=flat&logo=GitLab&logoColor=white" />

</div>
<br/>

### ποΈ Swagger URL

---------

> http://ec2-54-180-91-171.ap-northeast-2.compute.amazonaws.com:8080/swagger-ui/index.html

<br/>

### π κΈ°μ  μ€ν

-------
| λΆλ₯            | μ’λ₯ / λ²μ               |
|:--------------|:---------------------|
| **μΈμ΄**        | **Java 11**          |
| **Framework** | **SpringBoot 2.7.5** |
| **Build**        | **Gradle 7.4**       |
| **DB**        | **MySQL 8.0**        |
| **CI/CD**     | **GitLab**           |
| **Server**        | **Amazon EC2**       |
| **IDE**        | **IntelliJ**           |

<br/>

### βοΈ ERD

--------------
![SNS_ERD](https://user-images.githubusercontent.com/99169063/211485788-ea2a16d4-4296-4ecc-b581-99eed881c674.png)

<br/>

### π End Point

------

|      METHOD       | Description               | URL |
|:------------|:-----------------|:-----------------------------------------|
| **Post**    | **νμκ°μ**         | **api/v1/users/join**                    |
| **Post**    | **λ‘κ·ΈμΈ**          | **api/v1/users/login**                   |
| **Post**    | **Post μμ±**      | **api/v1/posts**                         |
| **Get**     | **Post μ‘°ν**      | **api/v1/posts**                         |
| **Get**     | **Post 1κ° μ‘°ν**   | **api/v1/posts/{postId}**                |
| **Put**     | **Post μμ **      | **api/v1/posts/{id}**                    |
| **Delete**  | **Post μ­μ **      | **api/v1/posts/{postId}**                |
| **Post**    | **λκΈ μμ±**        | **api/v1/posts/{postsId}/comments**      |
| **Get**     | **λκΈ μ‘°ν**        | **api/v1/posts/{postsId}/comments**      |
| **Put**     | **λκΈ μμ **        | **api/v1/posts/{postsId}/comments/{id}** |
| **Delete**  | **λκΈ μ­μ **        | **api/v1/posts/{postsId}/comments/{id}** |
| **Post**    | **μ’μμ λλ₯΄κΈ°**      | **api/v1/posts/{postId}/likes**          |
| **Get**     | **μ’μμ μ‘°ν**       | **api/v1/posts/{postId}/likes**          |
| **Get**     | **λ§μ΄νΌλ μ‘°ν**      | **api/v1/posts/my**                      |

<br/>

### βοΈ Error Code

------
| **μλ¬ μ½λ** | μ€λͺ |
|:------------------------------|:-----------------------|
| **DUPLICATED_USER_NAME**      | **UserNameμ΄ μ€λ³΅λ©λλ€.**   |
| **USERNAME_NOT_FOUND**        | **ν΄λΉ UserNameμ΄ μμ΅λλ€.** |
| **INVALID_PASSWORD**          | **ν¨μ€μλκ° μλͺ»λμμ΅λλ€.**     |
| **POST_NOT_FOUND**            | **ν΄λΉ ν¬μ€νΈκ° μμ΅λλ€.**      |
| **INVALID_PERMISSION**        | **μ¬μ©μκ° κΆνμ΄ μμ΅λλ€.**     |
| **DATABASE_ERROR**            | **ν΄λΉ μ λ³΄μ λ¬Έμ κ° μκ²Όμ΅λλ€.**  |
| **INVALID_TOKEN**             | **μλͺ»λ ν ν°μλλ€.**         |
| **COMMENT_NOT_FOUND**         | **ν΄λΉ λκΈμ΄ μμ΅λλ€.**       |
| **ALREADY_LIKED**             | **μ΄λ―Έ likeλ₯Ό λλ μ΅λλ€.**    |

<br/>