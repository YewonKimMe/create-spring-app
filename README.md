# 🌱 create-spring-app

### SpringBoot 기반 사이드 프로젝트용 시작 템플릿
> 사이드 프로젝트 시 스프링부트 기반으로 빠르게 개발 환경을 셋팅 할 수 있는 템플릿입니다.
---
## 📦 주요 기술 스택

| 영역         | 사용 기술                     |
|------------|---------------------------|
| Language   | Java 17 (Toolchain 적용)    |
| Build Tool | Gradle 8.x                |
| Framework  | Spring Boot 3.5.3         |
| ORM        | Spring Data JPA, QueryDSL |
| DB         | MySQL                     |
| Cache      | Redis                     |
| Security   | Spring Security           |
| Deployment / Infra | Docker, Docker Compose            |

---
## ✨feature

- **Spring Security**
  - **인증 (Authentication)**
    - **JWT / 세션 기반 인증**: `application-dev.yml`, `application-prod.yml` 파일의 `auth.use-session` boolean 설정을 통해 JWT 방식과 세션 방식 중 선택하여 사용할 수 있습니다.
    - **JSON 기반 로그인**: `JsonUsernamePasswordAuthenticationFilter`를 통해 Form Data가 아닌 JSON 형식의 Request Body로 로그인을 처리합니다.
      - 로그인 요청 엔드포인트: `/login`
      - 계정 등록 엔드포인트는 구현이 필요합니다. `SecurityConfig`에는 `/api/v1/sign-up`으로 설정되어 있습니다.
    - **Redis 토큰 관리**: JWT 사용 시, Refresh Token을 Redis에 저장하여 관리합니다.
    - **커스텀 핸들러**: 인증 성공/실패, 로그아웃 등 다양한 시나리오에 대한 커스텀 핸들러를 구현했습니다.
    - **User**: 기본적인 `User` 클래스 및 `Authority` 클래스가 정의되어 있습니다.
      - 인증 과정에서 `SecurityUserDetails`는 위 `User` 를 사용합니다.

- **데이터베이스 (Database)**
  - **QueryDSL 지원**: `QueryDslConfig`를 통해 QueryDSL을 프로젝트에 통합하여 타입-세이프(type-safe)한 동적 쿼리 작성이 가능합니다.
  - **Snowflake ID 생성**: Snowflake 알고리즘을 사용, 유니크하고 정렬 가능한 ID를 생성합니다. 엔티티의 ID에 `@SnowflakeGenerated` 어노테이션을 사용하여 적용할 수 있습니다.

- **유틸리티 (Utilities)**
  - **XSS 방지**: `XssSanitizer`를 통해 Cross-Site Scripting 공격을 방지합니다.
  - **AES 암호화**: `AESUtil`을 통해 데이터를 대칭키 방식으로 암호화하고 복호화할 수 있습니다.
  - **쿠키 관리**: `CookieUtils`를 통해 쿠키를 손쉽게 생성, 조회, 삭제할 수 있습니다.

- **개발 환경 (Development Environment)**
  - **프로필 기반 설정**: `dev`, `prod` 등 환경에 따라 설정을 분리하여 관리합니다. (`application-dev.yml`, `application-prod.yml`)
  - **Docker Compose 지원**: `compose-dev.yaml` 파일을 통해 `mysql`, `redis` 등 개발에 필요한 의존성들을 컨테이너로 한번에 실행할 수 있습니다.

---

## 🛠 템플릿 프로젝트 → 내 프로젝트로 완전히 리네이밍하기

이 가이드는 `create-spring-app` 템플릿을 기반으로
내 프로젝트를 `my-cool-project`로 이름, 패키지, 설정을 전부 바꾸는 방법을 안내합니다.

---

### ✅ 0단계. 준비

GitHub 템플릿에서 내 프로젝트 생성:

```bash
git clone https://github.com/YewonKimMe/create-spring-app.git my-cool-project
cd my-cool-project
```

