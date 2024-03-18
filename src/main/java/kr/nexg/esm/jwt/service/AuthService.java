package kr.nexg.esm.jwt.service;

import java.security.NoSuchAlgorithmException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kr.nexg.esm.common.util.SHA256;
import kr.nexg.esm.default1.mapper.DefaultMapper;
import kr.nexg.esm.devices.controller.DevicesController;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
 	
    @Transactional
    public TokenVo logincheck(AuthVo authVo) {
    	
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
//    	System.out.println("pwd : "+authVo.getPwd());
//    	String encodedPassword = passwordEncoder.encode(authVo.getPwd());
//    	System.out.println("encodedPassword : "+encodedPassword);
    	
//    	SHA256 sha256 = new SHA256();
//    	String pwd = "";
//    	
//		try {
//			pwd = sha256.encrypt(authVo.getPwd());
//		} catch (NoSuchAlgorithmException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	log.info("pwd : "+pwd);
//    	log.info("encodedPassword : "+passwordEncoder.encode(pwd));
    	
    	
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authVo.getLogin(), authVo.getPwd());
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenVo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
}
