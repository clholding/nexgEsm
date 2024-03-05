package kr.nexg.esm.administrator.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import kr.nexg.esm.administrator.dto.AdministratorVo;
import kr.nexg.esm.administrator.service.AdministratorService;
import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.common.util.CustomMessageException;
import kr.nexg.esm.nexgesm.mariadb.Log;
import lombok.extern.slf4j.Slf4j;


/**
 * 관리자 정보
 * 
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/administrator")
public class AdministratorController {
	
	@Autowired
	AdministratorService administratorService;
	
	@Autowired
	Log.EsmAuditLog esmAuditLog;

	/**
	* 관리자정보 조회
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getUserInfo")
    public ResponseEntity<MessageVo> getUserInfo(@RequestParam Map<String,String> paramMap) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = administratorService.getUserInfo(paramMap);
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
	* 관리자정보 리스트
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getUser")
    public ResponseEntity<MessageVo> getUser(@RequestParam Map<String,Object> paramMap) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        paramMap.put("sessionId", sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = administratorService.getUser(paramMap);
            
            int totalCount = 0;
            if(list.size() > 0) {
            	totalCount = (int) list.get(0).get("totalCount");
            }
            
            for (Map<String, Object> map : list) {
                map.remove("totalCount");
            }
        	
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
	* 관리자정보 삭제
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/delUser")
    public ResponseEntity<MessageVo> delUser(HttpServletRequest request, @RequestParam Map<String,Object> paramMap) {

		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	List<String> admin_names = administratorService.delUser(paramMap);
        	String arr = String.join(",", admin_names);
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.entitys("")
                	.build();
        	
        	esmAuditLog.esmlog(6, sessionId, clientIp, String.format("[설정/관리자] 관리자 삭제가 성공하였습니다. (id=%s)", arr));
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(4, sessionId, clientIp, "[설정/관리자] 관리자 삭제에 실패했습니다.");
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* 관리자정보 그룹 삭제
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/delUserGroup")
    public ResponseEntity<MessageVo> delUserGroup(HttpServletRequest request, @RequestParam Map<String,Object> paramMap) {

		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	List<Map<String, Object>> list = administratorService.selectUserGroup(paramMap);
        	if(list.size() > 0) {
        		message = MessageVo.builder()
                    	.success("false")
                    	.message("사용 중인 그룹은 삭제할 수 없습니다.")
                    	.entitys("")
                    	.build();
        	} else {
        		message = MessageVo.builder()
                    	.success("true")
                    	.message("")
                    	.entitys("")
                    	.build();
        	}
        	List<String> user_group_names = administratorService.delUserGroup(paramMap);
        	String arr = String.join(",", user_group_names);
        	
        	esmAuditLog.esmlog(6, sessionId, clientIp, String.format("[설정/관리자] 관리자 그룹 삭제가 성공하였습니다. (id=%s)", arr));
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(4, sessionId, clientIp, "[설정/관리자] 관리자 그룹 삭제에 실패했습니다.");
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
	
	/**
	* 관리자정보 추가/수정
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/setUserInfo")
    public ResponseEntity<MessageVo> setUserInfo(HttpServletRequest request, @RequestParam Map<String,Object> paramMap) {

		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
        paramMap.put("sessionId", sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	Map<String, Object> result = administratorService.setUserInfo(paramMap);
        	String audit_msg = (String) result.get("audit_msg");
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.entitys("")
                	.build();
        	
        	esmAuditLog.esmlog(6, sessionId, clientIp, audit_msg);
		} catch (CustomMessageException e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message(e.getMessage())
	            	.errMsg("")
	            	.errTitle("")
	            	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			//### TO-DO ###
//			setAuditInfo("setUserInfo", results["success"])
			
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
