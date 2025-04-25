package com.example.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.jpa.entity.Board;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    // // = ..WHERE B.WRITER = '~';
    // List<Board> findByWriter(String writer);

    // // .. WHERE B.title = '~';
    // List<Board> findByTitle(String title);

    // // ..WHERE like "user%"
    // List<Board> findByWriterStartingWith(String writer);

    // // ..WHERE like "%user"
    // List<Board> findByWriterEndingWith(String writer);

    // // ..WHERE like "%user%"
    // List<Board> findByWriterContaining(String writer);

    // // b.WRITER like '%user%' or b.content like '%내용%'
    // List<Board> findByWriterContainingOrContentContaining(String writer, String
    // content);

    // // b.WRITER like '%user%' and b.content like '%내용%'
    // List<Board> findByWriterContainingAndContentContaining(String writer, String
    // content);

    // // bno > 5 게시물 조회
    // List<Board> findByBnoGreaterThan(Long bno);

    // // bno > 0 order by bno desc... 따로 페이지 나누는 걸 쓰기에 잘 사용하지 않음
    // List<Board> findByBnoGreaterThanOrderByBnoDesc(Long bno);

    // // bno >= 5 and bno <= 10
    // // where bno between 5 and 10
    // List<Board> findByBnoBetween(Long start, Long end);

    // ----------------------------------------
    // @query
    // : from의 기준은 entity! db 테이블명 아님
    // ->Borad b 는 entity 기준
    // ----------------------------------------
    @Query("select b from Board b where b.writer = ?1")
    List<Board> findByWriter(String writer);

    @Query("select b from Board b where b.writer like ?1%")
    List<Board> findByWriterStartingWith(String writer);

    @Query("select b from Board b where b.writer like %?1%")
    List<Board> findByWriterContaining(String writer);

    // @Query("select b from Board b where b.bno > ?1%") entity 기준이 아니라
    // sql 구문형식 사용 가능(실제 sql구문... from b 같은 거 안됨)
    // @NativeQuery(value = "select * from Board b where b.bno > ?1%") // 이 형식도 가능
    @Query(value = "select * from Board b where b.bno > ?1%", nativeQuery = true) // 어떻게? = native 기준(=db테이블명써야 해)
    List<Board> findByBnoGreaterThan(Long bno);// 잘 쓰진 않음.

}
