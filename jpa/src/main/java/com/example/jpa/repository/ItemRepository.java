package com.example.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.example.jpa.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    // query문에 별칭을 줘야 한다.

    // public class Item{..} => entity 명 기준
    // @Table(name = "JPA_ITEM")

    @Query("SELECT count(ji), sum(ji.price), avg(ji.price), max(ji.price), min(ji.price) FROM Item ji")

    List<Object[]> aggreate();

}
