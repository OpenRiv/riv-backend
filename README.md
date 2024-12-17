# Riv-Backend 🖥️

**Riv-Backend**는 Spring Boot와 JPA 기반으로 개발된 백엔드 서비스입니다.

**Discord Bot**과 연동하여 사용자 서버 및 채널 정보를 관리하고, 
회의록을 처리하는 다양한 API를 제공합니다. 이 프로젝트는 **협업 효율화**를 목표로 하며,
OAuth2 로그인, Swagger 문서화, SSL 인증서 설정을 포함하고 있습니다.

<br/>

## 주요 기능 


✅️ **Spring Security를 사용한 OAuth2 로그인**

디스코드 OAuth2를 활용해 사용자 인증 및 서버 정보 접근이 가능합니다.

🗂️ **Discord Open API를 통한 서버/채널 정보 조회**

유저가 소유한 서버 목록과 채널 정보를 가져오고, 봇이 설치된 서버인지 확인합니다.

📝 **회의록 API**

디스코드 봇과 연동해 회의록을 저장, 조회, 수정, 삭제하는 API를 제공합니다.

🛠️ **Swagger 세팅**

API 문서를 Swagger를 통해 시각화하여 협업 및 테스트를 용이하게 합니다.

🔐 **SSL 인증서 사용한 HTTPS 설정**

SSL 인증서를 설정해 보안을 강화하고, 리다이렉트 설정으로 HTTPS를 지원합니다.

<br/>

# Riv-backend Manual 📖

## 1. 설치 가이드 🚀

### 시스템 요구사항

- JDK 8 (Java 8 사용)
- Gradle (빌드 툴)
- **AWS EC2** 및 **RDS MySQL** 사용
- 디스크 여유 공간: 최소 1GB
- RAM: 최소 512MB
<br/>

### **설치 단계**

1. **리포지토리 클론**

```bash
git clone https://github.com/OpenRiv/riv-backend.git
cd riv-backend
```
<br/>

2. **Gradle 의존성 설치
- `build.gradle` 파일에 설정된 의존성을 설치합니다.

```bash
./gradlew clean build
```
<br/>

3. **데이터베이스 설정**
- MySQL RDS 인스턴스를 준비하고 `application.yml`에 접속 정보를 입력합니다.

```yaml
spring:
  datasource:
    url: jdbc:mysql://your-rds-endpoint/db_name
    username: your_db_username
    password: your_db_password
  jpa:
    hibernate:
      ddl-auto: update
```
<br/>

4. **서버 실행** (443 포트)

```bash
nohup java -jar oauth2-discord.jar &
```

<br/>

## 개발 환경 설정 ⚙️
<br/>

**Gradle 플러그인 및 버전**

```
plugins {
    id 'java'
    id 'org.springframework.boot' version '2.7.15'
    id 'io.spring.dependency-management' version '1.0.15.RELEASE'
}
```
<br/>
**주요 의존성**

```
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-security'
    implementation 'org.springframework.security:spring-security-oauth2-client'
    implementation 'org.springdoc:springdoc-openapi-ui:1.6.15'
    implementation 'net.dv8tion:JDA:5.2.1' // 디스코드 봇 JDA
    runtimeOnly 'com.mysql:mysql-connector-j'
    compileOnly 'org.projectlombok:lombok'
    annotationProcessor 'org.projectlombok:lombok'
}
```
<br/>
**application.yml 파일 설정**
- ssl 인증서를 이용하기 위한 정보를 저장합니다.
- 디스코드 봇의 이용 권한을 얻기 위해 scope를 설정합니다.
- 로그인 시, 디스코드 봇이 이동할 redirect url과, 디스코드 봇 토큰을 부여합니다. 
```
server:
  address: 0.0.0.0
  port: 443
  ssl:
    enabled: true
    key-store: your_key_store
    key-store-password: your_password
    key-store-type: PKCS12
    key-alias: your_alias

spring:
  security:
    oauth2:
      client:
        registration:
          discord:
            client-id: your_client_id
            client-secret: your_client_secret
            clientAuthenticationMethod: post
            authorizationGrantType: authorization_code
            scope:
              - identify
              - guilds
              - email
              - guilds.members.read
              - role_connections.write
            redirect-uri: "http://riv-frontend.vercel.app/login/oauth2/code/discord"
            clientName: rivBot client
        provider:
          discord:
            authorizationUri: https://discordapp.com/api/oauth2/authorize
            tokenUri: https://discordapp.com/api/oauth2/token
            userInfoUri: https://discordapp.com/api/users/@me
            user-name-attribute: username
         
...
discord:
  bot:
    token: 

```

<br/>
## 3. 사용자 가이드 🗒️

<br/>
### **Swagger 문서 사용법**

- 서버를 실행한 후 브라우저에서 Swagger 문서를 확인합니다
   - `https://3.37.89.101/swagger-ui/index.html#`
