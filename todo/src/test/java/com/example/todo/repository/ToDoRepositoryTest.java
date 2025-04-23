package com.example.todo.repository;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.todo.entity.ToDo;

@SpringBootTest
public class ToDoRepositoryTest {

    @Autowired
    private TodoRepository toDoRepository;

    // test메서드는 무조건 void타입, junit 에서 Test 실행
    @Test
    public void testInsert() {

        IntStream.range(1, 10).forEach(i -> {
            ToDo todo = new ToDo();
            todo.setContent("강아지산책" + i);
            toDoRepository.save(todo);
        });

    }

    @Test
    public void testRead() {
        toDoRepository.findAll().forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testRead2() {
        // 완료 목록 추출
        toDoRepository.findByCompleted(true).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testRead3() {
        // 중요 목록 추출
        toDoRepository.findByImportanted(false).forEach(todo -> System.out.println(todo));
    }

    @Test
    public void testDelete() {
        toDoRepository.deleteById(9L);
    }

    @Test
    public void TestUpdate() {
        ToDo todo = toDoRepository.findById(8L).get();
        todo.setCompleted(true);

        toDoRepository.save(todo);
    }

}
