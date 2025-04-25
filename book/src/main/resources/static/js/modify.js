// 삭제 버튼이 클릭되면?
// actionForm 을 submit, 실행

// script 완성(2줄)
document.querySelector(".btn-danger").addEventListener("click", (e)=>{
    
    // actionForm 찾아오기
    const actionForm = document.querySelector("#actionForm");
    console.log("삭제클릭", actionForm);
    actionForm.submit();//화면 단 코드한 폼 내용을 보내달라
    
})