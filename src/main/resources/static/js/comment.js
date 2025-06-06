document.addEventListener('DOMContentLoaded', function () {
  // (1) CSRF 토큰 읽기
  const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

  const boardId = document.getElementById('board-id').value;
  const loginMemberId = document.getElementById('login-member-id')?.value || null;
  let currentPage = 0;

  const commentContent = document.getElementById('comment-content');
  const commentList = document.getElementById('comment-list');
  const pagination = document.getElementById('comment-pagination');
  const addBtn = document.getElementById('add-comment-btn');

  // 댓글 목록 조회
  function loadComments(page = 0) {
    fetch(`/comments?boardId=${boardId}&page=${page}`, {
      headers: { [csrfHeader]: csrfToken }
    })
      .then(res => {
        if (!res.ok) throw new Error('댓글 불러오기 실패');
        return res.json();
      })
      .then(data => {
        currentPage = page;
        renderComments(data.content || []);
        renderPagination(data);
      })
      .catch(err => {
        console.error(err);
        commentList.innerHTML = '<p>댓글을 불러오는 중 오류가 발생했습니다.</p>';
      });
  }

  // 댓글 등록
  addBtn.addEventListener('click', function () {
    if (!loginMemberId) {
      alert('로그인 후 댓글을 작성할 수 있습니다.');
      return;
    }

    const content = commentContent.value.trim();
    if (!content) return alert('내용을 입력하세요.');

    fetch('/comments', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify({ content, boardId })
    })
      .then(res => {
        if (!res.ok) {
          if (res.status === 401) {
            alert('로그인이 필요합니다.');
          } else {
            alert('댓글 등록 실패');
          }
          throw new Error('댓글 등록 실패');
        }
        return res.json();
      })
      .then(() => {
        loadComments(currentPage);
      })
      .finally(() => commentContent.value = '');
  });

  // 댓글 렌더링
  function renderComments(comments) {
    commentList.innerHTML = '';
    comments.forEach(comment => {
      const div = document.createElement('div');
      div.className = 'comment-item';
      const safeContent = JSON.stringify(comment.content); // 작은따옴표 안전 처리

      div.innerHTML = `
        <p><strong>${comment.writerName}</strong> (${comment.createdAt?.substring(0, 10)})</p>
        <p id="content-${comment.id}">${comment.content}</p>
        ${comment.writerId == loginMemberId ? `
          <button onclick="editComment(${comment.id}, ${safeContent})">수정</button>
          <button onclick="deleteComment(${comment.id})">삭제</button>
        ` : ''}
        <hr/>
      `;
      commentList.appendChild(div);
    });
  }

  // 페이징 렌더링
  function renderPagination(data) {
    pagination.innerHTML = '';
    const { totalPages, number: current } = data;

    if (current > 0) {
      const prev = document.createElement('button');
      prev.innerText = '이전';
      prev.onclick = () => loadComments(current - 1);
      pagination.appendChild(prev);
    }

    for (let i = 0; i < totalPages; i++) {
      const btn = document.createElement('button');
      btn.innerText = i + 1;
      if (i === current) btn.disabled = true;
      btn.onclick = () => loadComments(i);
      pagination.appendChild(btn);
    }

    if (current < totalPages - 1) {
      const next = document.createElement('button');
      next.innerText = '다음';
      next.onclick = () => loadComments(current + 1);
      pagination.appendChild(next);
    }
  }

  // 전역 함수로 등록
  window.editComment = function (id, oldContent) {
    const newContent = prompt('댓글 수정:', oldContent);
    if (newContent === null || newContent.trim() === '') return;

    fetch(`/comments/${id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
        [csrfHeader]: csrfToken
      },
      body: JSON.stringify({ content: newContent })
    })
      .then(res => {
        if (!res.ok) {
          if (res.status === 403) {
            alert('권한이 없습니다.');
          } else {
            alert('댓글 수정 실패');
          }
          throw new Error('수정 실패');
        }
        loadComments(currentPage);
      });
  };

  window.deleteComment = function (id) {
    if (!confirm('삭제하시겠습니까?')) return;

    fetch(`/comments/${id}`, {
      method: 'DELETE',
      headers: { [csrfHeader]: csrfToken }
    })
      .then(res => {
        if (!res.ok) {
          if (res.status === 403) {
            alert('권한이 없습니다.');
          } else {
            alert('댓글 삭제 실패');
          }
          throw new Error('삭제 실패');
        }
        loadComments(currentPage);
      });
  };

  // 최초 로드
  loadComments(0);
});