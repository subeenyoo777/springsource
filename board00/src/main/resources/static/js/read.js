// 날짜 포맷 함수
const formatDate = (str) => {
    const date = new Date(str);
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + +date.getDate() + " " + date.getHours() + ":" + date.getMinutes();
  };
  

  const replyList = () => {
      axios.get(`/replies/board/${bno}`).then((res) => {
          console.log(res.data);
          const data = res.data;
        
          let result = "";
          data.forEach((reply) => {
            result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
            result += `<div class="p-3">`;
            result += `<img src="/img/default.png" alt="" class="rounded-circle mx-auto d-block" style="width: 60px; height: 60px" /></div>`;
            result += `<div class="flex-grow-1 align-self-center">`;
            result += `<div>${reply.replyer}</div>`;
            result += `<div><span class="fs-5">${reply.text}</span></div>`;
            result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
            result += `<div class="d-flex flex-column align-self-center">`;
            result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
            result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
            result += `</div></div>`;
          });
        
          document.querySelector(".replyList").innerHTML = result;
        });    
  }
  

// 댓글 삭제
// 삭제 버튼 클릭 시 data-rno 가져오기
document.querySelector(".replyList").addEventListener("click", (e) => {
    // 어느 버튼의 이벤트?
    console.log(e.target);
    const btn = e.target;
  
  //   // rno 가져오기
    const rno = btn.closest(".reply-row").dataset.rno;
    console.log(rno);
  
  //   // 삭제 or 수정
    if (btn.classList.contains("btn-outline-danger")) {
  
  //       // 삭제
      if (!confirm("정말로 삭제 하시겠습니까?")) 
          return;
  
          axios.delete(`/replies/${rno}`).then((res)=>{
          console.log(res.data);
      
      
  //         // 댓글 다시 불러오기
              replyList();
      })
    } else if (btn.classList.contains("btn-outline-success")) {
  //     // 수정
    }
  });
  
  
  // 페이지 로드 시 호출
  replyList();
  