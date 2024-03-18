package kr.nexg.esm.jwt.service;

import java.text.SimpleDateFormat;
import java.util.Date;
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

	public int getMinDiff() {
		int minDiff = 0;
		
		return minDiff;
	}
	
	public boolean updateUserInfo(AuthVo authVo, String mode) {

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
        if("init".equals(mode) && authVo.getLoginStatus() != 13) {
    		authVo.setFailcount(0);
    		defaultMapper.updateFailCount(authVo);
    		defaultMapper.updateLoginTime(authVo);
        }
        
        if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
        	
            int minutesDiff = 0;
            
        	if(minutesDiff >= authVo.getBlockingTime()) {
        		
        		authVo.setFailcount(0);
        		defaultMapper.updateFailCount(authVo);
        		defaultMapper.updateLoginTime(authVo);
//				setSyslog(authVo.getLogin(), "4");
				authVo.setLoginStatus(4);           		
        		
        	}else {
        		if("delay".equals(authVo.getAdminFailAction())) {
//    				setSyslog(authVo.getLogin(), "5");
    				authVo.setLoginStatus(5);
        		}else {
//    				setSyslog(authVo.getLogin(), "11");
    				authVo.setLoginStatus(11);        			
        		}
        	}
        }else {
        	if("update".equals(mode)){
        		
        		authVo.setFailcount(authVo.getFailcount()+1);
        		defaultMapper.updateFailCount(authVo);
        		defaultMapper.updateLoginTime(authVo);
        	}
        }
		return true;
	}
	public void failLoginProcess(AuthVo authVo) {
		
		updateUserInfo(authVo, "update");
//		setSyslog(authVo.getLogin(), "3");
		
		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
			if("delay".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo.getLogin(), "5");
				authVo.setLoginStatus(5);
			}else if("lock".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo.getLogin(), "11");
				authVo.setLoginStatus(11);
			}
		}
	}
	
	public boolean checkLoginDelay(AuthVo authVo) {
		
		return true;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		
		AuthVo authVo = defaultMapper.selectLogin(loginId);
		
		if (authVo == null) {
			throw new BadCredentialsException(loginId + " -> 사용자가 없습니다.");
		}
		
		checkLoginDelay(authVo);

		if(authVo.getLoginStatus() == 3) {
			failLoginProcess(authVo);
		}else {
			failLoginProcess(authVo);
		}
		
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