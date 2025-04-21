package com.example.relation.repository.team;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.relation.entity.cascasde.Child;
import com.example.relation.entity.sports.Locker;
import com.example.relation.entity.team.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
