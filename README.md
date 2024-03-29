# 🖥️다함께 잡잡잡!
취업 관련 스터디를 모집하고 참여할 수 있는 채팅 웹 프로젝트입니다.

## 🔍프로젝트 개발환경
- IDE(IntelliJ)
- JDK 17
- Spring 3.2.1
- JPA
- Thymleaf
- OAuth 2.0
- JWT
- Web socket.io
- AWS RDS(MySQL)


## 📝프로젝트 기능 및 설계

- 회원가입 기능
  - 고객은 회원가입을 할 수 있다.
  - 회원가입 시 이메일, 비밀번호, 이름, 전화번호, 희망 직무를 입력해야 하고 이메일은 unique해야 한다.
  - 구글, 네이버, 카카오를 통해 소셜 로그인이 가능하다.

- 로그인 기능
  - 이메일, 비밀번호를 입력 후 로그인이 가능하다.

- 회원 정보 관리
  - 비밀번호, 전화번호, 희망 직무 중 수정할 사항을 수정할 수 있다.
  - 회원 탈퇴가 가능하다
 
- 채팅방
  - 회원 누구나 채팅방 생성이 가능하다. 채팅방 생성 시 제목, 상세 설명, 최대 인원을 작성 및 설정해야 한다.
  - 회원은 자신이 생성한 채팅방의 정보를 수정할 수 있고 채팅방을 삭제할 수 있다.
  - 희망 직무에 해당하는 생성된 채팅방 목록을 확인할 수 있다.
  - 채팅방 내에서 메시지, 사진 전송이 가능하고 채팅 데이터는 전송되는 즉시 데이터베이스에 저장된다.

- 투두 리스트
  - 채팅방 내에 스터디원 간 공유 가능한 투두 리스트를 작성, 수정, 삭제할 수 있다.
  - 투두 리스트 내용을 이행했는 지 체크 가능하다.


## 🖊️ERD

![image](https://github.com/Team-JobJobJob/study_project/assets/129375053/b3d10b61-20a4-4c40-978e-b77c3dbad706)


