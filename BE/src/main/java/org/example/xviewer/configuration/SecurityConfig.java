package org.example.xviewer.configuration;


import lombok.RequiredArgsConstructor;
import org.example.xviewer.security.jwt.LoginFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration  // 이 클래스가 스프링의 설정 클래스임을 나타냅니다.
@RequiredArgsConstructor  // Lombok의 어노테이션으로, final로 선언된 필드에 대한 생성자를 자동으로 생성합니다.
@EnableWebSecurity
public class SecurityConfig {
    private final AuthenticationConfiguration authenticationConfiguration;  // AuthenticationManager를 설정하기 위한 객체

    // AuthenticationManager를 빈으로 등록하는 메서드
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();  // Spring Security에서 인증 매니저를 가져옵니다.
    }

    // BCryptPasswordEncoder를 빈으로 등록하는 메서드 (비밀번호 암호화용)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();  // 비밀번호를 암호화하는 데 사용되는 빈을 생성합니다.
    }

    // HTTP 보안 설정을 담당하는 필터 체인을 설정합니다.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CSRF 보호 비활성화 (API에서 사용하는 경우에 보안상 CSRF를 비활성화하는 경우가 많습니다)
        http.csrf((auth) -> auth.disable());

        // 기본 로그인 방식 비활성화 (form-login 사용을 비활성화)
        http.formLogin((auth) -> auth.disable());

        // 기본 HTTP Basic 인증 방식을 비활성화
        http.logout((auth) -> auth.disable());

        // 커스텀 로그인 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
        // LoginFilter는 인증 처리를 위한 커스텀 필터로, AuthenticationManager를 인자로 받습니다.
        http.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)), UsernamePasswordAuthenticationFilter.class);

        // 경로별 접근 권한 설정
        http.authorizeHttpRequests((authorizeHttpRequests) ->
                authorizeHttpRequests
                        .requestMatchers("/api/user/join").permitAll() // 회원가입 요청 모두 접근 허가
                        .requestMatchers("/api/user/login").permitAll() // 로그인 요청 모두 접근 허가
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**").permitAll()
                        .anyRequest().permitAll()// 그 외 모든 요청 인증처리
        );

        // 세션 관리 설정: Stateless로 설정하여 세션을 사용하지 않도록 합니다.
        // JWT 인증 방식에서 세션을 사용하지 않도록 하기 위해 STATELESS로 설정합니다.
        http.sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 설정한 보안 체인을 반환하여 적용합니다.
        return http.build();
    }
}
