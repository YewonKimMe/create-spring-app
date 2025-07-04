# 🌱 create-spring-app

> Spring Boot 기반 사이드 프로젝트를 빠르게 시작할 수 있도록 구성된 보일러플레이트입니다.  
> 웹, REST API, Kafka, Redis, Security 등 다양한 기능이 기본으로 통합되어 있습니다.
> 웹 클라이언트, 앱 클라이언트 상호 호환을 지원하도록 만들었습니다.
> 
> 템플릿 리포지토리 기반으로 리포지토리를 생성후, 다음 단계를 따라서 자신만의 리포지토리로 변경하면 됩니다.

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
## 📦 주요 기술 스택

| 영역             | 사용 기술 |
|------------------|-----------|
| Language         | Java 17 (Toolchain 적용) |
| Build Tool       | Gradle 8.x |
| Framework        | Spring Boot 3.5.3 |
| ORM              | Spring Data JPA, QueryDSL |
| DB               | MySQL |
| Cache            | Redis |
| Messaging        | Apache Kafka |
| View             | Thymeleaf + Spring Security |
| API 문서         | SpringDoc OpenAPI (Swagger) |
| 인증             | Spring Security + JWT |
| 이메일 발송      | Spring Mail |
| HTML 파싱        | Jsoup |
| 테스트           | JUnit 5, Spring Security Test, Kafka Test |
| 기타             | Slack API 연동, Firebase Admin SDK |

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
