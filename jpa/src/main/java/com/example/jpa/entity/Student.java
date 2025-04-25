package com.example.jpa.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EntityListeners(value = AuditingEntityListener.class)

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Table(name = "studenttbl")
@Entity // == database 테이블

public class Student {

    @Id // id number(19,0) not null,
    @SequenceGenerator(name = "student_seq_gen", sequenceName = "student_seq_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_seq_gen")

    // GeneratedValue: create sequence studentbl_seq start with 1 increment by 50

    private Long id; // id number(19,0) not null primary key (id),
    // @Column(name = "sname", length = 100, nullable = false, unique = true)
    // @Column(name = "sname", columnDefinition = "varchar2(20) not null unique")

    @Column(length = 20, nullable = false)
    private String name;

    // @Column(columnDefinition = "number(8,0)")
    // private int grade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Grade grade;

    @Column(columnDefinition = "varchar2(1) CONSTRAINT chk_gender CHECK (gender in ('M', 'F'))")
    private String gender;

    // springframework
    @CreationTimestamp // insert 시
    private LocalDateTime cDateTime; // 오라클: C_DATE_TIME(대문자 기준으로 _ 구분지음)

    @UpdateTimestamp
    // insert 시 + 데이터 수정할 때마다 시간자동저장
    private LocalDateTime uDateTime; // U_DATE_TIME

    // springframework
    @CreatedDate
    private LocalDateTime cDateTime2;
    @LastModifiedDate
    private LocalDateTime uDateTime2;

    // 임의가 아닌 정해진 값만 넣고 싶을 때 사용하는 어노테이션
    // (check)도 되나, insert ..하는 시점임. 그 전에 확인을 하고 싶다.

    // enum 정의, enum(상수 집합)
    public enum Grade {
        FRESHMAN, SOPHOMORE, JUNIOR, SENIOR

    }

}
