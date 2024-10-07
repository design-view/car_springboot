package com.green.car.service;

import com.green.car.dto.TokenDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtTokenProvider {
    private final Key key;

    //생성자 secretKey를 디코딩하여 JWT 서명에 사용할 키를 생성
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
        //주입된 secretKey를 BASE64 디코딩하여 바이트 배열로 변환
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        //바이트배여을 사용하여 HmacShaKey를 생성 --> JWT서명을 생성하고 검증
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    //유저 정보를 가지고 AccessToken, RefreshToken을 생성하는 메소드
    public TokenDto generateToken(Authentication authentication){
        //권한 가져오기  USER,ADMIN
        String authorities = authentication.getAuthorities().stream()
                //객체에서 권한을 추출해서 문자열 컬렉션을 추출
                .map(GrantedAuthority::getAuthority)
                //하나의 문자열로 리턴
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();  //현재데이트객체를 밀리초로 리턴
        Date accessTokenExpiresIn = new Date(now+86400000);   //현제시간에 + 1일 86400000밀리초 = 하루
        //Access Token생성
        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                //사용자의 권한 정보
                .claim("roll",authorities)
                //JWT만료시간 설정(하루)
                .setExpiration(accessTokenExpiresIn)
                //서명추가하기 서명은 JWT의 무결성을 보장, 위조방지용
                //HS256알고리즘을 사용하여 비밀키를 이용해 서명을 생성
                .signWith(key, SignatureAlgorithm.HS256)
                //문자열로 반환
                .compact();
        //Refresh Token생성
        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now + 86400000))
                .signWith(key,SignatureAlgorithm.HS256)
                .compact();
        return TokenDto.builder()
                //토큰의 타입을 나타내는 속성 Bearer토큰을 사용
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    //JWT 복호화하여 토큰에 들어있는 정보를 꺼내는 메소드
    //Authentication리턴
    //Authentication 스프링 시큐리티에서 인증된 정보를 담고있는 인터페이스입니다.
    //accessToken을 전달받아 Authentication객체를 리턴
    public Authentication getAuthentication(String accessToken){
        //토큰 복호화
        Claims claims = parseClaims(accessToken);
        if(claims.get("roll")==null){
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }
        Collection<? extends GrantedAuthority> authorities =
                //"USER,ADMIN" --> { "USER","ADMIN"}
                //map(user-> dtoToEntity(user))
                Arrays.stream(claims.get("roll").toString().split(","))
                        //각각의 권한정보를 SimpleGrantedAuthority객체로 변환
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        //Authentication
        //
        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal,"",authorities);
    }
    //토큰 정보를 검증하는 메소드
    public boolean validateToken(String token){
        //Jwts.parserBuilder() : JWT 파싱하는 빌더를생성
        //setSigningKey(key) : 서명 키를 설정 JWT를 검증할때 사용
        //build() Jwtparser객체를 빌드함
        //parseClaimsJws(token) : 주어진 토큰을 파싱하여 서명을 확인하고 JWT본문 반환
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("유효하지 않는 JWT ",e);
        } catch (ExpiredJwtException e){
            log.info("만료된 JWT 토큰", e);
        } catch (UnsupportedJwtException e){
            log.info("입증되지않는 JWT 토큰",e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims가 비어있습니다.", e);
        }
        return false;

    }

    //주어진 accessToken을 해독하고 클래임객체를 리턴함.
    private Claims parseClaims(String accessToken){
        //Jwts.parserBuilder() JWT를 파싱하는 빌더를 생성
        //setSigningKey(key) : 서명 키를 설정 JWT검증시 사용
        //.parseClaimsJws(accessToken) 엑세스 토큰을 파싱해서 서명을 확인
        //.getBody() JWT본문을 얻습니다. Clalims를 반환 클레임은  JWT의 정보를 포함하고 있음
        try {
            return Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){
            return  e.getClaims();
        }

    }
}
