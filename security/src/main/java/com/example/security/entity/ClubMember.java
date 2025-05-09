package com.example.security.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ClubMember {

    @Id
    private String email;
    private String password;
    private String name;
    private boolean fromSocial;

    // [role 저장]
    // List : 중복된 Role
    // Set : 중복된 Role 담지 못하게
    // 한 사람에게 여러 role 부여됨 : admin, manager, member
    @ElementCollection(fetch = FetchType.LAZY) // 1:N 관계로 테이블 생성
    @Builder.Default
    private Set<ClubMemberRole> roleSet = new HashSet<>();

    public void addMemberRole(ClubMemberRole memberRole) {
        roleSet.add(memberRole);
    }
}
