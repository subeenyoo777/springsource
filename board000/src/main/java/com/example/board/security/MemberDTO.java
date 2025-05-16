package com.example.board.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class MemberDTO {

    @Email(message = "이메일 형식을 확인해주세요")
    @NotBlank(message = "이메일은 필수 요소입니다")
    private String email;
    @NotBlank(message = "이름은 필수 요소입니다")
    private String name;
    @NotBlank(message = "비밀번호는 필수 요소입니다")
    private String password;

    private boolean fromSocial;

}
