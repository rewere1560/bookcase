// 모달 요소와 버튼
const openModalBtn = document.getElementById("openModalBtn");
const closeModalBtn = document.getElementById("closeModalBtn");
const modal = document.getElementById("modal");
const submitBtn = document.getElementById("submitBtn");

// 모달 열기
openModalBtn.addEventListener("click", () => {
  modal.style.display = "flex";  // 모달을 보이도록 설정
});

// 모달 닫기
closeModalBtn.addEventListener("click", () => {
  modal.style.display = "none";  // 모달을 숨김
});

// 선택 완료 버튼 클릭 시 처리
submitBtn.addEventListener("click", () => {
  const selectedItems = [];
  const checkboxes = document.querySelectorAll('input[name="item"]:checked');
  checkboxes.forEach(checkbox => {
    selectedItems.push(checkbox.value);
  });

  if (selectedItems.length > 0) {
    alert("선택한 항목: " + selectedItems.join(", "));
  } else {
    alert("선택된 항목이 없습니다.");
  }

  // 모달 닫기
  modal.style.display = "none";
});

// 모달 밖을 클릭하면 모달 닫기
window.addEventListener("click", (event) => {
  if (event.target === modal) {
    modal.style.display = "none";  // 모달을 숨김
  }
});
