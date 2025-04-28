package com.example.relation.repository;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.relation.entity.team.Team;
import com.example.relation.entity.team.TeamMember;
import com.example.relation.repository.team.TeamMemberRepository;
import com.example.relation.repository.team.TeamRepository;

@SpringBootTest
public class TeamRepositoryTest {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Test
    public void insertTest() {

        // 팀(부모) 정보 삽입
        Team team = teamRepository.save(Team.builder().teamName("Team2").build());

        // 회원(자식) 정보 삽입
        // teamMemberRepository.save(TeamMember.builder().userName("user1").team(team).build());
    }

    @Test
    public void insertTest2() {

        Team team = teamRepository.findById(1L).get();
        teamMemberRepository.save(TeamMember.builder().userName("user2").team(team).build());
    }

    @Test
    public void readTest1() {
        // 팀 조회
        Team team = teamRepository.findById(1L).get();

        // 맴버 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        System.out.println(team);
        System.out.println(teamMember);

    }

    @Test
    public void readTest2() {
        // 맴버의 팀정보 조회
        TeamMember teamMember = teamMemberRepository.findById(1L).get();

        // 객체 그래프 탐색
        System.out.println(teamMember);
        System.out.println(teamMember.getTeam());

    }

    @Test
    public void readTest3() {
        Team team = Team.builder().id(2L).build();

        List<TeamMember> list = teamMemberRepository.findByTeam(team);
        System.out.println(list);
        // [TeamMember(id=1, userName=user1), TeamMember(id=2, userName=user2)]
    }

    @Test
    public void updateTest() {
        // 1번 팀원의 팀을 > 2팀으로 변경
        TeamMember member = teamMemberRepository.findById(1L).get();
        Team team = teamRepository.findById(2L).get();

        member.setTeam(team);
        teamMemberRepository.save(member);// save : 저장, update 역할 가능
    }

    @Test
    public void deleteTest() {
        // 1번 팀 삭제
        // 무결성 제약조건(C##JAVA.FK9UBP79EI4TV4CRD0R9N7U5I6E)이 위배되었습니다- 자식 레코드가 발견되었습니다
        // teamRepository.deleteById(1L);

        // 해결
        // 1. 삭제하려고 하는 팀의 팀원들을 다른 팀으로 이동시키거나 null 값 지정(아래 코드 ↓)
        // 2. 자식 삭제 후 부모 삭제

        TeamMember member = teamMemberRepository.findById(2L).get();
        Team team = teamRepository.findById(2L).get();
        member.setTeam(team);
        teamMemberRepository.save(member);

        teamRepository.deleteById(1L);

    }

    // --------------------------
    // 양방항 관계 : @OneToMany
    // : 쌍방향x, 단방향 2개
    // --------------------------

    // 팀 -> 회원 정보 조회
    // @Transactional
    @Test
    public void readBiTest1() {
        // 팀 찾기
        Team team = teamRepository.findById(2L).get();
        System.out.println(team);

        // 객체 그래프 탐색
        // JPA의 LazyInitializationException : 현재 "member"를 가지고 오지 못함.
        team.getMembers().forEach(member -> System.out.println(member));

    }

    // --------------------------
    // 양방항 일 때 쓸 수 있는 기능: Cascade(영속성 정의란?)
    // => teamMemberRepository.save(member) X
    // 해당 코드를 사용하지 않고, 자식 추가 후 부모 저장하니 자식도 들어감.
    // --------------------------
    @Test
    public void insertTest3() {
        Team team = Team.builder().teamName("team3").build();
        TeamMember member = TeamMember.builder().userName("홍길동").team(team).build();
        team.getMembers().add(member);

        teamRepository.save(team);
    }

    @Test
    public void deleteTest2() {
        // 부모, 자식 둘 다 삭제됨.
        // deleteTest()와 비교
        teamRepository.deleteById(3L);

    }

    @Test
    public void findByMemberEqualsTeamTest() {
        List<Object[]> result = teamMemberRepository.findByMemberEqualsTeam(2L);
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }

}
