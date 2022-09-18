# 사용 기술

- Java 11
- Spring boot 2.6
- Spring Validation
- Spring HATEOAS
- H2
- Querydsl
- JUnit 5
- MockMvc
- Gradle 7
- Docker

# 프로젝트 빌드 및 배포

> 빌드 전, 해당 사항들이 설치되어 있어야 합니다!
> 
> Docker
> 
> Gradle 7
> 
> openjdk

- 프로젝트 디렉토리에서 명령어 실행: `bash deploy.sh`

# API 호출

- 프로젝트 최상단의 내 Postman 컬렉션 `[dynamic-menu].postman_collection.json` 다운로드하여 import 후 실행해주세요!

---

# API 간략정보

## 메뉴 단일조회

- URL: `GET` `/find/{id}`
- Path Parameter: 조회할 메뉴ID

## 메뉴 목록조회

> Infinite Scroll 형식 기반 목록조회입니다

- URL: `GET` `/find`
- Request Parameter
    - size: 응답 갯수
    - lastId: 마지막 메뉴의 ID.
    - parentId: 부모ID. 미요청시 전체, 요청시 해당 부모 메뉴들만 조회
    - depth: 메뉴 뎁스. 미요청시 전체, 요청시 해당 뎁스 메뉴들만 조회
    - title: 메뉴명. 미요청시 전체, 요청시 해당 메뉴명만 조회

## 메뉴 등록

- URL: `POST` `/menu`
- Request Payload
    - parentId: 부모ID. 하위메뉴 등록시 요청, 없을시 최상위 메뉴로 등록 
    - title: 메뉴명(필수)
    - link: 링크(필수)
    - description: 설명
    - banners: 배너

## 메뉴 수정

- URL: `PATCH` `/menu/{id}`
- Path Parameter: 수정할 메뉴ID
- Request Payload
  - title: 수정할 제목, 필수
  - link: 수정할 링크, 필수
  - description: 수정할 내용, 선택

## 메뉴 삭제

- URL: `DELETE` `/menu/{id}`
- Path Parameter: 삭제할 메뉴ID
