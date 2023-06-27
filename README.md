# Spring Boot - Cookie(🍪) 사용 예제

### Spring Boot Cookie 개발환경

- Project : Gradle - Groovy
- Language : Java
- Spring Boot : 3.1.0
- Packaging : Jar
- Java : 17(JDK 버전)
- Dependencies : Spring Web, Lombok

### 0. Spring Boot Cookie Test

- Cookie 카운트 확인 - **이전에 방문한 적이 있는 웹서버에 다시 방문했을 때 몇 번째 방문인지**
  <p align=”center”><img width="70%" src="https://github.com/choidabom/Spring-Boot-Cookie/assets/48302257/dedfa2fc-1b58-4c55-b2cd-9f01dfc93d18"/></p>


- 개발자 도구에서 Application > Storage > Cookies
  <p align=”center”><img width="70%" src="https://github.com/choidabom/Spring-Boot-Cookie/assets/48302257/7c9a2356-4051-493d-995a-dfa1a0204e3a"/></p>

## 1. API 응답에 쿠키를 넣는 방법

### 1) 쿠키 생성

- 쿠키를 생성하려면 `javax.servlet.http.Cookie` 객체를 생성한다.
- 쿠키를 생성하려면 파라미터에 `HttpServletResponse` 객체가 필요하다.

```java
Cookie visitCountCookie=new Cookie("visitCount",cookieValue);

// Cookie cookie = new Cookie("쿠키이름", "쿠키값");
```

### 2) 쿠키 유효기간 설정

- **쿠키는 브라우저가 닫혀도 유효한가? 그렇다.**
- 브라우저가 닫히더라도 maxAge를 통해서 또는 브라우저의 설정에 따라 일정기간 동안 Cookie가 저장될 수 있다.

```java
visitCountCookie.setMaxAge(24*60*60);
// 초단위로 설정된다. 24 * 60 * 60 -> 24번 * 60번 * 60초 = 24시간
```

### 3) 현재 경로 이하에 모두 쿠키 적용

- 서버의 특정경로 요청에서만 쿠키를 전송하고자 할 때 **setPath()** 메소드를 사용
- setPath() 메소드의 인자값으로 지정하면, 지정된 경로와 그것의 하위 경로의 요청에 대해서만 클라이언트로부터 쿠키가 전송된다.

```java
visitCountCookie.setPath("/");
```

### 4) 클라이언트에게 보낼 응답에 쿠키 포함

```java
response.addCookie(visitCountCookie);
```

## 2. API 요청 헤더에서 쿠키 읽는 방법

### 1) @CookieValue Annotation 활용

- @CookieValue 어노테이션 이용하여 쿠키를 전달받을 수 있다.
- value 속성을 전달받을 쿠키의 이름으로 지정한다.
- defaultValue 속성은 쿠키가 없을 때, 최초로 지정할 값을 설정한다.

```java
@CookieValue(value = "visitCount", defaultValue = "0", required = true) String cookieValue

// cookieValue 파라미터는 @CookieValue 어노테이션으로부터 값을 받아온다.
// 만약 visitCount 이름의 쿠키가 존재하지 않으면, defaultValue로 지정된 "0" 값을 사용한다.
```

## 3. Thymeleaf 사용

- Thymeleaf 란?
    - 뷰 템플릿(View Template)
    - 뷰 템플릿은 컨트롤러가 전달하는 데이터를 이용하여 동적으로 화면을 구성할 수 있게 해준다.
- 의존성 추가
    - `dependencies { ... implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'}`
- 프로젝트 디렉터리 구조 내에서 Thymeleaf가 기본으로 사용될 경로
    - 프로젝트 디렉터리(root) > src > main > resources > **templates 폴더**
    - (+ just 추가 설명) templates 폴더와 같은 위치에 있는 **static 폴더**는 정적 파일(css)을 담는 폴더

### + About Cookie

  <details>
  <summary> More) 쿠키에 대해서 </summary>
  <h5>쿠키의 정의</h5>
  <li>쿠키는 서버가 클라이언트에 저장하는 정보로서, 클라이언트와 연결이 끊어져도 클라이언트에 저장된 정보가 유지되어 웹 서버에 요청을 보낼 때 헤더에 담아서 전송한다.</li>
  <li>브라우저는 그 데이터 조각들을 저장해 놓았다가, 동일한 서버에 재 요청 시 저장된 데이터를 함께 전송한다.</li>
  <li>쿠키는 두 요청이 동일한 브라우저에서 들어왔는지 아닌지를 판단할 때 주로 사용한다. 이를 이용하면 사용자의 로그인 상태를 유지할 수 있다</li>

  <br>  
  <h5>쿠키의 목적</h5>
   <li>세션 관리(Session management)</li>
    <ul>서버에 저장해야 할 로그인, 장바구니, 게임 스코어 등의 정보 관리</ul>
   <li>개인화(Personalization)</li>
    <ul>서버에 저장해야 할 로그인, 장바구니, 게임 스코어 등의 정보 관리</ul>
   <li>트래킹(Tracking)</li>
    <ul>사용자 행동을 기록하고 분석하는 용도</ul>





  </details>

ref. https://github.com/ohohoi/spring-boot-cookie
