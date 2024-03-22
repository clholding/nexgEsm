package kr.nexg.esm.default1.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import kr.nexg.esm.jwt.service.AuthService;
import kr.nexg.esm.nexgesm.mariadb.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/default")
public class DefaultController {

	@Autowired
	AuthService authService;
	
	@Autowired
    JwtTokenProvider jwtTokenProvider;
    
	@Autowired
	Log.EsmAuditLog esmAuditLog;

	public TokenVo index(TokenVo tokenVo) {
		
		if(tokenVo == null) {
			login(tokenVo, 14);
		}else {
			Map<String, Integer> configMaxCount = new HashMap<>();
	        configMaxCount.put("polFilter", 1000000);
	        configMaxCount.put("polNat", 1000000);
	        configMaxCount.put("polVpn", 10000);
	        configMaxCount.put("polTrackProfile", 100);
	        configMaxCount.put("polVrsProfile", 5000);
	        configMaxCount.put("polMrsProfile", 5000);
	        configMaxCount.put("polTrafficProfile", 5000);
	        configMaxCount.put("polHttpProfile", 5000);
	        configMaxCount.put("polDnsProfile", 5000);
	        configMaxCount.put("polIpProfile", 5000);
	        configMaxCount.put("polUrlProfile", 5000);
	        configMaxCount.put("objNetwork", 100);
	        configMaxCount.put("objNetworkGr", 100);
	        configMaxCount.put("objService", 100);
	        configMaxCount.put("objApp", 5000);
	        configMaxCount.put("objUserGr", 5000);
	        configMaxCount.put("objL7Http", 5000);
	        configMaxCount.put("objL7Dns", 5000);
	        configMaxCount.put("netBgpNet", 100);
	        configMaxCount.put("netBgpNeighbor", 100);
	        configMaxCount.put("netRipConfNeighbor", 100);
	        configMaxCount.put("netOspfConfNeighbor", 100);
	        configMaxCount.put("netOspfAreaNetwork", 100);
	        configMaxCount.put("netOspfAreaLsaType3", 100);
	        configMaxCount.put("netOspfAreaSummaryNetwork", 100);
	        configMaxCount.put("netOspfAccessControlAccess", 100);
	        configMaxCount.put("netOspfAccessControlPrifix", 100);
	        configMaxCount.put("netOspfRouteMapPolicy", 100);
	        configMaxCount.put("netVrrpVertualRouterAddress", 5);
	        configMaxCount.put("netClusterSyncGroup", 32);
	        configMaxCount.put("netDnsServiceMatchClient", 100);
	        configMaxCount.put("netDnsServiceAllowRecursion", 100);
	        configMaxCount.put("netDnsServiceDomain", 100);
	        configMaxCount.put("netDnsDomainZoneTransfer", 100);
	        configMaxCount.put("confAlarmAddress", 5);		
			
	        LocalDateTime currentTime = LocalDateTime.now();
	        String curTime = currentTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	        String curTimeFmt = currentTime.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss (E)"));

	        String defMode = tokenVo.getDefMode();
	        
	        log.info("defMode =============="+defMode);
	        if (defMode != null) {
	            switch (defMode) {
	                case "0":
	                    defMode = "ALL";
	                    break;
	                case "1":
	                    defMode = "FW";
	                    break;
	                case "2":
	                    defMode = "VForce";
	                    break;
	                case "3":
	                    defMode = "SW";
	                    break;
	                case "4":
	                    defMode = "M2MG";
	                    break;
	                case "5":
	                    defMode = "Others";
	                    break;
	            }
	        }
	        
			tokenVo.setTheme("navy");
			tokenVo.setCurTimeFmt(curTimeFmt);
			tokenVo.setCurTime(curTime);
			tokenVo.setDc("0");
			tokenVo.setLocale("ko");
			tokenVo.setConfMaxCnt(configMaxCount);
			tokenVo.setDefMode(defMode);
        
		}
		
		return tokenVo;
	}
	
	public TokenVo login(TokenVo tokenVo, int loginStatus) {
		
		if(tokenVo != null && loginStatus != 14) {
			tokenVo = index(tokenVo);
		}
		
		return tokenVo;
	}
	
	@PostMapping("/forcedLogin")
	public TokenVo forcedLogin(@RequestBody AuthVo authVo) throws ParseException {

		TokenVo tokenVo = null;
		if("true".equals(authVo.getForcedLogin())) {
			tokenVo = logincheck(authVo);
		}else {
			authService.setSyslog(authVo, "14");
			login(tokenVo, 14);
		}
		return tokenVo;
		
	}
	
