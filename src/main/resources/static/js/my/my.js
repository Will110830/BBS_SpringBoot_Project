// 프로필 사진 미리보기
function previewProfilePic(event) {
  const input = event.target;
  if (input.files && input.files[0]) {
    const reader = new FileReader();
    reader.onload = function (e) {
      document.getElementById('profilePicPreview').src = e.target.result;
    };
    reader.readAsDataURL(input.files[0]);
  }
}

// 네임 수정 가능하게 변경
function enableNameEdit() {
  const display = document.getElementById('nameDisplay');
  const input = document.getElementById('nameInput');
  input.value = display.textContent;
  display.style.display = 'none';
  input.style.display = 'inline-block';
  input.focus();

  input.onblur = function () {
    const newName = input.value.trim();
    if (newName.length > 0 && newName !== display.textContent) {
      // CSRF 토큰 정보 읽기
      const csrf = getCsrfHeader();

      // AJAX로 네임 저장 요청
      fetch('/mypage/updateName', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          [csrf.headerName]: csrf.token  // 동적으로 키 넣기
        },
        body: JSON.stringify({ name: newName })
      })
        .then(response => {
          if (!response.ok) {
            throw new Error('네임 변경 실패됑ㅠㅠ');
          }
          return response.json();
        })
        .then(data => {
          if (data.success) {
            display.textContent = newName;
          } else {
            alert('네임 변경 실패ㅠㅠ 다시 시도해줭');
            input.value = display.textContent;
          }
        })
        .catch(error => {
          alert(error.message);
          input.value = display.textContent;
        })
        .finally(() => {
          display.style.display = 'inline-block';
          input.style.display = 'none';
        });
    } else {
      // 변경 없거나 빈값이면 원래대로
      display.style.display = 'inline-block';
      input.style.display = 'none';
    }
  }
}

// CSRF 토큰 + 헤더 키 동적으로 가져오기
function getCsrfHeader() {
  return {
    headerName: document.querySelector('meta[name="_csrf_header"]').getAttribute('content'),
    token: document.querySelector('meta[name="_csrf"]').getAttribute('content')
  };
}

// 로그아웃 처리 (index.html 이동)
function logout() {
  window.location.href = '/index.html';
}
