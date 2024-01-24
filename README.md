# English
- **Project Description**
1. It utilizes a **Kakao Chatbot** to enable memo creation not only on PCs but also on mobile devices. The system utilizes AI(Chat GPT) to assist in automatic calendar registration for memos.
2. This project won the first prize for the full-stack developer training program’s final projects.
3. took the role of back-end leader (total 6 people, 3 of back-end).
   
 - Technical Experience
    - Implemented login using **Kakao Rest API** and integrated **Kakao Chatbot** services.
    - Considered introducing **MongoDB** to implement a file system with deep hierarchical structure.
    - Automated API documentation using **Swagger**, enabling smooth collaboration with the frontend through **CI/CD automation** using Github actions and Code Deploy.
    - Proposed logic for saving data for non-logged-in users and synchronizing existing data upon the initial login.
      
- Tech Stack
    - Back-end: Spring Boot 3.1.5 , JPA, mongoDB, H2, Java 17
    - Database: H2, MongoDB
    - Server: AWS EC2
    - CI: Github Actions, AWS CodeDeploy
 ---
 # Korean
 # 프로젝트 소개
 - 구름 풀스택 개발자 양성과정 1회차 최종 프로젝트입니다.
 - 메모를 '한 곳' 에서 '편리' 하게 작성할 수 있도록 하는 프로젝트입니다.
 - [시연 영상](https://www.youtube.com/watch?si=esyT39wzfpUsmYXV&v=NIDKg0yNWJs&feature=youtu.be)
## 주요기능
1. 퀵인풋을 통해서 작성한 todo 를 chat GPT 가 인식해서 자동으로 캘린더에 추가해줍니다.
   - ex) 내일까지 과제 마감하기 => 캘린더에 내일에 해당하는 날짜에 "과제 마감하기" 항목이 추가됩니다.
2. 캘린더를 통해서 편리하게 일정관리를 할 수 있습니다.
3. memo 를 통해서 일반적인 메모도 할 수 있습니다.
4. 카카오 로그인을 통해서 간편하게 가입할 수 있고, 가입할 경우 기존의 메모와 동기화되며, 클라우드 기반으로 다른 장소에서도 메모를 확인할 수 있습니다.
5. 카카오 챗봇기능을 활용해서 카톡 채널에 보내는 내용을 저장하고, PC 에서 확인할 수 있습니다.
