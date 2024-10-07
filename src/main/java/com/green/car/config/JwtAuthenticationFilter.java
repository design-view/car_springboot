package com.green.car.config;

import com.green.car.service.JwtTokenProvider;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {
    private final JwtTokenProvider jwtTokenProvider;
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //1.request Header에서 JWT 추출
        String token = resolveToken((HttpServletRequest) request);
        //2.토큰 유효성 검사
        if(token != null && jwtTokenProvider.validateToken(token)){
            //토큰이 유효하며 토큰에서 Authentication객체를 가지고와서
            //SecurityContext저장
            Authentication authentication = jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request,response);
    }
    //request전달받아서 Header에 있는 토큰 추출리턴
    private String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        //text가 존재하며 Bearer로 시작하는지 체크
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;
    }
}
