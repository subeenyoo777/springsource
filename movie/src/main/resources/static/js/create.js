// 등록 버튼을 누르면 폼 submit 중지
// li 태그 정보 수집 후, form 에 추가
// 폼에 전송.
document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  //   li 정보 찾음(업로드 파일 정보)
  // 부모 찾기
  const output = document.querySelectorAll(".uploadResult li");

  // 속성 찾을 시 "."찍거나 getAttribute
  // data- : dataset

  let result = "";
  output.forEach((obj, idx) => {
    console.log(obj.dataset.uuid);

    result += `<input type="hidden" name="movieImages[${idx}].path" value="${obj.dataset.path}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value="${obj.dataset.uuid}"/>`;
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value="${obj.dataset.name}"/>`;
  });

  // 폼에 추가
  e.target.insertAdjacentHTML("beforeend", result);
  e.target.submit();
});
