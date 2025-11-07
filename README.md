# 🌱 create-spring-app

### 🚀 Spring Boot 사이드 프로젝트 퀵스타트 템플릿

> 스프링 부트 기반 사이드 프로젝트를 위한 보일러플레이트입니다. 인증, 보안, DB, Docker 환경이 미리 구성되어 있어 비즈니스 로직 개발에 바로 집중할 수 있습니다.

-----

## 📦 Tech Stack

| Category | Technology | Description |
| :--- | :--- | :--- |
| **Language** | Java 17 | Toolchain 적용 |
| **Framework** | Spring Boot 3.5.3 | |
| **Build Tool** | Gradle 8.x | |
| **Security** | Spring Security | JWT / Session 하이브리드 지원 |
| **Database** | MySQL | |
| **ORM** | Spring Data JPA, QueryDSL | 타입 세이프한 동적 쿼리 지원 |
| **Cache** | Redis | Refresh Token 및 캐싱 데이터 관리 |
| **Infra** | Docker, Docker Compose | 개발 및 배포 환경 컨테이너화 |

-----

## 🛠 퀵스타트: 내 프로젝트로 만들기

### 1\. 프로젝트 가져오기

GitHub 리포지토리 우측 상단의 **[Use this template]** 버튼을 클릭하거나, 아래 명령어로 클론합니다.

```bash
git clone https://github.com/YewonKimMe/create-spring-app.git my-cool-project
cd my-cool-project
```

### 2\. 프로젝트 리네이밍 (필수)

| 단계 | 수정 파일/경로 | 내용 |
| :-- | :--- | :--- |
| **1. 프로젝트명** | `settings.gradle` | `rootProject.name = 'my-cool-project'` |
| **2. 그룹명** | `build.gradle` | `group = 'com.myname'` |
| **3. 패키지 경로** | `src/main/java/...` | `com.github.YewonKimMe...` → `com.myname.mycoolproject`<br>*(IDE Refactor 기능 권장)* |
| **4. 테스트 경로** | `src/test/java/...` | 메인 패키지와 동일하게 변경 |
| **5. 앱 이름** | `src/main/resources/application.yml` | `spring.application.name: my-cool-project` |

### 3\. 환경변수 파일 생성

```bash
cp create-spring-app-example.env create-spring-app.env
```

### 4\. 실행 (기본 로컬 모드)

IntelliJ에서 **실행(Run)** 버튼을 누르면 Spring Boot가 시작되며 필요한 DB 컨테이너(MySQL, Redis)도 자동으로 실행됩니다.

> 💡 최초 실행 후, `localhost:3307` DB에 접속하여 `src/main/resources/script/users-and-users-role-schema.sql` 스크립트를 반드시 실행해주세요.

-----

## 🗄️ 상세 데이터베이스 설정 가이드

사용 환경에 따라 아래 3가지 시나리오 중 하나를 선택하여 설정하세요.

### 시나리오 1: 외부 MySQL 사용 (로컬 설치형 또는 클라우드 DB)

> Docker 컨테이너 대신 이미 설치된 로컬 MySQL이나 AWS RDS 같은 외부 서비스를 사용할 때 선택합니다.

1.  `create-spring-app.env` 파일에서 `DB_URL`을 로컬 MySQL 데이터베이스 or 외부 RDB 서비스 엔드포인트로 변경합니다. `{DB_PROJECT_NAME}`을 자신의 데이터베이스 프로젝트명으로 변경하세요.
    ```properties
    DB_URL=jdbc:mysql://localhost:3306/{DB_PROJECT_NAME}?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true
    ```
2.  `compose-dev.yaml`(개발용) 및 `compose.yaml`(배포용)에서 `mysql:` 서비스 부분을 모두 **주석 처리**합니다.
3.  외부 MySQL에 접속하여 데이터베이스를 생성하고, 초기화 스크립트(`users-and-users-role-schema.sql`)를 실행합니다.

### 시나리오 2: 로컬 Spring Boot + 컨테이너 DB (로컬 실행 기본값)

> **가장 간편한 개발 방식으로, IDE 실행 시 컨테이너가 자동으로 뜹니다. (퀵스타트 기본 방식)**