또는 create-spring-app 리포지토리 우측 상단 'Use This template' -> create new repository 후,
자신의 repository 에서 git clone

---

### ✅ 1단계. 프로젝트 이름 변경

`settings.gradle` 열고, 아래 줄을 수정하세요:

```groovy
rootProject.name = 'my-cool-project'
```

---

### ✅ 2단계. 그룹명 변경

`build.gradle` 파일에서 이 부분을 수정하세요:

```groovy
group = 'com.myname'
```

원하는 그룹명 (도메인 기반 네이밍)을 넣으세요. 예: `dev.yewon`, `io.github.junho`

---

### ✅ 3단계. 패키지 경로 바꾸기

현재 패키지 경로는 다음과 같을 수 있습니다:

```
src/main/java/com/github/YewonKimMe/create_spring_app
```

이 경로를 다음과 같이 바꿔주세요:

```
src/main/java/com/myname/mycoolproject
```

> IntelliJ에서: 해당 폴더 우클릭 → `Refactor` → `Rename` & `Move` 순서대로 사용하면 안전하게 변경됩니다.

변경 후에는 `.java` 파일 상단의 `package` 선언도 이렇게 바뀌어야 합니다:

```java
// 변경 전
package com.github.YewonKimMe.create_spring_app;

// 변경 후
package com.myname.mycoolproject;
```

---

### ✅ 4단계. 테스트 코드 경로도 동일하게 변경

```
src/test/java/com/github/YewonKimMe/create_spring_app
→
src/test/java/com/myname/mycoolproject
```

테스트 클래스 내부의 `package` 선언도 꼭 함께 변경해주세요.

---

### ✅ 5단계. 애플리케이션 이름 변경

`src/main/resources/application.yml` 파일 열고, 아래 항목을 수정하세요:

```yaml
spring:
  application:
    name: my-cool-project
```

---

### ✅ 6단계. README 수정

`README.md` 파일을 열고, 다음 내용을 내 프로젝트에 맞게 바꾸세요:

* 프로젝트 이름 (`create-spring-app` → `my-cool-project`)
* 설명, 기술스택, 실행 방법 등 필요에 따라 업데이트

---

### ✅ 7단계. Git 원격 저장소 연결

새로운 GitHub 저장소를 만든 후, 기존 원격을 바꾸세요:

```bash
git remote set-url origin https://github.com/your-username/my-cool-project.git
```

최초 푸시:

```bash
git add .
git commit -m "chore: initialize my-cool-project from spring template"
git push -u origin main
```

---

## 🎉 이제 내 프로젝트로 완전히 리네이밍이 완료되었습니다. 바로 개발을 시작하세요! 🚀

---

# SpringSecurity 관련 DB 테이블 설정
src/main/resources/script/users-and-users-role-schema.sql 을 데이터베이스 프로젝트에서 실행

---
# 데이터베이스 설정
사용 환경에 따라 아래 단계를 따라하면 됩니다.

- <b>로컬 개발: 로컬 스프링부트 + MySQL 컨테이너 + Redis 컨테이너 를 기준으로 합니다. 로컬(Intellij)에서 처음 스프링부트 실행 시 2번으로 실행됩니다.<b>
- 배포: Docker Compose + 외부 RDB 서비스 or MySQL 컨테이너를 기준으로 합니다.

1. 로컬, 혹은 배포용 인스턴스에서 외부 RDB 서비스나 인스턴스(로컬) MySQL 등을 사용하는 경우(배포 기본)
   1. 환경변수 파일 `create-spring-app-example.env`의 `DB_URL=jdbc:mysql://localhost:3306/{DB_PROJECT_NAME}?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true` 로 로컬 MySQL 설정, 혹은 외부 RDB 서비스 엔드포인트 입력
   2. compose-dev.yaml(개발), compose.yaml(배포) 의 `mysql:` 이하를 전부 주석 처리
   3. `create-spring-app` 데이터베이스를 MySQL 내에 생성
   4. MySQL 접속 후 `src/main/resources/script/users-and-users-role-schema.sql` 을 복사 후 실행(시큐리티용 테이블 생성)

