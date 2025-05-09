package com.example.security.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
// 무조건 인증된 사람은 user로 통해 관리함.
public class ClubAuthMemberDTO extends User {

    private String email;
    private String name;
    private boolean fromSocial;// 회원인지 소셜로 로그인했는지

    // username 은 id 개념으로 사용
    public ClubAuthMemberDTO(String username, String password, boolean fromSocial,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.fromSocial = fromSocial;

    }

}
