package com.example.movie.dto;

import java.time.LocalDateTime;

import com.example.movie.entity.MemberRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberDTO {
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    private Long mid;

    private String email;

    private String password;

    private String nickName;

    private MemberRole memberRole;

}
