
# ♻️멋사마켓 
 🛒 중고 제품 거래 플랫폼을 만들어보는 미니 프로젝트입니다.
<br><br><br>

# ✔️ ERD 
![image](https://github.com/likelion-backend-5th/MiniProject_Basic_JungSuHyeon/assets/108582847/ea98c0d2-6fd1-4b2f-a2c6-1c52c34a1f4f)


<br> <br>
## 🔨 API 모아보기 🔨
<details>
<summary> (참고) API 확인 시 토글을 누르고 상황에 따른 요청과 응답을 확인해 보세요 ! </summary>
<div markdown="1">
<img width="589" alt="image" src="https://github.com/likelion-backend-5th/MiniProject_Basic_JungSuHyeon/assets/108582847/7e5907be-5381-42ce-bcec-3154711ecafc">
 </div>
</details>

[  1️⃣  API 바로 가기  ](https://documenter.getpostman.com/view/26726157/2s93zE4LU4) <br> <br>
[2️⃣ API 바로 가기  ](https://documenter.getpostman.com/view/26726157/2s93zE4LU7)<br> <br>
[3️⃣ API 바로 가기  ](https://documenter.getpostman.com/view/26726157/2s93zE4LYP)<br> <br>




## ✨ 요구사항 설명 ✨

### 1️⃣ 중고 물품 관리
<details>
<summary> 누구든지 중고 거래를 목적으로 물품에 대한 정보를 등록할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이때 반드시 포함되어야 하는 내용은 제목, 설명, 최소 가격, 작성자이다.</li><br>
  <li>또한 사용자가 물품을 등록할 때, 비밀번호 항목을 추가해서 등록한다.</li><br>
  <li>최초로 물품이 등록될 때, 중고 물품의 상태는 판매중 상태가 된다.</li><br>
</ul>
  </div>
</details>
<details>
<summary> 등록된 물품 정보는 누구든지 열람할 수 있다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>페이지 단위 조회가 가능하다.</li><br>
  <li>전체 조회, 단일 조회 모두 가능하다.</li><br>
</ul>
  </div>
</details>
<details>
<summary> 등록된 물품 정보는 수정이 가능하다.   </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.</li><br>
</ul>
  </div>
</details>
<details>
<summary> 등록된 물품 정보에 이미지를 첨부할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.</li><br>
  <li>이미지를 관리하는 방법은 자율이다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 물품 정보는 삭제가 가능하다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 물품이 등록될 때 추가한 비밀번호를 첨부해야 한다.</li><br>
</ul>
  </div>
</details>
물품 관리 API : https://documenter.getpostman.com/view/26726157/2s93zE4LU4 <br> <br>

</div>
</details>


### 2️⃣ 중고 물품 댓글 관리

<details>
<summary>등록된 물품에 대한 질문을 위하여 댓글을 등록할 수 있다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이때 반드시 포함되어야 하는 내용은 대상 물품, 댓글 내용, 작성자이다.</li><br>
  <li>또한 댓글을 등록할 때, 비밀번호 항목을 추가해서 등록한다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 댓글은 누구든지 열람할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>페이지 단위 조회가 가능하다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 댓글은 수정이 가능하다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 댓글이 등록될 때 추가한 비밀번호를 첨부해야 한다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 댓글은 삭제가 가능하다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 댓글이 등록될 때 추가한 비밀번호를 첨부해야 한다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 댓글은 삭제가 가능하다. </summary>
<div markdown="1">
<br>
<ul>
  <li>만약 댓글이 등록된 대상 물품을 등록한 사람일 경우, 물품을 등록할 때 사용한 비밀번호를 첨부할 경우 답글 항목을 수정할 수 있다. </li><br>
  <li>답글은 댓글에 포함된 공개 정보이다. </li><br>
</ul>
  </div>
</details>
댓글 관리 API : https://documenter.getpostman.com/view/26726157/2s93zE4LU7<br> <br>

### 3️⃣ 구매 제안 가능 
<details>
<summary>등록된 물품에 대하여 구매 제안을 등록할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이때 반드시 포함되어야 하는 내용은 대상 물품, 제안 가격, 작성자이다. </li><br>
  <li>또한 구매 제안을 등록할 때, 비밀번호 항목을 추가해서 등록한다. </li><br>
  <li>구매 제안이 등록될 때, 제안의 상태는 제안 상태가 된다. </li><br>
</ul>
  </div>
</details>
<details>
<summary>구매 제안은 대상 물품의 주인과 등록한 사용자만 조회할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>대상 물품의 주인은, 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다. <br>
    이때 물품에 등록된 모든 구매 제안이 확인 가능하다. 페이지 기능을 지원한다. </li><br>
  <li>등록한 사용자는, 조회를 위해서 자신이 사용한 작성자와 비밀번호를 첨부해야 한다. <br>
    이때 자신이 등록한 구매 제안만 확인이 가능하다. 페이지 기능을 지원한다. </li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 제안은 수정이 가능하다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 제안이 등록될때 추가한 작성자와 비밀번호를 첨부해야 한다. </li><br>
</ul>
  </div>
</details>
<details>
<summary>등록된 제안은 삭제가 가능하다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이때, 제안이 등록될때 추가한 작성자와 비밀번호를 첨부해야 한다. </li><br>
</ul>
  </div>
</details>
<details>
<summary>대상 물품의 주인은 구매 제안을 수락할 수 있다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이를 위해서 제안의 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.  </li><br>
  <li>이때 구매 제안의 상태는 수락이 된다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>대상 물품의 주인은 구매 제안을 거절할 수 있다.  </summary>
<div markdown="1">
<br>
<ul>
  <li>이를 위해서 제안의 대상 물품을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.</li><br>
  <li>이때 구매 제안의 상태는 거절이 된다.</li><br>
</ul>
  </div>
</details>
<details>
<summary>구매 제안을 등록한 사용자는, 자신이 등록한 제안이 수락 상태일 경우, 구매 확정을 할 수 있다. </summary>
<div markdown="1">
<br>
<ul>
  <li>이를 위해서 제안을 등록할 때 사용한 작성자와 비밀번호를 첨부해야 한다.</li><br>
  <li>이때 구매 제안의 상태는 확정 상태가 된다. </li><br>
  <li>구매 제안이 확정될 경우, 대상 물품의 상태는 판매 완료가 된다.</li><br>
  <li>구매 제안이 확정될 경우, 확정되지 않은 다른 구매 제안의 상태는 모두 거절이 된다.</li><br>
</ul>
  </div>
</details>
구매 제안 API : https://documenter.getpostman.com/view/26726157/2s93zE4LYP <br> <br>

<br><br>

* * *
## ➕ 추가 개선 방향 ➕

- [ ] 구매 제안 수정하기
      
  - [ ] 확정(판매 완료) -> nego 할 수 없음
        
  - [ ] 판매자 수락이면 금액 수정 불가
        
- [ ] minPriceWanted 보다 적은 금액을 제시할 수 없음

* * *

### 🗓️ 프로젝트 기간 🗓️
### 2023 . 06 . 29 ~ 2023 . 07 . 04
* * *

### 🪄 개발 스택 🪄
> Language : Java 17 <br>
> Framwork : SpringBoot 3.1.1<br>
> Database : SQLite<br>
> Tool : IntelliJ IDEA, Postman<br>

* * *
### 💻 developer 💻
👩‍💻 JUNG SU HYEON <br>
🔸Github : https://github.com/walwaljj


