package com.example.jpa.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

//번호, user 아이디, 이름, 나이, 역할, 가입일자, 자기소개

//번호 > 자동증가, user 아이디 > uniqe, 역할 > ADMIM, USER

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@EntityListeners(value = AuditingEntityListener.class)
@Entity

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가
    private Long no;

    @Column(unique = true)
    private String userid;
    private String name;
    private int age;

    @Enumerated(EnumType.STRING)
    private RoleType rollType;

    @CreatedDate
    private LocalDateTime regDate;

    @Column(length = 2000)
    private String description;

    public enum RoleType {
        ADMIM, USER
    }
}