	/*
	 * 로그인
	 */
	@PostMapping("/logincheck")
	public TokenVo logincheck(@RequestBody AuthVo authVo) throws ParseException {
		
		TokenVo tokenVo = authService.logincheck(authVo);
	
		if(tokenVo == null) {
			
			tokenVo = new TokenVo(null, null, null, 0, false, null, null, null, null, null, null, null, null, null, null, 0);
			tokenVo.setAlertWarning("로그인에 실패하였습니다.");
			tokenVo.setFailLogin(false);

		}else {

			log.info("getLoginStatus = "+tokenVo.getLoginStatus());
			
			if(tokenVo.getLoginStatus() == 13) {
				authService.setSyslog(authVo, "1");
				tokenVo.setFailLogin(false);
				tokenVo = index(tokenVo);
			}else if(tokenVo.getLoginStatus() == 88) {
				authService.forcedLogin(authVo);
				tokenVo = index(tokenVo);
			}else if(tokenVo.getLoginStatus() == 4) {
				tokenVo.setAlertWarning("해당 관리자의 접속 지연이 해제되었습니다. 다시 로그인해주시기 바랍니다.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 5) {
				tokenVo.setAlertWarning("해당 관리자는 접속 지연중입니다.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 3) {
				tokenVo.setAlertWarning("로그인에 실패하였습니다. 로그인 시도 = "+tokenVo.getFileCount());
				tokenVo.setFailLogin(true);				
			}else if(tokenVo.getLoginStatus() == 14) {
				tokenVo.setAlertWarning("다른 관리자가 이미 로그인 되어있습니다.");
				tokenVo.setFailLogin(false);
			}else if(tokenVo.getLoginStatus() == 21) {
				tokenVo.setAlertWarning("비활성화된 관리자 계정입니다.");
				tokenVo.setFailLogin(false);
			}else if(tokenVo.getLoginStatus() == 22) {
				tokenVo.setAlertWarning("비밀번호 유효기간이 만료되었습니다. 비밀번호를 변경해주세요.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 100) {
				tokenVo.setAlertWarning("서버에 로그인 할수 있는 최대 ID 는 100개 입니다.");
				tokenVo.setFailLogin(true);
			}
			
			tokenVo = login(tokenVo, tokenVo.getLoginStatus());			

		}
		
		return tokenVo;
	}
	
	/*
	 * 재인증
	 */
	@PostMapping("/refresh")
	public TokenVo refresh(@RequestBody AuthVo authVo) {
		
		TokenVo tokenVo = null;
		if(jwtTokenProvider.validateToken(authVo.getRefreshToken())) {
			
			Authentication authentication = jwtTokenProvider.getAuthentication(authVo.getRefreshToken());
			tokenVo = jwtTokenProvider.generateToken(authentication);
		}
		
		return tokenVo;
	}
	
	/*
	 * 로그아웃
	 */
	@PostMapping("/logout")
	public TokenVo logout() {

		TokenVo tokenVo = authService.logout();
		
		return tokenVo;
	}
	
	/*
	 * 비밀번호 유효성 검사
	 */
	@PostMapping("/checkpasswordrule")
    public ResponseEntity<MessageVo> checkpasswordrule(HttpServletRequest request, @RequestBody AuthVo authVo) throws IOException  {
	  	
	  HttpHeaders headers= new HttpHeaders();
	  headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	
      MessageVo message;
    
      try {
    	  
    	    String remoteIP = ClientIpUtil.getClientIP(request);
    	
//	      	Map<String, Object> result = authService.checkpasswordrule();
    	    String return_msg = "";
	        int totalCount = 0;
	        
	        if("pass".equals(return_msg)) {
	        	message = MessageVo.builder()
	        			.success("true")
	        			.message("비밀번호 유효성이 확인되었습니다")
	        			.totalCount(totalCount)
	        			.entitys("")
	        			.build();
	        	
	        	esmAuditLog.esmlog(5, authVo.getLogin(), remoteIP, "비밀번호 유효성 검사를 성공하였습니다.");
	        	
	        }else {
	        	message = MessageVo.builder()
	        			.success("false")
	        			.message(return_msg)
	        			.totalCount(totalCount)
	        			.entitys("")
	        			.build();
	        	
	        	esmAuditLog.esmlog(3, authVo.getLogin(), remoteIP, "비밀번호 유효성 검사를 실패하였습니다");
	        }
    	
    	
	  } catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("비밀번호 유효성 검사를 실패하였습니다")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
	  }  
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
  	
    } 
	
	/*
	 * 비밀번호 변경
	 */
	@PostMapping("/changepassword")
	public ResponseEntity<MessageVo> changepassword(@RequestBody AuthVo authVo) {
		
	  HttpHeaders headers= new HttpHeaders();
	  headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	  
      MessageVo message;
      
      try {
    	  
    	    AuthVo vo = authService.changepassword(authVo);
	        int totalCount = 0;
	        
	        if(vo != null) {
	        	message = MessageVo.builder()
	        			.success("true")
	        			.message("비밀번호 변경에 성공하였습니다. 변경된 비밀번호로 다시 로그인해주세요.")
	        			.totalCount(totalCount)
	        			.entitys("")
	        			.build();
	        	
	        	
	        }else {
	        	message = MessageVo.builder()
	        			.success("false")
	        			.message("존재하지 않는 계정입니다.")
	        			.totalCount(totalCount)
	        			.entitys("")
	        			.build();
	        }
    	
    	
	  } catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("비밀번호 갱신에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
	  }  
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
  	
    } 		
	
	
}
