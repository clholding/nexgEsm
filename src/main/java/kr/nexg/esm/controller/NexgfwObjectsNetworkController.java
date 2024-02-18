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
@RequestMapping("/nexgfw_objects_network")
public class NexgfwObjectsNetworkController {

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
    
    @PostMapping("/addObj")
    public ResponseEntity<MessageVo> addObj() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/add_group")
    public ResponseEntity<MessageVo> add_group() throws IOException  {
    	
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
    
    @PostMapping("/clone_group")
    public ResponseEntity<MessageVo> clone_group() throws IOException  {
    	
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
    
    @PostMapping("/deleteObj")
    public ResponseEntity<MessageVo> deleteObj() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/delete_group")
    public ResponseEntity<MessageVo> delete_group() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getNetworkZone")
    public ResponseEntity<MessageVo> getNetworkZone() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/loaddomaindb")
    public ResponseEntity<MessageVo> loaddomaindb() throws IOException  {
    	
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
    public ResponseEntity<MessageVo> modify() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/modifyObj")
    public ResponseEntity<MessageVo> modifyObj() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/modify_group")
    public ResponseEntity<MessageVo> modify_group() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/network")
    public ResponseEntity<MessageVo> network() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/networkObj")
    public ResponseEntity<MessageVo> networkObj() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/network_group")
    public ResponseEntity<MessageVo> network_group() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/networks")
    public ResponseEntity<MessageVo> networks() throws IOException  {
    	
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
