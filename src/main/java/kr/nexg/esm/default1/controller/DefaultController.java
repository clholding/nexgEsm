package kr.nexg.esm.default1.controller;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.jwt.JwtTokenProvider;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import kr.nexg.esm.jwt.service.AuthService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/default")
public class DefaultController {

	@Autowired
	AuthService authService;
	
	@Autowired
    JwtTokenProvider jwtTokenProvider;
    

	public Map<String, Object> index(AuthVo authVo) {
		
		Map<String, Object> map = new HashMap<String,Object>(); 
		
		if(authVo == null) {
			login(authVo, 14);
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

	        String defMode = authVo.getDefMode();
	        
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
	        
	        map.put("theme", "navy");
	        map.put("curTimeFmt", curTimeFmt);
	        map.put("curTime", curTime);
	        map.put("dc", "0");
	        map.put("locale", "ko");
	        map.put("confMaxCnt", configMaxCount);
	        map.put("defMode", defMode);
	        map.put("userAlarm", authVo.getAlarm());
	        map.put("userPopupTime", authVo.getPopupTime());
        
		}
		
		return map;
	}
	
	public Map<String, Object> login(AuthVo authVo, int loginStatus) {
		
		Map<String, Object> map = new HashMap<String,Object>(); 
		if(authVo != null && loginStatus != 14) {
			map = index(authVo);
		}
		
		return map;
	}
	
	@PostMapping("/forcedLogin")
	public TokenVo forcedLogin(@RequestBody AuthVo authVo) throws ParseException {

		TokenVo tokenVo = null;
		if("true".equals(authVo.getForcedLogin())) {
			tokenVo = logincheck(authVo);
		}else {
			authService.setSyslog(authVo, "14");
			login(authVo, 14);
		}
		return tokenVo;
		
	}
	
	@PostMapping("/logincheck")
	public TokenVo logincheck(@RequestBody AuthVo authVo) throws ParseException {
		
		TokenVo tokenVo = authService.logincheck(authVo);
	
		if(tokenVo == null) {
			tokenVo.setAlertWarning("로그인에 실패하였습니다.");
			tokenVo.setFailLogin(true);
		}else {
			
			if(tokenVo.getLoginStatus() == 3) {
//				tokenVo.setAlertWarning("비활성화된 관리자 계정입니다.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 4) {
				tokenVo.setAlertWarning("해당 관리자의 접속 지연이 해제되었습니다. 다시 로그인해주시기 바랍니다.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 5) {
				tokenVo.setAlertWarning("해당 관리자는 접속 지연중입니다.");
				tokenVo.setFailLogin(true);
			}else if(tokenVo.getLoginStatus() == 13) {

				
			}else if(tokenVo.getLoginStatus() == 14) {
				tokenVo.setAlertWarning("다른 관리자가 이미 로그인 되어있습니다.");
				tokenVo.setFailLogin(false);
			}else if(tokenVo.getLoginStatus() == 21) {
				tokenVo.setAlertWarning("비활성화된 관리자 계정입니다.");
				tokenVo.setFailLogin(false);
			}else if(tokenVo.getLoginStatus() == 22) {
				tokenVo.setAlertWarning("비밀번호 유효기간이 만료되었습니다. 비밀번호를 변경해주세요.");
				tokenVo.setFailLogin(true);
//				change_password();
			}else if(tokenVo.getLoginStatus() == 88) {
				index(authVo);
			}else if(tokenVo.getLoginStatus() == 100) {
				tokenVo.setAlertWarning("서버에 로그인 할수 있는 최대 ID 는 100개 입니다.");
				tokenVo.setFailLogin(true);
			}
			
			Map<String, Object> map = login(authVo, tokenVo.getLoginStatus());
			String theme = String.valueOf(map.get("theme"));
			String curTimeFmt = String.valueOf(map.get("curTimeFmt"));
			String curTime = String.valueOf(map.get("curTime"));
			String dc = String.valueOf(map.get("dc"));
			String locale = String.valueOf(map.get("locale"));
			String confMaxCnt = String.valueOf(map.get("confMaxCnt"));
			String defMode = String.valueOf(map.get("defMode"));
			String userAlarm = String.valueOf(map.get("userAlarm"));
			String userPopupTime = String.valueOf(map.get("userPopupTime"));
			
			tokenVo.setTheme(theme);
			tokenVo.setCurTimeFmt(curTimeFmt);
			tokenVo.setCurTime(curTime);
			tokenVo.setDc(dc);
			tokenVo.setLocale(locale);
			tokenVo.setConfMaxCnt(confMaxCnt);
			tokenVo.setDefMode(defMode);
			tokenVo.setUserAlarm(userAlarm);
			tokenVo.setUserPopupTime(userPopupTime);
		}
		
		return tokenVo;
	}
	
	@PostMapping("/refresh")
	public TokenVo refresh(@RequestBody AuthVo authVo) {
		
		TokenVo tokenVo = null;
		if(jwtTokenProvider.validateToken(authVo.getRefreshToken())) {
			
			Authentication authentication = jwtTokenProvider.getAuthentication(authVo.getRefreshToken());
			tokenVo = jwtTokenProvider.generateToken(authentication);
		}
		
		return tokenVo;
	}
	
	@PostMapping("/logout")
	public TokenVo logout() {

		TokenVo tokenVo = authService.logout();
		
		return tokenVo;
	}
}