- HTTPS 설정 시, **HTTP → HTTPS 리다이렉트**가 자동 적용됩니다.

<br/>

## 4. 개발자 가이드 👨‍💻


### **코드 구조**

```css
src/
├── main/
│   ├── java/
│   │   └── ssu/
│   │       └── riv/
│   │           ├── domain/
│   │           │   ├── channel/
│   │           │   │   ├── controller/
│   │           │   │   ├── service/
│   │           │   │   ├── repository/
│   │           │   │   ├── dto/
│   │           │   │   └── entity/
│   │           │   └── global/
│   │           │       ├── config/
│   │           │       ├── error/
│   │           │       ├── result/
│   │           │       └── security/
│   │           └── RivApplication.java
│   └── resources/
│       ├── application.yml
│       └── bootsecurity.p12
└── test/

```
<br/>

### 기술 스택

- Spring Boot
- JPA
- Spring Security
- Swagger
- MySQL (AWS RDS)
- AWS EC2
- JDA (Discord API 사용)

<br/>

### 주요 코드 🛠️

**1. Http 헤더 설정**

- 디스코드에 날리는 API는 항상 헤더에 USER-AGENT 정보를 가지고 있어야 유효하기 때문에, 해당 정보를 헤더에 항상 추가합니다.

```java
class OAuth2UserAgentUtils {
    static RequestEntity<?> withUserAgent(RequestEntity<?> request) {
        HttpHeaders headers = new HttpHeaders();
        headers.putAll(request.getHeaders());
        headers.add(HttpHeaders.USER_AGENT, DISCORD_BOT_USER_AGENT);

        return new RequestEntity<>(request.getBody(), headers, request.getMethod(), request.getUrl());
    }

    private static final String DISCORD_BOT_USER_AGENT = "RivBot (https://github.com/OpenRiv/riv-bot.git)";
}
```

**2. Discord Open API를 사용한 회의록 API 예제**
- Open API를 사용하여 디스코드로부터 해당 사용자의 채널의 목록을 받아옵니다.
- 이를 호출할 권한을 갖기 위해, 헤더에 봇 토큰을 추가하여 Discord API를 호출합니다.
```java
// 디스코드로부터, 채널의 목록을 가져온다. (권한 우회)
@Override
public List<Long> getGuildChannel(Long guildId) {
    // 디스코드 API 엔드포인트 설정
    String url = "https://discord.com/api/guilds/" + guildId + "/channels";

    // 헤더에 봇 토큰 추가
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bot " + botToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);

    // 디스코드 API 호출
    ResponseEntity<Channel[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Channel[].class);

    // 채널 ID 목록 반환
    return Arrays.stream(Objects.requireNonNull(response.getBody()))
            .map(Channel::getId)
            .collect(Collectors.toList());
}

```

<br/>

## 5. 문제 해결 🛠️


1. **CORS 오류 해결**
    - WebConfig에 아래와 같은 Cors 설정 추가
    - allowedOrigin으로 해당 url 허용

    ```java
    .allowedOrigins("http://3.37.89.101:8080",
    "https://3.37.89.101:443",
    "https://riv-frontend.vercel.app",
    "http://localhost:5173",
    "https://www.riv-discord.online/",
    "https://vercel.live")
    ```

2. **HTTPS 리다이렉트 오류**
- resources/ 아래에 자체 SSL 인증서를 생성
- 이후 application.yml 설정

<br/>

## 6. 기여 가이드 🤝

<br/>

### **파일 구조 규칙**

- Controller → Converter -> Service → Repository 순으로 계층 분리
- 예외 처리는 `BusinessException`과 `ErrorCode`를 사용

<br/>

### **함수화 가이드**

- `findChannel(Long channelId)`처럼 반복되는 코드를 함수화합니다.
- 예외 발생 시 Exception을 던져 명확하게 처리합니다.

<br/>

### **Pull Request 프로세스**

1. **Fork 저장소**
2. **Feature 브랜치 생성**
3. **코드 작성 및 테스트**
4. **Pull Request 생성**
5. **코드 리뷰 진행**

<br/>

---

이 메뉴얼은 지속적으로 업데이트됩니다. 개선사항이나 제안사항이 있다면 Issue를 생성해주세요.

---

**리브**

생각은 회의에, 기록은 RIV에 맡기세요!

Riv는 AI 기술을 통해 어렵고, 복잡하고, 귀찮았던 우리의 회의를 쉽고, 간편하고, 즐거운 회의로 바꿔줍니다. Riv와 함께 우리의 세상을 바꿔나가고 싶지 않나요?

Riv는 오픈소스 프로젝트입니다. 우리 함께 열려있는 협업 세상을 만들어봐요!

**편유나** Chatbot&AI

**문세종** Frontend

**김혜령** Backend
