package com.example.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices.RememberMeTokenAlgorithm;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.security.security.CustomLoginSuccessHandler;

@Configuration // 환경설정 파일
@EnableWebSecurity // 해당 환경 설정 파일에 저장된 내용으로 web-security 활성화
public class SecurityConfig {

    private final RememberMeServices rememberMeServices;

    SecurityConfig(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                // .authorizeHttpRequests(authorize -> authorize
                                // .requestMatchers("/", "/sample/guest").permitAll() // .permitAll():
                                // // 8080,guest 다 열어라
                                // .requestMatchers("/sample/member").hasRole("USER") // hasRole: ROLE 있다면
                                // // 권한 줌
                                // .requestMatchers("/sample/admin").hasRole("ADMIN")
                                // .anyRequest().authenticated())

                                // // .formLogin(Customizer.withDefaults()); // Customizer.withDefaults()

                                // .formLogin(login -> login.loginPage("/member/login").permitAll())
                                // .oauth2Login(Customizer.withDefaults());

                                // // 로그인 성공 시에도 가야할 장소를 정해야 함. 권한에 따라 다르게 할 것.
                                // http.logout(logout -> logout
                                // .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout")) // 어떤경로로
                                // 로그아웃할지
                                // .logoutSuccessUrl("/")); // 로그아웃 성공 시 주소

                                // return http.build();

                                .authorizeHttpRequests(authorize -> authorize
                                                .requestMatchers("/css/**", "/js/**", "/image/**").permitAll()
                                                .anyRequest().permitAll())

                                .formLogin(login -> login.loginPage("/member/login")
                                                .successHandler(successHandler())
                                                .permitAll())
                                .oauth2Login(login -> login.successHandler(successHandler()));
                http.logout(logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                                .logoutSuccessUrl("/"));

                http.rememberMe(remember -> remember.rememberMeServices(rememberMeServices));

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
        @Bean
        CustomLoginSuccessHandler successHandler() {
                return new CustomLoginSuccessHandler();
        }

        @Bean
        RememberMeServices rememberMeServices(UserDetailsService userDetailsService) {
                RememberMeTokenAlgorithm encodingAlgorithm = RememberMeTokenAlgorithm.SHA256;
                TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("myKey",
                                userDetailsService, encodingAlgorithm);
                rememberMeServices.setMatchingAlgorithm(RememberMeTokenAlgorithm.MD5);
                rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7);
                return rememberMeServices;
        }
}
