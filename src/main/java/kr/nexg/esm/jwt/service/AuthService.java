package kr.nexg.esm.jwt.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
 
//    public String encodePassword(String rawPassword) {
//        try {
//            // Compute SHA-256 hash
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            byte[] encodedhash = digest.digest(
//                    rawPassword.getBytes(StandardCharsets.UTF_8));
//
//            // Convert to hexadecimal format
//            StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
//            for (byte b : encodedhash) {
//                String hex = Integer.toHexString(0xff & b);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//
//            return hexString.toString();
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return null; // Handle the exception appropriately in your application
//        }
//    }
    
    @Transactional
    public TokenVo login(AuthVo authVo) {
    	
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
    	
//    	System.out.println("pwd : "+authVo.getPwd());
//    	String encodedPassword = passwordEncoder.encode(authVo.getPwd());
//    	System.out.println("encodedPassword : "+encodedPassword);
    	
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authVo.getLogin(), authVo.getPwd());
 
        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
 
        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenVo tokenInfo = jwtTokenProvider.generateToken(authentication);
 
        return tokenInfo;
    }
}
