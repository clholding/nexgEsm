package kr.nexg.esm.jwt.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.default1.mapper.DefaultMapper;
import kr.nexg.esm.jwt.dto.AuthVo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	DefaultMapper defaultMapper;
    
	@Autowired
	private HttpServletRequest request;
	
    public long getMinDiff(String tarTime, String curTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime d1 = LocalDateTime.parse(tarTime, formatter);
        LocalDateTime d2 = LocalDateTime.parse(curTime, formatter);
        
        Duration duration = Duration.between(d1, d2);
        
        if (duration.toDays() > 0) {
            return duration.toDays() * 24 * 60;
        } else {
            return duration.toMinutes();
        }
    }
	
	public int updateUserInfo(AuthVo authVo, String mode, int loginStatus) {

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
        if("init".equals(mode) && loginStatus != 13) {
    		authVo.setFailcount(0);
    		defaultMapper.updateFailCount(authVo);
    		defaultMapper.updateLoginTime(authVo);
        }
        
        if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
        	
            long minutesDiff = getMinDiff(authVo.getLastLogin(), authVo.getCurTime());
            
            log.info("minutesDiff : "+minutesDiff);
        	if(minutesDiff >= authVo.getBlockingTime()) {
        		
        		authVo.setFailcount(0);
        		defaultMapper.updateFailCount(authVo);
        		defaultMapper.updateLoginTime(authVo);
//				setSyslog(authVo.getLogin(), "4");
				loginStatus = 4;
        		
        	}else {
        		if("delay".equals(authVo.getAdminFailAction())) {
//    				setSyslog(authVo.getLogin(), "5");
    				loginStatus = 5;
        		}else {
//    				setSyslog(authVo.getLogin(), "11");
    				loginStatus = 11;
        		}
        	}
        }else {
        	if("update".equals(mode)){
        		
        		authVo.setFailcount(authVo.getFailcount()+1);
        		defaultMapper.updateFailCount(authVo);
        		defaultMapper.updateLoginTime(authVo);
        	}
        }
        
        return loginStatus;
	}
	
	public int failLoginProcess(AuthVo authVo, int loginStatus) {
		
		loginStatus = updateUserInfo(authVo, "update", loginStatus);
//		setSyslog(authVo.getLogin(), "3");
		
		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
			if("delay".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo.getLogin(), "5");
				loginStatus = 5;
			}else if("lock".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo.getLogin(), "11");
				loginStatus = 11;
			}
		}
		
		return loginStatus;
	}
	
	public int checkLoginURL(AuthVo authVo, int loginStatus) {
		
		String clientIp = ClientIpUtil.getClientIP(request);
		log.info("clientIp ============== "+clientIp);
		
		Map<String, Object> map = defaultMapper.getUserURLs(authVo);
		if(map != null) {
			
			if(map.get("url1").equals(clientIp) || map.get("url2").equals(clientIp) || map.get("url3").equals(clientIp)) {
				
				loginStatus = 23;
//				setSyslog(loginId, "23");
			}
		}
		
		return loginStatus;
	}
	
	public boolean isSuperUser(int role) {
		return (role == 1 ? true: false);
	}
	public int checkLoginDelay(AuthVo authVo, int loginStatus) {
		
		String clientIp = ClientIpUtil.getClientIP(request);
		
		if(!"1".equals(authVo.getActive())) {
			loginStatus = 21;
//			setSyslog(authVo.getLogin(), "21");
		}
		
		loginStatus = checkLoginURL(authVo, loginStatus);
		
		if(authVo.getAllowIp1() != null && authVo.getAllowIp1().length() > 0) {
			if(!clientIp.equals(authVo.getAllowIp1())) {
				if(!clientIp.equals(authVo.getAllowIp2())) {
					loginStatus = 7;
//					setSyslog(authVo.getLogin(), "7");
				}
			}
		}
		
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
			long minutesDiff = getMinDiff(authVo.getLastLogin(), authVo.getCurTime());
			
			if(minutesDiff >= authVo.getBlockingTime()) {
	    		authVo.setFailcount(0);
	    		defaultMapper.updateFailCount(authVo);
	    		defaultMapper.updateLoginTime(authVo);
	    		
	    		loginStatus = 4;
//	    		setSyslog(authVo.getLogin(), "4");
			}else {
	    		loginStatus = 5;
//	    		setSyslog(authVo.getLogin(), "5");
			}
		}else {
//			if() {
//	    		loginStatus = 22;
////	    		setSyslog(authVo.getLogin(), "22");
//			}
			
			if("0".equals(authVo.getStatus())) {
				
	    		authVo.setFailcount(0);
	    		defaultMapper.updateFailCount(authVo);
	    		defaultMapper.updateLoginTime(authVo);
	    		defaultMapper.updateUserData(clientIp, authVo.getLogin());
				loginStatus = 13;
				
			}else if("1".equals(authVo.getStatus())) {
				boolean isSuper = isSuperUser(authVo.getRole1());
				if(isSuper) {
					
				}else {
					
				}
			}else if("2".equals(authVo.getStatus())) {
				
			}
			
			
		}
		
		
		
		
		return loginStatus;
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
		
		AuthVo authVo = defaultMapper.selectLogin(loginId);
		int loginStatus = 0;
		
		if (authVo == null) {
			
			if(authVo.getLoginStatus() == 100) {
				loginStatus = 100;
//				setSyslog(loginId, "100");
			}else {
				loginStatus = 8;
//				setSyslog(loginId, "8");
			}
			
			throw new BadCredentialsException(loginId + " -> 사용자가 없습니다.");
		}
		
		if(authVo.getLoginStatus() >= 100) {
			loginStatus = 100;
		}
		
		loginStatus = checkLoginDelay(authVo, loginStatus);
		
		log.info("loginStatus : "+loginStatus);
//		if(loginStatus == 3) {
//			failLoginProcess(authVo, loginStatus);
//		}else {
//			failLoginProcess(authVo, loginStatus);
//		}
		
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