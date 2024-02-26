package kr.nexg.esm.administrator.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.StatusEnum;
import kr.nexg.esm.administrator.dto.AdministratorVo;
import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.administrator.service.AdministratorService;
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

	/**
	* 관리자정보 조회
	* 
	* @ param AdministratorVo
	* @ return ResponseEntity
	* @ exception 예외사항
	*/
	@PostMapping("/getUserInfo")
    public ResponseEntity<MessageVo> getUserInfo(@RequestBody AdministratorVo vo) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        if(vo.getAdminID().isBlank()) {
        	vo.setAdminID("1");
        }
        
        List<Map<String, Object>> list = administratorService.getUserInfo(vo);
        int totalCount = list.size();
        
        MessageVo message;
        
        try {
        	message = MessageVo.builder()
                	.success("true")
                	.message("성공 코드")
                	.totalCount(totalCount)
                	.entitys(list)
                	.build();
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("에러 코드")
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
	* @ exception 예외사항
	*/
	@PostMapping("/getUser")
    public ResponseEntity<MessageVo> getUser(@RequestBody AdministratorVo vo) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	message = MessageVo.builder()
                	.success("true")
                	.message("성공 코드")
                	.totalCount(0)
                	.entitys("")
                	.build();
		} catch (Exception e) {
			message = MessageVo.builder()
	            	.success("false")
	            	.message("에러 코드")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }
}
