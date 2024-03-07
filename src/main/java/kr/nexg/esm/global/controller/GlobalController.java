package kr.nexg.esm.global.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.dto.MessageVo;
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
	* @ param AdministratorVo
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
	* @ param AdministratorVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getUserInfoByLogin")
    public ResponseEntity<MessageVo> getUserInfoByLogin(@RequestParam Map<String,Object> paramMap) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
//        	List<Map<String, Object>> list = globalService.getUserInfoByLogin(paramMap);
//            int totalCount = list.size();
        	
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
