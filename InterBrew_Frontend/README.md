# ☕ 인턴브루(InterBrew)

>
> ### *_Intership Toy Project_* 
> 
> ### **사내 캡슐커피 결제 시스템** 
>

<br/>

## 1. 개요

 #### :pushpin: 사내 탕비실 캡슐커피 결제 시스템화

 -  기존의 수기 작성방법의 비효율성 개선  

 -  효율적 재고/결제 관리 : 통계자료 제공  

<br/>

## 2. 프로젝트 제작 기본 정보
#### 1) 제작기간 
    - 2023-07-28 ~ 2023-09-01
#### 2) 참여인원
    - 3명 (김미란, 노승인, 정범수)


<br/>

## 3. 기술 스택
#### 1) BACK-END
  - Spring boot 2.4.3
  - OpenJDK 1.8.0
  - MariaDB 10.6.5
  - querydsl 5.0.0
  - Redis
#### 2) FRONT-END
  - vuetify 3.3.11

<br/>

## 4. 주요 기능
#### 1) 사용자
    - 로그인 / 회원가입
    - 캡슐커피 결제 / 취소
    - 월별 결제 내역 조회
    - 관리자에게 요청사항 실시간 발신
#### 2) 관리자
    - 간편 통계
    - 재고 관리 : 추가 / 수정
    - 회원별, 월별 결제 내역 관리
    - 요청사항 관리 : 처리 / 미처리 
    - 요청사항 실시간 수신

<br/>

## 5. System Architecture
![image](https://github.com/InternBrew/InterBrew_Docs/assets/119405733/8be2c940-4ce4-4b47-aaae-c8838fa3564a)


<br/>

## 6. ERD
![image](https://github.com/InternBrew/InterBrew_Docs/assets/119405733/633c40ce-e687-4d14-84ae-3ab13ac74ca7)

<br/>

## 7. 주요 트러블슈팅
#### 1) QueryDsl 적용
   - Entity : Setter 사용 지양
   - business logic에서 Entity가 드러나지 않도록 DTO 사용
   - update 관련 : DirtyChecking
      -> QuedyDsl update는 벌크연산시 유용하기 때문에 영속성 컨텍스트로 관리되는 dirty checking을 활용
   - Repository 구조 : Respoisoty Interface extends JpaRespository 
      -> RespositporyCustom Interface를 확장한 RepositoryImpl에서 custom querydsl 적용

#### 2) 결제시스템에 Redis적용

#### 3) Websocket을 활용한 실시간 요청사항 수신/발신
  -  pub/sub : Redis 사용.

<br/>

