package com.example.relation.entity.sports;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.example.relation.entity.BaseEntity;

// sports회원과 rocker 간 관계 = 1:1

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity

@Getter
@Setter
@ToString(exclude = "locker")
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class SportsMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;

    // 즉시로딩을 지연로딩으로 전환
    @OneToOne(fetch = FetchType.LAZY)
    private Locker locker;

}
