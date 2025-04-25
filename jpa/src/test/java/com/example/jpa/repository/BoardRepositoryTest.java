package com.example.jpa.repository;

import java.util.stream.LongStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Board;

@SpringBootTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void queryMethodTest() {
        // System.out.println(boardRepository.findByWriter("user4"));
        // [Board(bno=4, title=board Title4, content=BOARD CONTENT4, writer=user4...]

        // System.out.println(boardRepository.findByTitle("board Title1"));
        // [Board(bno=1, title=board Title1, content=BOARD CONTENT1, writer=user1]

        // System.out.println(boardRepository.findByWriterStartingWith("user"));//
        // [user%]
        // System.out.println(boardRepository.findByWriterEndingWith("user")); //
        // [%user]
        System.out.println(boardRepository.findByWriterContaining("user"));//
        // [%user%]

        // System.out.println(boardRepository.findByWriterContainingAndContentContaining("5",
        // "9"));
        // System.out.println(boardRepository.findByWriterContainingOrContentContaining("5",
        // "9"));
        // System.out.println(boardRepository.findByBnoGreaterThan(5L));

        // System.out.println(boardRepository.findByBnoGreaterThan(0L));

        // System.out.println(boardRepository.findByBnoBetween(5L, 10L));
    }

    // CRUD
    @Test
    public void insertTest() {
        LongStream.rangeClosed(1, 10).forEach(i -> {
            Board board = Board.builder()
                    .title("board Title" + i)
                    .content("BOARD CONTENT" + i)
                    .writer("user" + i)
                    .build();

            boardRepository.save(board);

        });
    }

    @Test
    public void updateTest() {
        Board board = boardRepository.findById(1L).get();
        board.setContent("Content Update");
        boardRepository.save(board);
    }

    @Test
    public void readtTest() {
        Board board = boardRepository.findById(1L).get();
        System.out.println(board);
    }

    @Test
    public void listTest() {
        boardRepository.findAll().forEach(board -> System.out.println(board));
    }

    @Test
    public void deleteTest() {
        // boardRepository.deleteAll();
        boardRepository.deleteById(10L);
    }
}
