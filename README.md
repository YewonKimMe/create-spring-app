# 🌱 create-spring-app

> Spring Boot 기반 사이드 프로젝트를 빠르게 시작할 수 있도록 구성된 보일러플레이트 프로젝트입니다.
> 
> 웹, REST API, Redis, Security 등 다양한 기능이 기본으로 통합되어 있습니다.
> 
> 토큰 기반 인증으로 웹 클라이언트, 앱 클라이언트 상호 호환을 지원합니다.
> 
> 템플릿 리포지토리 기반으로 리포지토리를 생성 후, 다음 단계를 따라서 자신만의 리포지토리로 변경하세요.

---

## 🛠 템플릿 프로젝트 → 내 프로젝트로 완전히 리네이밍하기

이 가이드는 `create-spring-app` 템플릿을 기반으로
내 프로젝트를 `my-cool-project`로 이름, 패키지, 설정을 전부 바꾸는 방법을 안내합니다.

---
### my-cool-project

이 프로젝트는 템플릿 저장소 [create-spring-app](https://github.com/YewonKimMe/create-spring-app) 를 기반으로 생성된 Spring Boot 프로젝트입니다.  
아래 설정을 수정하여 프로젝트에 맞게 설정하세요.

---

## 프로젝트 설정 변경

### 1. settings.gradle

```groovy
rootProject.name = 'my-cool-project'
````

---

### 2. build.gradle 그룹명 변경

```groovy
group = 'your.group.name'
```
프로젝트에 맞는 적절한 그룹명을 설정해주세요.

---

### 3. 패키지 구조 변경

`src/main/java` 및 `src/test/java` 경로 내의 패키지를 다음과 같이 변경하세요.

* 변경 전: `com.github.YewonKimMe.create-spring-app`
* 변경 후: `com.github.your_name.my-cool-project`
  * 위 com.github.your_name.my-cool-project 는 자유롭게 변경하세요.

IntelliJ에서 디렉토리 우클릭 → Refactor → Rename 또는 Move 사용 시 안전하게 변경 가능

---

### 4. 테스트 코드 패키지명 변경

```
// 변경 전
package com.github.YewonKimMe.create_spring_app;

// 변경 후
package com.github.your_name.my_cool_project;

위 com.github.your_name.my_cool_project 는 메인 패키지명과 동일하게 변경하세요.
```

---

### 5. README.md 수정

프로젝트 목적에 맞게 문서를 수정하세요.

---

### 6. Git 원격 주소 변경 (선택)

```bash
git remote set-url origin git@github.com:<your-username>/my-cool-project.git
```

---

## 체크리스트

* [ ] settings.gradle의 프로젝트 이름 수정
* [ ] build.gradle의 group 수정
* [ ] 패키지 구조 및 테스트 코드 패키지 수정
* [ ] README.md 수정
* [ ] git remote 주소 확인

```
Hello World!
```


---

## 🎉 이제 내 프로젝트로 완전히 리네이밍이 완료되었습니다. 바로 개발을 시작하세요! 🚀

---
## 📦 주요 기술 스택

| 영역              | 사용 기술                             |
|-----------------|-----------------------------------|
| Language        | Java 17 (Toolchain 적용)            |
| Build Tool      | Gradle 8.x                        |
| Framework       | Spring Boot 3.5.3                 |
| ORM             | Spring Data JPA, QueryDSL         |
| DB              | MySQL(with DDL_security entities) |
| Cache           | Redis                             |
| Template Engine | Thymeleaf                         |
| API 문서          | SpringDoc OpenAPI (Swagger)       |
| 보안/인증           | Spring Security + JWT             |
| 이메일 발송          | Spring Mail                       |
| HTML 파싱         | Jsoup                             |
| 테스트             | JUnit 5, Spring Security Test     |
| 기타              | Slack API 연동, Firebase Admin SDK  |

---

## 🛠️ 기본 구성

```bash
.
├── src/main/java
│   └── com/github/YewonKimMe/...   # 기본 패키지 구조
├── src/main/resources
│   ├── application.yml
├──Dockerfile
├── docker-compose.yml              # (Docker Compose 사용 시)
├── build.gradle
└── README.md
