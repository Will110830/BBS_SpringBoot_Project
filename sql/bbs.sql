-- 코드 --

-- 시퀀스 생성
CREATE SEQUENCE BOARD_SEQ START WITH 1 INCREMENT BY 1;

-- 게시판 테이블 생성
CREATE TABLE BOARD (
    BOARD_ID NUMBER(11),                        -- 게시글 ID (PK)
    TITLE VARCHAR2(30),                         -- 제목
    CONTENT CLOB,                               -- 내용
    WRITER VARCHAR2(11) NOT NULL,               -- 작성자
    CREATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP,  -- 작성 날짜
    UPDATED_AT TIMESTAMP DEFAULT SYSTIMESTAMP   -- 수정 날짜
);
--기본키
alter table BOARD add Constraint BOARD_ID_PK primary key (BOARD_ID);

CREATE OR REPLACE TRIGGER BOARD_ID_TRG
BEFORE INSERT ON BOARD
FOR EACH ROW
BEGIN
  IF :NEW.BOARD_ID IS NULL THEN
    SELECT BOARD_SEQ.NEXTVAL INTO :NEW.BOARD_ID FROM DUAL;
  END IF;
END;


CREATE TABLE board_comment (
    comment_id NUMBER PRIMARY KEY,           -- 댓글 고유 ID
    board_id NUMBER NOT NULL,                -- 글번호
    member_id NUMBER(19,0) NOT NULL,         -- 댓글 작성자 ID (참조용)
    content VARCHAR2(4000) NOT NULL,         -- 댓글 내용
    created_at DATE DEFAULT SYSDATE,         -- 작성일자
    updated_at DATE DEFAULT SYSDATE          -- 수정일자
);

-- FK 연결
ALTER TABLE board_comment
ADD CONSTRAINT fk_comment_board
FOREIGN KEY (board_id) REFERENCES board(board_id);

ALTER TABLE board_comment
ADD CONSTRAINT fk_member
FOREIGN KEY (member_id) REFERENCES member(id);

SELECT * from MEMBER;
-- 시퀀스
CREATE SEQUENCE seq_board_comment START WITH 1 INCREMENT BY 1;

-- 트리거
CREATE OR REPLACE TRIGGER trg_board_comment_pk
BEFORE INSERT ON board_comment
FOR EACH ROW
BEGIN
  SELECT seq_board_comment.NEXTVAL INTO :NEW.comment_id FROM dual;
END;
/
COMMIT;

