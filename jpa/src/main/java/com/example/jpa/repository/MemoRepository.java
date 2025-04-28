package com.example.jpa.repository;

import java.lang.reflect.Member;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import com.example.jpa.entity.Memo;

public interface MemoRepository extends JpaRepository<Memo, Long>, QuerydslPredicateExecutor<Member> {

    // where mno < 5
    List<Memo> findByMnoLessThan(Long mno);

    // where mno < 10 order by mno desc
    List<Memo> findByMnoLessThanOrderByMnoDesc(Long mno);

    // where memoText like '%memo%'
    List<Memo> findByMemoTextContaining(Long memoText);

}
