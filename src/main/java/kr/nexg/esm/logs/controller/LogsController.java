package kr.nexg.esm.logs.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.global.controller.GlobalController;
import kr.nexg.esm.logs.dto.LogsVo;
import kr.nexg.esm.logs.service.LogsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그
 * 
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/logs")
public class LogsController {
	
	@Autowired
	LogsService logsService;

	/**
	* 메인 > 탑 메뉴 메인 > 초기 대시보드
	* 대쉬보드, 최근 장애 장비
	* 
	* @ param Map
	* @ return ResponseEntity
	*/
	@PostMapping("/lastFailDevice")
    public ResponseEntity<MessageVo> lastFailDevice(@RequestBody LogsVo logsVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        logsVo.setSessionId(sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = logsService.lastFailDevice(logsVo);
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
