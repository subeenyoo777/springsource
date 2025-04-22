package com.example.relation.entity.team;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 하나의 팀에는 여러 회원이 소속된다
@ToString(exclude = "members")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
public class Team {
    // id, name(팀명)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String teamName;

    @Builder.Default
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private List<TeamMember> members = new ArrayList<>();
}
// CascadeType.ALL : 모든 작업을 부모와 함께 하겠다. 모든 Cascade 옵션을 적용합니다.
// CascadeType.PERSIST : 엔티티를 영속화할 때, 연관된 엔티티도 함께 영속화합니다.
// CascadeType.REMOVE : 엔티티를 제거할 때, 연관된 엔티티도 함께 제거합니다.
// CascadeType.MERGE : 엔티티 상태를 병합할 때, 연관된 엔티티도 함께 병합합니다.
// CascadeType.REFRESH : 부모 엔티티를 Refresh하면, 연관된 엔티티도 함께 Refresh됩니다.
// CascadeType.DETACH : 부모 엔티티를 Detach하면, 연관된 엔티티도 함께 Detach됩니다
