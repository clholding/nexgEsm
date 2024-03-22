package kr.nexg.esm.jwt.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.default1.mapper.DefaultMapper;
import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.SecurityUtil;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import kr.nexg.esm.nexgesm.mariadb.Log;
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
	Log.EsmAuditLog audit;
	
	@Autowired
	private HttpServletRequest request;
	
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
 	
    private int loginStatus = 0;
    private String loginMsg = "";
    
    public void setSyslog(AuthVo authVo, String type) {
    	
        int f_count;
        if (authVo != null) {
            f_count = authVo.getFailcount();
        } else {
            f_count = 1;
        }

        String remoteIP = ClientIpUtil.getClientIP(request);
        String id = authVo.getLogin();
        
        String msg = "";
        boolean setLoginStatus = true;
        
        if (type.equals("1")) {
            msg = "Login succeeded.";
            audit.esmlog(5, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("3")) {
            msg = "Login Fail: invalid password. attempt=" + String.valueOf(f_count);
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));

            if (f_count == authVo.getMaxFailCount()) {
//                Smtp smtp_util = new Smtp();
                String smtp_title = "[ESM] " + String.valueOf(id) + " login " + String.valueOf(f_count) + " failed";
                String smtp_body = "ID : " + String.valueOf(id) + "\nIP : " + String.valueOf(remoteIP) + "\nLogin fail count: " + String.valueOf(f_count);
//                smtp_util.send_mail(null, null, String.valueOf(smtp_title), String.valueOf(smtp_body));
            }
        } else if (type.equals("4")) {
            msg = "Login unlocked.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("5")) {
            msg = "Login failed: delayed user.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("6")) {
            msg = "Login failed: already logged in to " + String.valueOf(id) + ".";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("7")) {
            msg = "Login failed: unacceptable access address " + String.valueOf(remoteIP);
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("8")) {
            msg = "Login failed: invalid user.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("9")) {
            msg = "Logout";
        } else if (type.equals("10")) {
            msg = "Timeout";
            audit.esmlog(5, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("11")) {
            msg = "Login failed: Another user is currently connected.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("12")) {
            msg = "Any User Information does not exist.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("14")) {
            msg = "Login failed: already logged in to " + String.valueOf(id) + ".";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("15")) {
            msg = "Logout: forced to logout " + String.valueOf(id) + ".";
            audit.esmlog(5, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("17")) {
        	HttpSession session = request.getSession();
            msg = "Timeout: " + String.valueOf(id) + " user last accessed at " + String.valueOf(session.getMaxInactiveInterval());
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("18")) {
            msg = "Login succeeded: forced to logout " + String.valueOf(id) + ".";
            audit.esmlog(5, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("21")) {
            msg = "Login failed: " + String.valueOf(id) + " account is inactive.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("23")) {
            msg = "Login failed: " + String.valueOf(id) + " invalid URL access.";
            audit.esmlog(3, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        } else if (type.equals("100")) {
            msg = "Too many user logined esm server.";
            audit.esmlog(5, String.valueOf(id), String.valueOf(remoteIP), String.valueOf(msg));
        }
        
        if (setLoginStatus) {
            loginStatus = Integer.parseInt(type);
            loginMsg = String.valueOf(msg);
        }
    }
    
    public void forcedLogin(AuthVo authVo) {
    	
    	String remoteIP = ClientIpUtil.getClientIP(request);
    	defaultMapper.updateUserData(remoteIP, authVo.getLogin());
    	
    	setSyslog(authVo, "18");
    }
    
    public long getMinDiff(String tarTime, String curTime) {
    	
    	log.info("getMinDiff ============== " );
    	
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
    	
    	log.info("getSecDiff ============== " );
    	
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date targetDate = dateFormat.parse(tarTime);
        long seconds1 = targetDate.getTime() / 1000;

        Date currentDate = dateFormat.parse(curTime);
        long seconds2 = currentDate.getTime() / 1000;

        long secDiff = seconds2 - seconds1;
        return secDiff;
    }
    
	public boolean updateUserInfo(AuthVo authVo, String mode) {

		log.info("updateUserInfo ============== " + authVo);
		
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
        	
            long minutesDiff = getMinDiff(authVo.getLdate(), authVo.getCurTime());
            
            log.info("minutesDiff : "+minutesDiff);
        	if(minutesDiff >= authVo.getBlockingTime()) {
        		
        		authVo.setFailcount(0);
        		defaultMapper.updateFailCount(authVo);
        		defaultMapper.updateLoginTime(authVo);
//				setSyslog(authVo, "4");
				loginStatus = 4;
        		
        	}else {
        		if("delay".equals(authVo.getAdminFailAction())) {
//    				setSyslog(authVo, "5");
    				loginStatus = 5;
        		}else {
//    				setSyslog(authVo, "11");
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
	
	public boolean failLogin(AuthVo authVo) {
		log.info("failLogin ============== " + authVo);
		
		updateUserInfo(authVo, "update");
//		setSyslog(authVo, "3");
		
		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
			if("delay".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo, "5");
				loginStatus = 5;
			}else if("lock".equals(authVo.getAdminFailAction())) {
//				setSyslog(authVo, "11");
				loginStatus = 11;
			}
		}
		
		return true;
	}
	
	public boolean checkLoginURL(AuthVo authVo) {
		log.info("checkLoginURL ============== " + authVo);
		
		String remoteIP = ClientIpUtil.getClientIP(request);
		log.info("remoteIP ============== "+remoteIP);
		
		Map<String, Object> map = defaultMapper.getUserURLs(authVo);
		if(map != null) {
			if(map.get("url1").equals(remoteIP) || map.get("url2").equals(remoteIP) || map.get("url3").equals(remoteIP)) {
				loginStatus = 23;
		//		setSyslog(authVo, "23");
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isSuperUser(int role) {
		return (role == 1 ? true: false);
	}
	public boolean checkLoginDelay(AuthVo authVo, String login, String pw) throws ParseException {
		log.info("checkLoginDelay ============== " + authVo);
		
		String remoteIP = ClientIpUtil.getClientIP(request);
		
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedTime = formatter.format(currentTime);
        authVo.setCurTime(formattedTime);
        
        if(authVo != null) {
        	
        	if(!"1".equals(authVo.getActive())) {
    			loginStatus = 21;
//    			setSyslog(authVo, "21");
    			return true;
    		}
    		
//        	log.info("pw = "+ pw);
//        	log.info("password = "+ authVo.getPassword());
        	
    		if (!passwordEncoder.matches(pw, authVo.getPassword())) {
    			
    			authVo.setFailcount(authVo.getFailcount()+1);
    			
    			if(authVo.getFailcount() == authVo.getMaxFailCount()) {
    				
    				defaultMapper.updateLoginTime(authVo);
    			}else if(authVo.getFailcount() > authVo.getMaxFailCount()){
    				long minutesDiff = getMinDiff(authVo.getLdate(), authVo.getCurTime());
    				
    				if(minutesDiff >= authVo.getBlockingTime()) {
    					authVo.setFailcount(1);
    				}
    			}
    			
    			defaultMapper.updateFailCount(authVo);
    			loginStatus = 3;
//    			setSyslog(authVo, "3")
    			return true;
    			
    		}else{
    			
    			if(checkLoginURL(authVo)) {
    				return true;
    			}
    			
    			if(authVo.getAllowIp1() != null && authVo.getAllowIp1().length() > 0) {
        			if(!remoteIP.equals(authVo.getAllowIp1())) {
        				if(!remoteIP.equals(authVo.getAllowIp2())) {
        					loginStatus = 7;
//        					setSyslog(authVo, "7");
        					return true;
        				}
        			}
        		}
        		
        		if(authVo.getFailcount() >= authVo.getMaxFailCount()) {
        			long minutesDiff = getMinDiff(authVo.getLdate(), authVo.getCurTime());
        			
        			if(minutesDiff >= authVo.getBlockingTime()) {
        	    		authVo.setFailcount(0);
        	    		defaultMapper.updateFailCount(authVo);
        	    		defaultMapper.updateLoginTime(authVo);
        	    		
        	    		loginStatus = 4;
//        	    		setSyslog(authVo, "4");
        	    		return true;
        			}else {
        	    		loginStatus = 5;
//        	    		setSyslog(authVo, "5");
        	    		return true;
        			}
        		}else {
        			
                    Date expireDate = formatter.parse(authVo.getPwdExpireDate());
                    Date currentTime2 = formatter.parse(authVo.getCurTime());

                    if (expireDate.before(currentTime2)) {
                    	loginStatus = 22;
//        	    		setSyslog(authVo, "22");
        				return true;
        			}
        			
        			if("0".equals(authVo.getStatus())) {
        				
        	    		authVo.setFailcount(0);
        	    		defaultMapper.updateFailCount(authVo);
        	    		defaultMapper.updateLoginTime(authVo);
        	    		defaultMapper.updateUserData(remoteIP, authVo.getLogin());
        				loginStatus = 13;
        				return false;
        				
        			}else if("1".equals(authVo.getStatus())) {
        				boolean isSuper = isSuperUser(authVo.getRole1());
        				if(isSuper) {
        					long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        					if(secDiff >= 30) {
        						defaultMapper.updateUserData(remoteIP, authVo.getLogin());
        						loginStatus = 13;
        						return false;
        					}else {
        						authVo.setFailcount(0);
        						defaultMapper.updateFailCount(authVo);
        						defaultMapper.updateLoginTime(authVo);
//        						setSyslog(authVo, "14");
        						return false;
        					}
        				}else {
        					
        					long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        					if(secDiff >= 30) {
        						defaultMapper.updateUserData(remoteIP, authVo.getLogin());
        						loginStatus = 13;
        						return false;
        					}else {
//        						setSyslog(authVo, "11");
        						return true;
        					}
        				}
        			}else if("2".equals(authVo.getStatus())) {
        				
        				long secDiff = getSecDiff(authVo.getHbtime(), authVo.getCurTime());
        				if(secDiff >= 30) {
        					defaultMapper.updateUserData(remoteIP, authVo.getLogin());
        					loginStatus = 13;
        				}else {
//        					setSyslog(authVo, "11");
        				}
        				return false;
        			}
        			
        		}    			
    			
    		}
    		
        }
//        else {
//        	if(loginStatus == 100) {
//        		loginStatus = 100;
//        		setSyslog(login, "100");
//        		return true;
//        	}else {
//        		loginStatus = 8;
//        		setSyslog(login, "8");
//        		return true;
//        	}
//        }
		
		return true;
	}
	
    @Transactional
    public TokenVo logincheck(AuthVo vo) throws ParseException {
    	
    	boolean stateCheck = false; 
    	boolean userCheck = false; 
    	
    	AuthVo authVo = defaultMapper.selectLogin(vo.getLogin());
    	if(authVo == null) {
//    		stateCheck = failLogin(authVo);
    		stateCheck = true;
    		userCheck = false; 
    	}else {
    		if(authVo.getLoginStatus() >= 100) {
    			loginStatus = 0;
    		}
    	}
    	
    	if(!stateCheck) {
    		stateCheck = checkLoginDelay(authVo, vo.getLogin(), vo.getPwd());
    		userCheck = true;
    	}
		
		if(loginStatus == 11) {
			stateCheck = true; 
			userCheck = true;
		}
		if(loginStatus == 13 || loginStatus == 14) {
			authVo.setFailcount(0);
		}
		
		log.info("loginStatus : "+loginStatus);
		log.info("loginMsg : "+loginMsg);
		log.info("stateCheck : "+stateCheck);
		
		if(loginStatus == 3 && !stateCheck) {
			stateCheck = failLogin(authVo);
			userCheck = false;
		}else if(!stateCheck){
			stateCheck = failLogin(authVo);
			userCheck = false;
		}
    	
		TokenVo tokenInfo = null;
		
		if(stateCheck && userCheck) {
			// 1. Login ID/PW 를 기반으로 Authentication 객체 생성
			// 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(vo.getLogin(), vo.getPwd());
			// 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
			// authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			
			// 3. 인증 정보를 기반으로 JWT 토큰 생성
			tokenInfo = jwtTokenProvider.generateToken(authentication);
			tokenInfo.setDefMode(authVo.getDefMode());
			tokenInfo.setUserAlarm(authVo.getAlarm());
			tokenInfo.setUserPopupTime(authVo.getPopupTime());
			tokenInfo.setLoginStatus(loginStatus);
			tokenInfo.setFileCount(authVo.getFailcount());
		}
		
 
        return tokenInfo;
    }
    
    
    @Transactional
    public TokenVo logout() {
    	
        String login = SecurityUtil.getId();
        
        defaultMapper.updateUserStatus(login);
        defaultMapper.deleteUserToken(login);
        
        String remoteIP = ClientIpUtil.getClientIP(request);
    	audit.esmlog(3, login, remoteIP, "Logout");
    	
        TokenVo tokenInfo = null;
        return tokenInfo;
    }
    
    public AuthVo changepassword(AuthVo authVo) {
    	
    	AuthVo vo = defaultMapper.selectLogin(authVo.getLogin());
    	
    	if(vo != null) {
    		defaultMapper.updateUserPassword(vo);
    	}
    	
    	return vo;
    }
}
