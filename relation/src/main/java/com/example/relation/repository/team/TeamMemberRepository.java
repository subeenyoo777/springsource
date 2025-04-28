package com.example.relation.repository.team;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    // team 기준으로 member 찾기
    List<TeamMember> findByTeam(Team team);

    // team 기준으로 member 찾기 => 맴버, 팀정보 조회
    @Query("SELECT m,t FROM TeamMember m JOIN m.team t WHERE t.id=:id")
    List<Object[]> findByMemberEqualsTeam(Long id);

    // [TeamMember(id=1, userName=user1), Team(id=2, teamName=Team2)]
    // [TeamMember(id=2, userName=user2), Team(id=2, teamName=Team2)]

}
