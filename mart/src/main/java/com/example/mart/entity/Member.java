package com.example.mart.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString(exclude = "orders")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "MART_MEMBER") // 선택사항
@Entity // 테이블과 관련됨, 필수사항

public class Member extends BaseEntity {
    // 회원: 회원번호(pk), 이름, 우편번호, 주소, 상세주소
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;
    private String name;

    private String zipcode;
    private String city;
    private String street;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

}
