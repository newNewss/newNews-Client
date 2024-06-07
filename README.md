
<div align="center">

<h2>🗞️ 뉴 뉴스 📰</h2>


<div>카테고리 별로 오늘의 핫한 기사 보여주는 어플리케이션!<br/>
뉴스를 잘 보지 않는 현대인들... 이 어플을 이용해서 뉴스를 손쉽게 접하고 읽을 수 있습니다.</div>



</div>


<h2> 🍀 진행사항 🍀 </h2>

5/24 모바일컴퓨팅 주제 회의<br/>
1. 현대인들이 귀찮아서 뉴스를 잘 보지 않는데, 이 어플을 이용해서 뉴스를 손쉽게 접할 수 있음. 'like 뉴스 오마카세'<br/>
2. 다시 보고 싶은 뉴스는 저장 가능. <br/>
3. 네이버 뉴스 api 이용<br/> <br/>

5/25 기능 명세서 작성<br/>
- 메인페이지
- 카테고리 선택 화면
- 뉴스기사 추천 화면
- 뉴스 기사 상세보기 화면
- 좋아요한 뉴스 리스트 화면

5/27 주제 회의<br/>

- 지은: 메인페이지 퍼블리싱
- 성우: 네이버 api 연결. 기사 불러오도록 구현.
- 성표: 네이버 기사 전처리
     
    
5/31 맡을 개발 파트 회의     

- 지은: 메인페이지 수정, DB 생성해서 좋아요 추가, 삭제 기능 구현
- 성우: ‘상세보기’ 버튼 누르면 기사 상세보기 모달로 이어지도록 구현
- 성표: 메인페이지에서 ‘카테고리 재선택’ 버튼 누르면 카테고리 재선택이 가능하도록 구현

6/7 개발 현황체크 및 최종 발표 회의



<h2>📌   뉴뉴스 기능</h2>    

- 모바일컴퓨팅 기말프로젝트 '뉴뉴스' 저장소입니다.
1. 카테고리 별로 기사 보여주기
2. 다시 보고 싶은 뉴스 찜하기 기능
3. 카테고리 별 추천 받고싶은 기사 개수 선택 기능
4. firebase 를 이용한 알림 기능
    1. “오늘의 기사가 추천 되었습니다!”
5. 기사에 대한 개인 메모 기능



<h2> 🛠 기술스택 </h2>

|  **category**   |          **stack**           |
| :-------------: | :--------------------------: |
|    `Language`     |            `Kotlin`            |
|    `api`     |            `네이버 뉴스 검색 api`            |


<br/>

<br/>

## 🖋️ 커밋 컨벤션

| **태그**  |           **설명**            |
| :-------: | :---------------------------: |
|   feat   |   새로운 기능을 구현한 경우   |
|    fix   |     버그 수정, 오류 해결      |
|  refactor |     코드 수정 및 리팩토링     |
|  style |      스타일 추가 및 수정      |
|    docs  |     문서 추가, 수정, 삭제     |
|    init   |      개발 환경 초기 세팅      |
|   chore  | 그 외 자잘한 수정 (주석 삭제) |

<br />

## 🖋️ 브랜치 전략

`Github-flow`

- 브랜치명
    - **camelCase**

```
develop
 ㄴ feature/featureName
 ㄴ fix/fixContent
```

<br />

