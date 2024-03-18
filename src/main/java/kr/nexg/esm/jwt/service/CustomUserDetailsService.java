package kr.nexg.esm.jwt.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.default1.mapper.DefaultMapper;
import kr.nexg.esm.jwt.dto.AuthVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	DefaultMapper defaultMapper;

	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		AuthVo authVo = defaultMapper.selectLogin(loginId);
		
		if (authVo == null) {
			throw new BadCredentialsException(loginId + " -> 사용자가 없습니다.");
		}
		
		if(!"1".equals(authVo.getActive())) {
			authVo.setLoginStatus(21);
//			setSyslog(authVo.getLogin(), "21");
		}
		
		log.info("getLoginStatus : "+authVo.getLoginStatus());
		
		return createUserDetails(loginId, authVo);
	}

	// 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
	private UserDetails createUserDetails(String loginId, AuthVo authVo) {

		if (authVo == null) {
			throw new BadCredentialsException(loginId + " -> 사용자가 없습니다.");
		}

//		System.out.println("authVo.getUsername() : "+ authVo.getUsername());
//		System.out.println("authVo.getPassword() : "+ authVo.getPassword());
//		System.out.println("authVo.getGroupId() : "+ authVo.getGroupId());
		return User.builder()
				.username(authVo.getUsername())
				.password(authVo.getPassword())
                .roles(authVo.getGroupId()).build();
	}
}