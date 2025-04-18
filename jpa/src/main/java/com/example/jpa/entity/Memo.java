package com.example.jpa.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
// import lombok.Setter; //기본적으로 직접적으로 열지는 않길 권장.
import lombok.ToString;

@Getter
// @Setter// 따로 메소드를 만들기 권장
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

// db 기준으로
// 번호(mno), 내용(memo_text, 200), 생성날짜(created_text), 수정날짜(updated_date)
// mno = 자동증가, pk
// 나머지 필드 not null(NN)

@EntityListeners(value = AuditingEntityListener.class)

@Entity
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mno;

    @Column(length = 200, nullable = false)
    private String memoText;

    @Column(nullable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime updatedDate;

    // setter 대신에 만드는 메서드
    public void changeMemoText(String memoText) {
        this.memoText = memoText;

    }

}