2. 로컬에서 스프링부트 실행, MySQL, Redis 는 도커 컨테이너로 사용하는 경우(로컬 기본)
   1. compose-dev.yaml(개발), compose.yaml(배포) 의 `mysql:` 이하를 전부 주석 해제 처리
   2. `create-spring-app.env` 의 `2. 로컬에서 스프링 실행, 컨테이너로 DB를 사용하는 경우` 아래의 `DB_URL` 을 주석 해제, 나머지 `DB_URL`은 전부 주석 처리
   3. 스프링부트 실행(Intellij 실행 버튼) 후 mysql workbench 등에서 MySQL 컨테이너 접속(`localhost:3307`, `.env` 의 `DB_USERNAME`, `DB_PASSWORD`를 입력 후 접속)
   4. MySQL 접속 후 `src/main/resources/script/users-and-users-role-schema.sql` 을 복사 후 실행(시큐리티용 테이블 생성)
3. 배포용 인스턴스에서 한 인스턴스 내에 Mysql 도커 컨테이너를 띄워서 사용하는 경우(로컬/외부서비스 사용 X)
- 이 경우 Springboot, MySQL, Redis 모두 컨테이너로 동작
   1. `.env` 파일의 `MYSQL_ROOT_PASSWORD`, `DB_USERNAME`, `DB_PASSWORD` 세팅
   2. `create-spring-app-example.env` 의 `DB_USERNAME`, `DB_PASSWORD` 을 `.env`와 동일하게 세팅
   3. `create-spring-app-example.env` 의 로컬용 `DB_URL` 을 주석 처리(17번 라인), 컨테이너용 `DB_URL` 주석 해제(24번 라인)
   4. `compose.yaml` 의 `depends_on: - mysql` 라인 주석 해제 
   5. `compose.yaml` 의 `# 컨테이너 DB 사용 시 아래 전부 주석 해제` 아래를 전부 주석 해제


---

# docker-compose 기반 배포

gradlew build, 컨테이너 실행 환경 구성

1. 인스턴스에 프로젝트 가져오기: `git clone your-project-name`
2. JAR 파일 만들기: 프로젝트 root 디렉토리에서, `./gradlew clean build
`
3. root 디렉토리에 `create-spring-app.env` 파일 생성 (또는 첨부된 create-spring-app-example.env 파일 제목 변경 후 내용 작성)
4. 프로젝트 루트 디렉토리에서 `docker compose up -d --build` 명령 실행 
    - (Dockerfile이 JAR 배포파일을 docker image 안으로 복사)

> docker-compose 기반 배포 시 root 디렉토리에 `create-spring-app.env` 파일 추가가 필요합니다.
> <br>create-spring-app-example.env 를 참고하세요.

---

## 🛠️ 기본 구성

```bash
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── github
│   │   │           └── YewonKimMe
│   │   │               └── ...                # 기본 패키지 구조
│   │   └── resources
│   │       ├── script
│   │       │   └── users-and-users-role-schema.sql   # 시큐리티용 users, users_role 스키마
│   │       ├── application.yml
│   │       ├── application-dev.yml
│   │       └── application-prod.yml
│   └── test
│       └── java
│           └── ...                            # 테스트 코드
├── Dockerfile
├── compose.yml                                # Docker Compose (운영용)
├── compose-dev.yml                            # Docker Compose (개발용)
├── .env                                       # docker compose 실행 환경변수
├── create-spring-app.env                      # 환경변수 파일 (직접 생성 or example 복사)
├── create-spring-app-example.env              # 예시 env 파일
├── build.gradle
├── LICENSE
└── README.md

