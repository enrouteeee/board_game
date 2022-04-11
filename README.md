# board\_game

## 0\. 목차

### 개요

### 프로젝트 환경

### 구현한 것

### 구현하지 못한 것

### 트러블 슈팅
<br/>

## 1\. 개요

웹사이트에서 동작하는 보드게임 어플리케이션을 구현한다.

다빈치 코드 게임을 시작으로 다양한 게임을 추가한다. 점수 시스템, 매칭 시스템 등 다양한 시스템을 추가한다.

다양한 게임과 서비스를 추가 확장할 수 있도록 객체지향적 설계를 적용한다.

클라이언트와 서버를 구현 및 배포까지 프로젝트가 진행되면서 요구되는 전반적인 경험을 해본다.
<br/><br/>

## 2\. 프로젝트 환경

#### **Spring boot**

-   Java 1.8
-   Spring boot 2.6.4
-   Spring Web MVC
-   Spring Security OAuth2
-   Spring boot WebSocket
-   Stomp-WebSocket

#### **DB**

-   H2
-   MariaDB

#### **Vue**

-   Vue 2.6.14
-   Vuex
-   Vue-Router
-   Axios
-   Vuetify

#### **Deploy**

-   Backend : AWS EC2
-   DB : AWS RDS
-   Frontend : Netlify
<br/><br/>

## 3\. 구현한 것

#### **로그인**

~간단한 회원가입 및 로그인 (세션 사용)~

OAuth2 를 이용한 소셜 로그인 (세션 샤용)

세션 -> JWT 인증 방식으로 변경

#### **게임방**

채팅 및 게임 시작

#### **다빈치코드 게임**

각각의 Command에 따른 적절한 게임 진행
<br/><br/>

## 4\. 해야할 것

로그인 : refresh token 기능 구현

배포 + https 설정 : 클라이언트(https)와 서버(http) Mixed content 문제 발생, 서버 https 설정하기

validation : DTO 로 오는 값을 검증 없이 사용하고 있다. validation 적용하기

예외 처리 : 검증에 대한 오류나 로직에 대한 오류에 있어서 서버에서 제대로 된 응답을 주기

페이지를 강제로 나가거나, 새로고침 한 경우 처리 : 정상적으로 stomp연결을 해제하지 않는 경우에 대해 적절한 로직을 추가해주기

프론트엔드 리펙토링 : .view 파일마다 각 페이지에 대한 모든 소스코드가 전부 들어가 있다. 컴포넌트, 모듈 등을 이용해서 리펙토링 하기

~디자인 : 예쁘게 디자인 하기~
<br/><br/>

## 5\. 트러블 슈팅

cors

setCookie

dto mapping
<br/><br/>

## 6\. 배운 것들
