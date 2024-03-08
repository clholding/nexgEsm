package kr.nexg.esm.global.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.util.ClientIpUtil;
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
	* -
	* 관리자 권한정보 조회 (로그인시 또는 F5키 누를시만 호출)
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getUserInfoByLogin")
    public ResponseEntity<MessageVo> getUserInfoByLogin(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        paramMap.put("sessionId", sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getUserInfoByLogin(paramMap);
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
	* -
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getDeviceStatusByLogin")
    public ResponseEntity<MessageVo> getDeviceStatusByLogin(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        paramMap.put("sessionId", sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getDeviceStatusByLogin(paramMap);
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
	* -
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getDeviceFaultStatus")
    public ResponseEntity<MessageVo> getDeviceFaultStatus(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        paramMap.put("sessionId", sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getDeviceFaultStatus(paramMap);
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
	* -
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getAllDeviceFaultStatus")
    public ResponseEntity<MessageVo> getAllDeviceFaultStatus(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        paramMap.put("sessionId", sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getAllDeviceFaultStatus(paramMap);
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
	* -
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getAlarmMsg")
    public ResponseEntity<MessageVo> getAlarmMsg(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        paramMap.put("sessionId", sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	Map<String, Object> map = globalService.getAlarmMsg(paramMap);
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
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/getApplyStatus")
    public ResponseEntity<MessageVo> getApplyStatus(@RequestParam Map<String,Object> paramMap) {
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = globalService.getApplyStatus(paramMap);
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
