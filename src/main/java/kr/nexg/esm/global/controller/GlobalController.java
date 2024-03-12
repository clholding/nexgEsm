package kr.nexg.esm.global.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.global.dto.GlobalVo;
import kr.nexg.esm.global.service.GlobalService;
import lombok.extern.slf4j.Slf4j;

/**
 * 공통정보 관리
 * 
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/global")
public class GlobalController {
	
	@Autowired
	GlobalService globalService;

	/**
	* -
	* -
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
//	@PostMapping("/devices")
//    public ResponseEntity<MessageVo> devices(@RequestParam Map<String,Object> paramMap) {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//        
//        MessageVo message;
//        
//        try {
//        	
//        	List<Map<String, Object>> list = globalService.devices(paramMap);
//            int totalCount = list.size();
//        	
//        	message = MessageVo.builder()
//                	.success("true")
//                	.message("")
//                	.totalCount(totalCount)
//                	.entitys(list)
//                	.build();
//		} catch (Exception e) {
//			log.error("Error : ", e);
//			message = MessageVo.builder()
//	            	.success("false")
//	            	.message("")
//	            	.errMsg(e.getMessage())
//	            	.errTitle("")
//	            	.build();
//		}
//    	
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    }
	
	/**
	* 메인 > 탑 메뉴 메인 > 초기 대시보드
	* 관리자 권한정보 조회 (로그인시 또는 F5키 누를시만 호출)
	* 
	* @ param 
	* @ return ResponseEntity
	*/
	@PostMapping("/getUserInfoByLogin")
    public ResponseEntity<MessageVo> getUserInfoByLogin() {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();

        GlobalVo globalVo = new GlobalVo();
        globalVo.setSessionId(sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getUserInfoByLogin(globalVo);
            int totalCount = list.size();
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* -
	* 관리자가 관리하는 모드별 전체 장비 개수 및 장애 장비 개수 조회
	* 
	* @ param GlobalVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getDeviceStatusByLogin")
    public ResponseEntity<MessageVo> getDeviceStatusByLogin(@RequestBody GlobalVo globalVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        globalVo.setSessionId(sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getDeviceStatusByLogin(globalVo);
            int totalCount = list.size();
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* 메인 > 대시보드 >장비현황
	* 관리자가 관리하는 전체 그룹(하위 그룹 포함)에 속한 장비에 대한 장비 장애 조회
	* 
	* @ param GlobalVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getDeviceFaultStatus")
    public ResponseEntity<MessageVo> getDeviceFaultStatus(@RequestBody GlobalVo globalVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        globalVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getDeviceFaultStatus(globalVo);
            int totalCount = list.size();
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* -
	* 관리자가 관리하는 장비 그룹 리스트를 조회
	* 
	* @ param GlobalVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getAllDeviceFaultStatus")
    public ResponseEntity<MessageVo> getAllDeviceFaultStatus(@RequestBody GlobalVo globalVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        globalVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getAllDeviceFaultStatus(globalVo);
            int totalCount = 1;
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* -
	* 실시간 알람 메세지 체크
	* 
	* @ param GlobalVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getAlarmMsg")
    public ResponseEntity<MessageVo> getAlarmMsg(@RequestBody GlobalVo globalVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        globalVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	Map<String, Object> map = globalService.getAlarmMsg(globalVo);
        	List<Map<String, Object>> list = (List<Map<String, Object>>) map.get("result");
        	String userStatus = (String) map.get("userStatus");
            int totalCount = (int) map.get("totalCount");
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message(userStatus)
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* -
	* -
	* 
	* @ param 
	* @ return ResponseEntity
	*/
	@PostMapping("/getApplyStatus")
    public ResponseEntity<MessageVo> getApplyStatus() {
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getApplyStatus();
        	int totalCount = list.size();
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* 세션의 남은 유효 시간
	* 
	* @ param 
	* @ return ResponseEntity
	*/
	@PostMapping("/remaintimeCheck")
    public ResponseEntity<MessageVo> remaintimeCheck(HttpSession session) {
		
		long remainTime = 0;
        if (session != null) {
            // 세션의 최대 유효 시간
            int maxInactiveInterval = session.getMaxInactiveInterval();
            // 세션의 마지막 요청으로부터 경과된 시간
            long timeElapsedSinceLastRequest = System.currentTimeMillis() - session.getLastAccessedTime();
            // 세션의 남은 유효 시간
            remainTime = (maxInactiveInterval*1000) - timeElapsedSinceLastRequest;
            remainTime = remainTime > 0 ? remainTime : 0;
        }
        
        int _remainTime = (int) (remainTime / 1000);	// 밀리초(ms) -> 초(s)로 변환
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Integer>> list = new ArrayList<>();
        	Map<String, Integer> map = new HashMap<>();
        	map.put("remain_time", _remainTime);
        	list.add(map);
        	int totalCount = list.size();
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
}
