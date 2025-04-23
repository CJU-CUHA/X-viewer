package org.example.xviewer.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor  // final 필드가 있는 생성자를 자동으로 생성해주는 Lombok 어노테이션. 이를 통해 AuthenticationManager를 주입받습니다.
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;  // 인증을 처리하는 AuthenticationManager 객체

    // 클라이언트에서 보낸 로그인 정보(username, password)를 추출하고 인증을 시도하는 메소드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 클라이언트 요청에서 username과 password를 추출
        // obtainUsername()과 obtainPassword()는 UsernamePasswordAuthenticationFilter에서 제공되는 메소드
        // 클라이언트가 전송한 로그인 정보를 얻기 위해 호출
        String username = obtainUsername(request);  // HTTP 요청에서 username 추출
        String password = obtainPassword(request);  // HTTP 요청에서 password 추출

        // Spring Security에서 username과 password를 검증하려면 AuthenticationToken 객체를 사용해야 함
        // UsernamePasswordAuthenticationToken은 인증 시 사용되는 token 객체로, 클라이언트의 인증 정보를 포함
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);

        // AuthenticationManager를 사용하여 인증 토큰을 넘겨서 인증을 시도합니다.
        // 인증이 성공하면 authentication 객체가 반환되고, 실패하면 AuthenticationException이 발생합니다.
        return authenticationManager.authenticate(authToken);
    }

    // 로그인 성공 시 실행되는 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        // 인증이 성공하면 호출됩니다.
        // 이곳에서 JWT를 발급하고, 사용자 정보를 Response에 담아 클라이언트에 반환할 수 있습니다.
        // 예를 들어, "Authorization" 헤더에 JWT를 담아 응답할 수 있습니다.
    }

    // 로그인 실패 시 실행되는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        // 인증 실패 시 호출됩니다.
        // 실패한 이유를 응답으로 반환하거나, 실패 메시지를 로그에 남기는 등의 작업을 할 수 있습니다.
        // 예: HTTP 401 Unauthorized 응답을 반환하거나, 에러 메시지를 클라이언트에게 전달합니다.
    }
}
