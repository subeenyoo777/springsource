package com.example.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todo.entity.ToDo;

public interface TodoRepository extends JpaRepository<ToDo, Long> {

    // select 문을 대신할 메서드 생성
    // 완료 목록 추출

    List<ToDo> findByCompleted(boolean completed);

    // 중요/안중요 목록 추출
    List<ToDo> findByImportanted(boolean importanted);
}
