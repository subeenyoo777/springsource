package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.team.Team;
import com.example.jpa.entity.team.TeamMember;
import com.example.jpa.repository.team.TeamMemberRepository;
import com.example.jpa.repository.team.TeamRepository;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void insertTest() {

        Team team = teamRepository.save(Team.builder().teamName("Team1").build());

        teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());
    }

    @Test
    public void insertTest2() {

        Team team = teamRepository.findById(2L).get();

        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());
    }

}
