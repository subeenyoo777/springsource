package com.example.relation.repository.cascade;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.relation.entity.cascasde.Parent;

public interface ParentRepository extends JpaRepository<Parent, Long> {

}
