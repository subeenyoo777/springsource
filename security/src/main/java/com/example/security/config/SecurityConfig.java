package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration // 환경설정 파일
@EnableWebSecurity // 해당 환경 설정 파일에 저장된 내용으로 web-security 활성화
public class SecurityConfig {

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/", "/sample/guest").permitAll() // .permitAll():
                                                                                                   // 8080,guest 다 열어라
                                                .requestMatchers("/sample/member").hasRole("USER") // hasRole: ROLE 있다면
                                                                                                   // 권한 줌
                                                .requestMatchers("/sample/admin").hasRole("ADMIN")
                                                .anyRequest().authenticated())

                                // .formLogin(Customizer.withDefaults()); // Customizer.withDefaults()

                                .formLogin(login -> login.loginPage("/member/login").permitAll());
                // 로그인 성공 시에도 가야할 장소를 정해야 함. 권한에 따라 다르게 할 것.

                http.logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 어떤경로로 로그아웃할지
                                .logoutSuccessUrl("/")); // 로그아웃 성공 시 주소

                return http.build();
        }

        @Bean // = new 해서 Spring Container 에게 관리하라는 것.
        PasswordEncoder passwordEncoder() {

                return PasswordEncoderFactories.createDelegatingPasswordEncoder();

        }

        // @Bean
        // UserDetailsService users() {
        // UserDetails user = User.builder()
        // .username("user")
        // .password("{bcrypt}$2a$10$az5R5TaxzpF5OQ9c/QDqJuBqUkIkC0aSfaZmUFlOZ1KB.VNwaU6di")
        // .roles("USER") // ROLE_"권한명" 여기에 USER 지정함
        // .build();

        // UserDetails admin = User.builder()
        // .username("admin")
        // .password("{bcrypt}$2a$10$az5R5TaxzpF5OQ9c/QDqJuBqUkIkC0aSfaZmUFlOZ1KB.VNwaU6di")
        // .roles("USER", "ADMIN")
        // .build();

        // return new InMemoryUserDetailsManager(user, admin);
        // }
}
