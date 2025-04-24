// 체크박스 클릭이 되면
// checkbox value,data-id 값 가져오기

// 이벤트 버블링(자식의 이벤트가 부모한테 전달)
document.querySelector(".list-group").addEventListener("click", (e) => {
  // 어느 label 내부 checkbox에서 이벤트 발생했는지 확인
  const chk = e.target; //체크박스 있는 태그 자체를 가져옴
  console.log(chk);
//   checknbox 체크, 해제 여부 확인
  console.log(chk.checked);

// id 가져오기
// closest("선택자") : 부모(여기선 chk)에서 제일 가까운 요소 찾기 
//   data-이름 속성 값 가져오기 :  dataset
  const id = chk.closest("label").dataset.id;
  console.log(id);

//   actionForm 찾은 후 값 변경하기
const actionForm = document.querySelector("#actionForm");

actionForm.id.value = id;
actionForm.completed.value = chk.checked;

actionForm.submit();

});
