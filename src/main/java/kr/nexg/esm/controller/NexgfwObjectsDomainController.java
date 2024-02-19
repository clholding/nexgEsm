package kr.nexg.esm.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.StatusEnum;
import kr.nexg.esm.dto.MessageVo;

@RestController
@RequestMapping("/nexgfw_objects_domain")
public class NexgfwObjectsDomainController {

//    @PostMapping("/addObj")
//    public ResponseEntity<MessageVo> addObj() throws IOException  {
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
//    @PostMapping("/deleteObj")
//    public ResponseEntity<MessageVo> deleteObj() throws IOException  {
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
//    @PostMapping("/domaindb")
//    public ResponseEntity<MessageVo> domaindb() throws IOException  {
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
}
