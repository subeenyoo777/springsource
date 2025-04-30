package com.example.board.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.ToString;

@ToString(exclude = { "member", "replies" })
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Table(name = "BOARDTBL")
@Entity
public class Board extends BaseEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    // 게시글에 달려있는 댓글 정보를 조회
    @Builder.Default
    @OneToMany(mappedBy = "board") // OneToMany는 원래 lazy이다
    private List<Reply> replies = new ArrayList<>();
}
