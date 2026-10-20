# Post_Test_JCH

Spring Boot MVC 게시판 과제 (문항 1~6).

## 기술
Java 17, Gradle 8.14.3, Spring Boot 3.5.7, Thymeleaf, MyBatis, MySQL 8.

## 기능
게시글 등록 · 목록 · 조회 · 수정 · 삭제, 최신순 목록, 10개 단위 페이징,
5개 단위 페이지 번호, 입력값 검증, 없는 게시글 404 처리.

## 실행 (Windows / IntelliJ)
1. JDK 17과 MySQL 8을 설치합니다.
2. `sql/create_database.sql`을 MySQL에서 실행합니다.
3. IntelliJ에서 `build.gradle`을 프로젝트로 열고 Gradle 동기화를 실행합니다.
   Gradle 배포판은 Wrapper, Gradle JVM은 JDK 17로 지정합니다.
4. `BoardApplication` 실행 구성의 환경 변수에 `DB_USERNAME`, `DB_PASSWORD`를 설정합니다.
   기본 연결 주소는 `jdbc:mysql://localhost:3306/post_test`입니다.
   다른 서버를 쓰면 `DB_URL`도 설정합니다. 비밀번호를 Git에 올리지 마세요.
5. `BoardApplication`을 실행하고 http://localhost:8080 에 접속합니다.

프로젝트 폴더의 PowerShell에서는 다음처럼 실행할 수 있습니다.

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="본인 MySQL 비밀번호"
.\gradlew.bat bootRun
```

테이블은 `schema.sql`로 자동 생성되며 기존 데이터는 삭제하지 않습니다.

## 검증
`.\gradlew.bat clean test bootJar`를 실행합니다. 테스트는 MySQL 모드의 H2를 사용하므로
실제 MySQL 계정 없이 CRUD, 화면 렌더링, 페이징 경계값을 확인할 수 있습니다.
GitHub Actions에도 동일한 빌드 명령이 등록되어 있습니다.
H2 테스트와 별도로 실제 MySQL 연결 환경에서도 실행 확인이 필요합니다.

## 제출 파일
- 문항 1: `src/main/resources/mapper/PostMapper.xml`
- 문항 2: `dto/Post.java`, `service/PostService.java`, `service/PostServiceImpl.java`, `mapper/PostMapper.java`
- 문항 3: `controller/PostController.java`, `build.gradle`
- 문항 4: `templates/index.html`, `templates/Post/{list,read,insert,update}.html`
- 문항 5: `dto/Page.java` 및 Controller/Service/Mapper의 페이징 코드

Java 경로의 기준은 `src/main/java/com/example/board`, 뷰 경로의 기준은
`src/main/resources`입니다. URL은 과제에 맞춰 `/Post/list`, `/Post/read?PostNo=1`,
`/Post/insert`, `/Post/update?PostNo=1`로 통일했습니다.

로그인 없이 사용하는 CRUD 학습용 프로젝트입니다.

## 제출 URL
https://github.com/rkdcjfvbxj/Post_Test_JCH