1.  `compose-dev.yaml`에서 `mysql:` 서비스가 **주석 해제**되어 있는지 확인합니다. (기본값)
2.  `create-spring-app.env`에서 **'2. 로컬에서 스프링 실행...'** 주석 아래의 `DB_URL`을 사용합니다. (기본값)
    ```properties
    # 로컬 컨테이너 접속용 URL (포트 3307)
    DB_URL=jdbc:mysql://localhost:3307/create-spring-app?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&rewriteBatchedStatements=true
    ```
3.  앱 실행 후, `localhost:3307`에 접속(정보는 `.env` 참조)하여 초기화 스크립트를 실행합니다.

### 시나리오 3: 전체 컨테이너 배포 (배포 환경 기본값)

> EC2 등 배포 서버에서 Spring Boot를 포함한 모든 스택을 Docker Compose로 실행할 때 사용합니다.

1.  `.env` 파일 및 `create-spring-app.env` 파일의 DB 계정 정보(`DB_USERNAME`, `DB_PASSWORD` 등)를 동일하게 맞춥니다.
2.  `create-spring-app.env`에서 **컨테이너용 DB\_URL** 주석을 해제하고, 로컬용 URL은 주석 처리합니다.
    ```properties
    # 컨테이너 간 통신용 URL (서비스명 'mysql' 사용)
    DB_URL=jdbc:mysql://mysql:3307/create_spring_app...
    ```
3.  `compose.yaml` 파일에서 다음 항목들의 **주석을 해제**합니다:
    - `depends_on: - mysql` 라인
    - `# 컨테이너 DB 사용 시 아래 전부 주석 해제` 아래의 모든 `mysql` 관련 설정

-----

## 🐳 배포 (Deployment)

위 '시나리오 3' 설정을 마친 후, 운영 서버에서 다음 명령어로 전체 서비스를 실행합니다.

```bash
# 1. 프로젝트 빌드
./gradlew clean build

# 2. 전체 스택 실행
docker-compose -f compose.yaml up -d --build
```

-----

## ✨ Key Features

- **🔐 Spring Security**: JWT/Session 하이브리드 인증, JSON 로그인 처리
- **💾 Data**: QueryDSL, Snowflake ID 지원
- **🛠 Utils**: XSS 방지, AES 암호화, 편리한 쿠키 관리
---

### 🔓 인증 예외 URL (Permitted URLs)
다음 엔드포인트들은 인증 없이 접근 가능하도록 기본 설정되어 있습니다.

| Method | URI | 설명                 |
| :--- | :--- |:-------------------|
| `POST` | **`/api/v1/login`** | 로그인 요청 (JSON Body) |
| `POST` | **`/api/v1/sign-up`** | 회원가입 요청(별도 구현 필요)  |

> **📌 로그인 요청 예시 (JSON Body)**
>
> ```json
> {
>     "username" : "admin",
>     "password": "password1234"
> }
> ```

> 💡 추가적인 인증 예외 설정은 `SecurityConfig` 클래스의 `filterChain` 메서드 내부 `requestMatchers(...).permitAll()` 부분에서 수정할 수 있습니다.

### 🔒 인증 필요 URL (Authenticated URL Pattern)
| Method | URI                  | 설명                                 |
|:-------|:---------------------|:-----------------------------------|
| `ALL`  | **`/api/v1/user/**`**  | 인증된 유저 전용 URL 패턴(`ROLE_USER` 필요)   |
| `ALL`  | **`/api/v1/admin/**`** | 인증된 관리자 전용 URL 패턴(`ROLE_ADMIN` 필요) |

-----

## 📂 프로젝트 구조

```bash
.
├── src/main/resources/
│   ├── script/                     # DB 스키마 초기화 SQL
│   ├── application.yml             # 공통 설정
│   └── application-dev.yml         # 개발 프로필 설정
├── compose-dev.yaml                # 개발용 인프라 (Spring Boot 자동 실행)
├── compose.yaml                    # 배포용 전체 인프라
├── Dockerfile                      # 배포용 이미지 빌드 설정
├── .env                            # Docker Compose 환경변수
└── create-spring-app.env           # Spring Boot 애플리케이션 환경변수
```