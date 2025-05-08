package com.example.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.board.entity.Board;
import com.example.board.entity.Reply;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // bno 를 기준으로 삭제
    // DELETE FROM reply WHERE board_bno = 1

    @Modifying // delete,update 무조건 사용해야 함
    @Query("DELETE FROM Reply r WHERE r.board.bno = :bno")
    void deleteByBoardBno(Long bno);

    // 특정 글 조회 시 달려 있는 댓글 모둑 가져오기
    List<Reply> findByBoardOrderByRno(Board board);
}
