package kr.nexg.esm.jwt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.default1.mapper.DefaultMapper;
import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {
	
	@Autowired
	DefaultMapper defaultMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	private HttpServletRequest request;
	
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
 	
    private int loginStatus = 0;
    
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
	
    public long getSecDiff(String tarTime, String curTime) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetDate = dateFormat.parse(tarTime);
        long seconds1 = targetDate.getTime() / 1000;

        Date currentDate = dateFormat.parse(curTime);
        long seconds2 = currentDate.getTime() / 1000;

        long secDiff = seconds2 - seconds1;
        return secDiff;
    }
    
	public boolean updateUserInfo(AuthVo authVo, String mode) {

        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
        if("init".equals(mode) && loginStatus != 13) {
    		authVo.setFailcount(0);
    		defaultMapper.updateFailCount(authVo);
    		defaultMapper.updateLoginTime(authVo);
    		
    		return true;
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
        		return false;
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
	
	public boolean failLoginProcess(AuthVo authVo) {
		
		updateUserInfo(authVo, "update");
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
		
		return true;
	}
	
	public boolean checkLoginURL(AuthVo authVo) {
		
		String clientIp = ClientIpUtil.getClientIP(request);
		log.info("clientIp ============== "+clientIp);
		
		Map<String, Object> map = defaultMapper.getUserURLs(authVo);
		if(map != null) {
			if(map.get("url1").equals(clientIp) || map.get("url2").equals(clientIp) || map.get("url3").equals(clientIp)) {
				return false;
			}
		}
		
		loginStatus = 23;
//		setSyslog(loginId, "23");
		return true;
	}
	
	public boolean isSuperUser(int role) {
		return (role == 1 ? true: false);
	}
	public boolean checkLoginDelay(AuthVo authVo, String login, String pw) throws ParseException {
		
		String clientIp = ClientIpUtil.getClientIP(request);
		
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
        if(authVo != null) {
        	
        	if(!"1".equals(authVo.getActive())) {
    			loginStatus = 21;
//    			setSyslog(authVo.getLogin(), "21");
    			return true;
    		}
    		
    		if (!passwordEncoder.matches(authVo.getPassword(), pw)) {
    			authVo.setFailcount(authVo.getFailcount()+1);
    			
    			if(authVo.getFailcount() == authVo.getMaxFailCount()) {
    				defaultMapper.updateLoginTime(authVo);
    			}else if(authVo.getFailcount() > authVo.getMaxFailCount()){
    				long minutesDiff = getMinDiff(authVo.getLastLogin(), authVo.getCurTime());
    				
    				if(minutesDiff >= authVo.getBlockingTime()) {
    					authVo.setFailcount(1);
    				}
    			}
    			
    			defaultMapper.updateFailCount(authVo);
    			loginStatus = 3;
//    			setSyslog(authVo.getLogin(), "3")
    			return true;
    			
    		}else{
    			
    			if(checkLoginURL(authVo)) {
    				return true;
    			}
    			
    			if(authVo.getAllowIp1() != null && authVo.getAllowIp1().length() > 0) {
        			if(!clientIp.equals(authVo.getAllowIp1())) {
        				if(!clientIp.equals(authVo.getAllowIp2())) {
        					loginStatus = 7;
//        					setSyslog(authVo.getLogin(), "7");
        					return true;
        				}
        			}
        		}
        		
        		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
        			long minutesDiff = getMinDiff(authVo.getLastLogin(), authVo.getCurTime());
        			
        			if(minutesDiff >= authVo.getBlockingTime()) {
        	    		authVo.setFailcount(0);
        	    		defaultMapper.updateFailCount(authVo);
        	    		defaultMapper.updateLoginTime(authVo);
        	    		
        	    		loginStatus = 4;
//        	    		setSyslog(authVo.getLogin(), "4");
        	    		return true;
        			}else {
        	    		loginStatus = 5;
//        	    		setSyslog(authVo.getLogin(), "5");
        	    		return true;
        			}
        		}else {
        			
                    Date expireDate = formatter.parse(authVo.getPwdExpireDate());
                    Date currentTime2 = formatter.parse(authVo.getCurTime());

                    if (expireDate.before(currentTime2)) {
                    	loginStatus = 22;
//        	    		setSyslog(authVo.getLogin(), "22");
        				return true;
        			}
        			
        			if("0".equals(authVo.getStatus())) {
        				
        	    		authVo.setFailcount(0);
        	    		defaultMapper.updateFailCount(authVo);
        	    		defaultMapper.updateLoginTime(authVo);
        	    		defaultMapper.updateUserData(clientIp, authVo.getLogin());
        				loginStatus = 13;
        				return false;
        				
        			}else if("1".equals(authVo.getStatus())) {
        				boolean isSuper = isSuperUser(authVo.getRole1());
        				if(isSuper) {
        					long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        					if(secDiff >= 30) {
        						defaultMapper.updateUserData(clientIp, authVo.getLogin());
        						loginStatus = 13;
        						return false;
        					}else {
        						authVo.setFailcount(0);
        						defaultMapper.updateFailCount(authVo);
        						defaultMapper.updateLoginTime(authVo);
//        						setSyslog(authVo.getLogin(), "14");
        						return false;
        					}
        				}else {
        					
        					long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        					if(secDiff >= 30) {
        						defaultMapper.updateUserData(clientIp, authVo.getLogin());
        						loginStatus = 13;
        						return false;
        					}else {
//        						setSyslog(authVo.getLogin(), "11");
        						return true;
        					}
        				}
        			}else if("2".equals(authVo.getStatus())) {
        				
        				long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        				if(secDiff >= 30) {
        					defaultMapper.updateUserData(clientIp, authVo.getLogin());
        					loginStatus = 13;
        				}else {
//        					setSyslog(authVo.getLogin(), "11");
        				}
        				return false;
        			}
        			
        		}    			
    			
    		}
    		
        }else {
        	if(loginStatus == 100) {
        		loginStatus = 100;
//        		setSyslog(login, "100");
        		return true;
        	}else {
        		loginStatus = 8;
//        		setSyslog(login, "8");
        		return true;
        	}
        }
		
		return true;
	}
	
    @Transactional
    public TokenVo logincheck(AuthVo vo) throws ParseException {
    	
    	boolean stateCheck = false; 
    	
    	AuthVo authVo = defaultMapper.selectLogin(vo.getLogin());
    	if(authVo == null) {
    		stateCheck = failLoginProcess(authVo);
    	}else {
    		if(authVo.getLoginStatus() >= 100) {
    			loginStatus = 0;
    		}
    	}
    	
    	stateCheck = checkLoginDelay(authVo, vo.getLogin(), vo.getPwd());
		
		
		if(loginStatus == 11) {
			stateCheck = true; 
		}
		
		log.info("loginStatus : "+loginStatus);
		
		if(loginStatus == 3 && !stateCheck) {
			stateCheck = failLoginProcess(authVo);
		}else if(!stateCheck){
			stateCheck = failLoginProcess(authVo);
		}
    	
		
		TokenVo tokenInfo = null;
		
		if(stateCheck) {
			// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
			// 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(vo.getLogin(), vo.getPwd());
			// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
			// authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			
			// 3. 인증 정보를 기반으로 JWT 토큰 생성
			tokenInfo = jwtTokenProvider.generateToken(authentication);
			tokenInfo.setLoginStatus(loginStatus);
		}
 
        return tokenInfo;
    }
}
