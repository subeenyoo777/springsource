package com.example.relation.entity.team;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 회원은 단 하나의 팀에 소속된다.
@ToString(exclude = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class TeamMember {
    // id, name(회원명)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String userName;

    // @JoinColumn : 외래키 필드명 지정
    // 해당 기능 사용하지 않을 시 default : "table명_pk명"

    @JoinColumn(name = "team_id")
    @ManyToOne // 하나의 팀에는 여러명의 회원이 존재한다.
    private Team team;
}
