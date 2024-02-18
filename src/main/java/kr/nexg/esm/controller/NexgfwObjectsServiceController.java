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
@RequestMapping("/nexgfw_objects_service")
public class NexgfwObjectsServiceController {

    @PostMapping("/add")
    public ResponseEntity<MessageVo> add() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MessageVo message = MessageVo.builder()
            	.status(StatusEnum.OK)
            	.message("성공 코드")
            	.data("")
            	.build();
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/clone")
    public ResponseEntity<MessageVo> clone()  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/delete")
    public ResponseEntity<MessageVo> delete() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/modify")
    public ResponseEntity<MessageVo> modify() throws IOException {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/service")
    public ResponseEntity<MessageVo> service() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    
    @PostMapping("/services")
    public ResponseEntity<MessageVo> services() throws IOException {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
}
