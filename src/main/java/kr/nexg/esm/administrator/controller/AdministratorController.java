package kr.nexg.esm.administrator.controller;

import java.nio.charset.Charset;
import java.util.ArrayList;
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

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.administrator.dto.AdministratorVo;
import kr.nexg.esm.administrator.service.AdministratorService;
import kr.nexg.esm.common.dto.MessageVo;
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
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = administratorService.getUserInfo(vo);
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
	* @ exception 예외사항
	*/
	@PostMapping("/getUser")
    public ResponseEntity<MessageVo> getUser(@RequestBody AdministratorVo vo) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = administratorService.getUser(vo);
            
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
	* @ return 없음
	* @ exception 예외사항
	*/
	@PostMapping("/delUser")
    public ResponseEntity<MessageVo> delUser(@RequestBody AdministratorVo vo) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	administratorService.delUser(vo);
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(0)
                	.entitys("")
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
