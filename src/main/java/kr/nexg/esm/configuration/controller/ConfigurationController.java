package kr.nexg.esm.configuration.controller;

import java.io.IOException;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.configuration.dto.ConfigurationVo;
import kr.nexg.esm.configuration.service.ConfigurationService;
import kr.nexg.esm.nexgesm.mariadb.Config;
import kr.nexg.esm.nexgesm.mariadb.Log;
import kr.nexg.esm.util.config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
	
	@Autowired
	ConfigurationService configurationService;
	
	@Autowired
	Log.EsmAuditLog esmAuditLog;
	
	@Autowired
	Config.Config1 config1;
	
    /*
     * TopNav > 시스템 설정 > 시스템 > 제품정보
     */
    @PostMapping("/getSystemInfo")
    public ResponseEntity<MessageVo> getSystemInfo() throws IOException  {
  	
	  HttpHeaders headers= new HttpHeaders();
	  headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
	
      MessageVo message;
    
      try {
    	
	      	Map<String, Object> result = configurationService.getSystemInfo();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("제품정보가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
    	
    	
	  } catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("제품정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
	  }  
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
  	
    } 

    /*
     * TopNav > 시스템 설정 > 시스템 > 시간 동기화
     */  
    @PostMapping("/getNtpInfo")
    public ResponseEntity<MessageVo> getNtpInfo() throws IOException  {
		
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	      	Map<String, Object> result = configurationService.getNtpInfo();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("시간동기화가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("시간동기화 정보조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}  
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
    } 
  
    /*
     * TopNav > 시스템 설정 > 시스템 > 서버 무결성 상태
     */      
	@PostMapping("/getIntegrityInfo")
	public ResponseEntity<MessageVo> getIntegrityInfo(@RequestBody ConfigurationVo configurationVo) throws IOException  {
		
		
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
        	Map<String, Object> result = configurationService.getIntegrityInfo(configurationVo);
            int totalCount = (int) result.get("total");
            
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("무결성이 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result.get("result"))
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("무결성 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
		
		return new ResponseEntity<>(message, headers, HttpStatus.OK);
		
	}   
  
    /*
     * TopNav > 시스템 설정 > 시스템 > 서버 무결성 상태 > 무결성 검사
     */ 	
    @PostMapping("/updateIntegrity")
    public ResponseEntity<MessageVo> updateIntegrity() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	        int totalCount = 0;
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys("")
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("실패되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
  	
    } 
   
    /*
     * TopNav > 시스템 설정 > 시스템 > 에이전트 무결성 상태
     */     
	@PostMapping("/getDeviceIntegrityInfo")
	public ResponseEntity<MessageVo> getDeviceIntegrityInfo() throws IOException  {
	 	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	List<Map<String, Object>> list = configurationService.getDeviceIntegrityInfo();
	    	
	        int totalCount = list.size();
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("Agent무결성이 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(list)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("Agent무결성이 실패되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
	  	
	}     
	
    /*
     * TopNav > 시스템 설정 > 시스템 > 에이전트 무결성 상태 > 무결성 확인
     */ 	
    @PostMapping("/checkAgentIntegrity")
    public ResponseEntity<MessageVo> checkAgentIntegrity() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	        int totalCount = 0;
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys("")
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("에이전트 무결성 확인중 오류가 발생되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 	
    
    /*
     * TopNav > 시스템 설정 > 시스템 > 장비 설정파일 백업
     */    
    @PostMapping("/getConfigBackupInfo")
    public ResponseEntity<MessageVo> getConfigBackupInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getConfigBackupInfo();
	    	
	        int totalCount = 0;
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("환경 설정파일 백업 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 	   
    
    /*
     * TopNav > 시스템 설정 > 시스템 > 장비 설정파일 백업 내역
     */ 
    @PostMapping("/getConfigBackupList")
    public ResponseEntity<MessageVo> getConfigBackupList(@RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getConfigBackupList(configurationVo);
	    	int totalCount = (int) result.get("total");
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("")
	            	.totalCount(totalCount)
	            	.entitys(result.get("result"))
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("db connection error")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
    /*
     * TopNav > 시스템 설정 > 시스템 > 시스템 설정파일 백업
     */ 
    @PostMapping("/getSystemConfigBackupInfo")
    public ResponseEntity<MessageVo> getSystemConfigBackupInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
    	
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSystemConfigBackupInfo();
	    	int totalCount = 0;
	    	
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("환경 설정파일 백업 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
    /*
     * TopNav > 시스템 설정 > 시스템 > 시스템 설정파일 백업 내역
     */
    @PostMapping("/getSystemConfigBackupList")
    public ResponseEntity<MessageVo> getSystemConfigBackupList(@RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSystemConfigBackupList(configurationVo);
	    	int totalCount = (int) result.get("total");
	    	
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("")
	            	.totalCount(totalCount)
	            	.entitys(result.get("result"))
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("no request datas")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
      
    /*
     * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정
     */
    @PostMapping("/getLogDiskInfo")
    public ResponseEntity<MessageVo> getLogDiskInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getLogDiskInfo();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("디스크 임계치가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("디스크 임계치 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
    /*
     * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정 > 설정 
     */
    @PostMapping("/setLogDiskInfo")
    public ResponseEntity<MessageVo> setLogDiskInfo(HttpServletRequest request, @RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.setLogDiskInfo(configurationVo);
	    	
	    	if("0".equals(result.get("col"))) {
	    		
	    		message = MessageVo.builder()
	    				.success("false")
	    				.message("")
	    				.entitys("")
	    				.build();
	    		
	    		esmAuditLog.esmlog(3, sessionId, clientIp, "디스크 임계치 설정 실패되었습니다.");
	    	}else {
	    		
	    		message = MessageVo.builder()
	    				.success("true")
	    				.message("")
	    				.entitys("")
	    				.build();
	    		
	    		esmAuditLog.esmlog(6, sessionId, clientIp, "디스크 임계치 설정 변경되었습니다.");
	    	}
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("디스크 임계치 설정 실패되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(3, sessionId, clientIp, "디스크 임계치 설정 실패되었습니다.");
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
    /*
     * TopNav > 시스템 설정 > 알람 > 장비/그룹 임계치 설정 > 장비/그룹 기본 설정
     */
    @PostMapping("/getAlarmInfo")
    public ResponseEntity<MessageVo> getAlarmInfo(@RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getAlarmInfo(configurationVo);
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("디스크 임계치가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("장비 임계치 정보 조회가 실패되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
    
    /*
     * TopNav > 시스템 설정 > 알람 > 장비/그릅 입계치 설정 > 장비/그룹 기본설정 / 기본 설정 
     */
    @PostMapping("/setAlarmInfo")
    public ResponseEntity<MessageVo> setAlarmInfo(HttpServletRequest request, @RequestBody ConfigurationVo configurationVo) throws IOException  {
    	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = null;
    	
    	try {
    		
    		Map<String, Object> result = configurationService.setAlarmInfo(configurationVo);
    		
	    	if(!result.isEmpty()) {
	    		if("0".equals(result.get("col"))) {
    				
    				message = MessageVo.builder()
    						.success("false")
    						.message("장비 임계치 설정 실패되었습니다.")
    						.entitys(result)
    						.build();
    				
    				esmAuditLog.esmlog(3, sessionId, clientIp, "디스크 임계치 설정 실패되었습니다.");
    			}else {
    				
    				message = MessageVo.builder()
    						.success("true")
    						.message("장비 임계치 설정 변경되었습니다.")
    						.entitys(result)
    						.build();
    				
    				esmAuditLog.esmlog(6, sessionId, clientIp, "장비 임계치 설정 변경되었습니다.");
    				
    				config1.set_apply_status(true);
    			}
    		}
    		
    		
    	} catch (Exception e) {
    		
    		message = MessageVo.builder()
    				.success("false")
    				.message("장비 임계치 설정 실패되었습니다.")
    				.errMsg(e.getMessage())
    				.errTitle("")
    				.build();
    		
    		esmAuditLog.esmlog(3, sessionId, clientIp, "디스크 임계치 설정 실패되었습니다.");
    	} 
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
    
  
    /*
     * TopNav > 시스템 설정 > 알람 > SMTP 설정
     */
    @PostMapping("/getSmtpInfo")
    public ResponseEntity<MessageVo> getSmtpInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSmtpInfo();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("SMTP가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("SMTP 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }   
    
    /*
     * TopNav > 시스템 설정 > 알람 > SMTP 전송 이벤트 
     */
    @PostMapping("/getSmtpEventInfo")
    public ResponseEntity<MessageVo> getSmtpEventInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSmtpEventInfo();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("SMTP가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("SMTP 전송 이벤트 조회가 실패되었습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }    
  
    /*
     * TopNav > 시스템 설정 > 기타 > SNMP > SNMP 설정  
     */
    @PostMapping("/getSnmpInfo")
    public ResponseEntity<MessageVo> getSnmpInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSnmpInfo();
	        int totalCount = 0;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("SNMP 정보 조회가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("SNMP 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }      
    
    /*
     * TopNav > 시스템 설정 > 기타 > SNMP > Trap 설정
     */
    @PostMapping("/getSnmpTrapInfo")
    public ResponseEntity<MessageVo> getSnmpTrapInfo() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getSnmpTrapInfo();
	        int totalCount = 0;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("SNMP TRAP 정보 조회가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("SNMP TRAP 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
  
    /*
     * TopNav > 시스템 설정 > 기타 > 장애 회선 관리
     */
    @PostMapping("/getInterfaceConfig")
    public ResponseEntity<MessageVo> getInterfaceConfig() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getInterfaceConfig();
	        int totalCount = 0;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("장애 회선 관리 정보 조회를 완료하였습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("장애 회선 관리 정보 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
  
    /*
     * TopNav > 시스템 설정 > 기타 > 장애 회선 관리 > 장애 회선 임계치 설정
     */
    @PostMapping("/setInterfaceConfig")
    public ResponseEntity<MessageVo> setInterfaceConfig(HttpServletRequest request, @RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
    	SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
			
			if(configurationVo.getInterfaceDowntime() == null) {
				configurationVo.setInterfaceDowntime("0");
			}
			if(configurationVo.getInterfaceUpdownCount() == null) {
				configurationVo.setInterfaceUpdownCount("0");
			}
			if(configurationVo.getInterfaceUpdownHours() == null) {
				configurationVo.setInterfaceUpdownHours("0");
			}
			
	    	Map<String, Object> result = configurationService.setInterfaceConfig(configurationVo);
	    	log.info("result : "+result);
	    	
	    	if(!result.isEmpty()) {
	    		if("0".equals(result.get("col"))) {
	    			
	    			message = MessageVo.builder()
	    	            	.success("false")
	    	            	.message("")
	    	            	.errTitle("")
	    	            	.build();
	    			
	    			esmAuditLog.esmlog(3, sessionId, clientIp, "장애 회선 관리 정보 설정에 실패하였습니다.");
	    		}else {
	    			
	    	    	message = MessageVo.builder()
	    	            	.success("true")
	    	            	.message("")
	    	            	.entitys("")
	    	            	.build();
	    	    	
	                String msg = "장애 회선 관리 정보를 설정하였습니다. 장시간 Down 임계 시간 = " + configurationVo.getInterfaceDowntime() + "분, Up/Down 반복수 제한 = "
	                        + configurationVo.getInterfaceUpdownCount() + "회, Up/Down 반복 임계 시간 = " + configurationVo.getInterfaceUpdownHours() + "분";
	                
	    			esmAuditLog.esmlog(6, sessionId, clientIp, msg);
	    		}
	    	}else {
	    		
				message = MessageVo.builder()
		            	.success("false")
		            	.message("")
		            	.errTitle("")
		            	.build();
				
	    		esmAuditLog.esmlog(3, sessionId, clientIp, "장애 회선 관리 정보 설정에 실패하였습니다.");
	    	}
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(3, sessionId, clientIp, "장애 회선 관리 정보 설정에 실패하였습니다.");
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    } 
    
    /*
     * TopNav > 시스템 설정 > 기타 > 장비 자동 등록
     */
    @PostMapping("/getDeviceRegisterConfig")
    public ResponseEntity<MessageVo> getDeviceRegisterConfig() throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	    	Map<String, Object> result = configurationService.getDeviceRegisterConfig();
	        int totalCount = 1;
	        
	    	message = MessageVo.builder()
	            	.success("true")
	            	.message("장비 자동 등록 조회가 완료되었습니다.")
	            	.totalCount(totalCount)
	            	.entitys(result)
	            	.build();
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("장비 자동 등록 조회에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }     

    /*
     * TopNav > 시스템 설정 > 기타 > 장비 자동 등록 > 장비 자동 등록 설정
     */
    @PostMapping("/setDeviceRegisterConfig")
    public ResponseEntity<MessageVo> setDeviceRegisterConfig(@RequestBody ConfigurationVo configurationVo) throws IOException  {
  	
		HttpHeaders headers= new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
	    MessageVo message;
	    
	    try {
	    	
	        if ("0".equals(configurationVo.getEnable())) {
	        	configurationVo.setGroupid("0");
	        	configurationVo.setActive("0");
	        	configurationVo.setLog("0");
	        }
	        
	        if ((configurationVo.getGroupid() == null || configurationVo.getActive() == null || configurationVo.getLog() == null) && "1".equals(configurationVo.getEnable())) {
	        	
	        	message = MessageVo.builder()
	        			.success("false")
	        			.message("Please input required fields.")
	        			.entitys("")
	        			.build();
	        }else {
	        	
	        	configurationService.setDeviceRegisterConfig(configurationVo);
	        	
	        	message = MessageVo.builder()
	        			.success("true")
	        			.message("완료되었습니다.")
	        			.entitys("")
	        			.build();
	        }
	    	
	    	
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("장비 자동 등록 설정 변경에 실패하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
  	
  	  return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }     

    
//    @PostMapping("/backupConfig")
//    public ResponseEntity<MessageVo> backupConfig() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        MessageVo message = MessageVo.builder()
//            	.status(StatusEnum.OK)
//            	.message("성공 코드")
//            	.data("")
//            	.build();
//    	
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    
//    @PostMapping("/configbackupDelete")
//    public ResponseEntity<MessageVo> configbackupDelete() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/configbackupDownload")
//    public ResponseEntity<MessageVo> configbackupDownload() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/deviceConfigBackup")
//    public ResponseEntity<MessageVo> deviceConfigBackup() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    
//    @PostMapping("/getBwtConfigInfo")
//    public ResponseEntity<MessageVo> getBwtConfigInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    
//    @PostMapping("/restoreConfig")
//    public ResponseEntity<MessageVo> restoreConfig() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setBwtConfigInfo")
//    public ResponseEntity<MessageVo> setBwtConfigInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setConfigBackupInfo")
//    public ResponseEntity<MessageVo> setConfigBackupInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setNtpInfo")
//    public ResponseEntity<MessageVo> setNtpInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setSmtpEventInfo")
//    public ResponseEntity<MessageVo> setSmtpEventInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setSmtpInfo")
//    public ResponseEntity<MessageVo> setSmtpInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setSnmpTrapInfo")
//    public ResponseEntity<MessageVo> setSnmpTrapInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setSystemConfigBackupInfo")
//    public ResponseEntity<MessageVo> setSystemConfigBackupInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/systemConfigBackup")
//    public ResponseEntity<MessageVo> systemConfigBackup() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/systemConfigbackupDelete")
//    public ResponseEntity<MessageVo> systemConfigbackupDelete() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/systemConfigbackupDownload")
//    public ResponseEntity<MessageVo> systemConfigbackupDownload() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/testSmtpConnection")
//    public ResponseEntity<MessageVo> testSmtpConnection() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("성공 코드")
//    			.data("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    

}
