# AnonymLog
익명의 힘을 빌려 하고싶은 말을 하자!


======TODO(BackLog)======
- 코드 설명 추가
- rest api로 구현 
- 테스트코드 작성
- 일상 포스팅 기능 구현 (사진, 동영상 업로드 구현) -> S3 이용
- CI/CD -> 젠킨스, 도커

==========진행중==========
- 여러 사용자 사용할 수 있도록 구현
  => 로그인 후 홈 화면 변경 필요(마이페이지, 나의 게시판 등등)
- spring security 이용하여 각 회원이 접근할 수 있는 페이지 인가



===========DONE===========
- 세션 로그인,로그아웃 구현 완료(TESTER 계정)
- TESTER 로그인 -> 비밀글 인증없이 조회
- 페이징 완료
- HEN 로그인 -> 비밀글 인증없이 조회, 모든 게시물 수정가능
- EC2 이용하여 배포
- 댓글 기능 구현
- Spring AOP를 이용한 로그 추적기 구현
- OAuth -> 카카오로그인 구현


======BUG FIX(BackLog)======
 


======BUG FIX(Done)=========
- 게시글 제목, 작성자, 비번 안치고 익명으로 남겼을 때, '비밀게시글 입니다' 안뜸
- post add 및 edit 시 validation 추가
- validation 에러메세지 항상 떠있음
- 로그 추적기 구현중, 로그 안찍히는 이슈 수정
  => @Component 추가를 안했음
- 어떤 로직을 실행하더라도 4번씩 실행되는 버그
  => AOP 에서 joinpoint.proceed 두번 써서 생긴 오류
  => try catch finally문으로 잡음
- ERROR 발생시 에러발생 log, 정상 log 두개가 찍히고 결국 log를 두번 끝내려고 해서 NPE발생
  => try-catch-finally 에서 catch와 finally에서 두번 log를 끝내서 그럼
  => finally구문 삭제, try문에서 해결

