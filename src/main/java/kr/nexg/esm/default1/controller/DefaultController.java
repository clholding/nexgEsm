package kr.nexg.esm.default1.controller;

import java.io.IOException;
import java.nio.charset.Charset;

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
import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.jwt.dto.AuthVo;
import kr.nexg.esm.jwt.dto.TokenVo;
import kr.nexg.esm.jwt.service.AuthService;

@RestController
@RequestMapping("/default")
public class DefaultController {

	@Autowired
	AuthService authService;
	
//    @PostMapping("/call")
//    public ResponseEntity<MessageVo> call() throws IOException  {
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
//    @PostMapping("/change_password")
//    public ResponseEntity<MessageVo> change_password() throws IOException  {
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
//    @PostMapping("/changepassword")
//    public ResponseEntity<MessageVo> changepassword() throws IOException  {
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
//    @PostMapping("/checkOn")
//    public ResponseEntity<MessageVo> checkOn() throws IOException  {
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
//    @PostMapping("/checkpasswordrule")
//    public ResponseEntity<MessageVo> checkpasswordrule() throws IOException  {
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
//    @PostMapping("/createSocketNexGFW")
//    public ResponseEntity<MessageVo> createSocketNexGFW() throws IOException  {
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
//    @PostMapping("/downloadAgent")
//    public ResponseEntity<MessageVo> downloadAgent() throws IOException  {
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
//    @PostMapping("/downloadIPS")
//    public ResponseEntity<MessageVo> downloadIPS() throws IOException  {
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
//    @PostMapping("/downloadOS")
//    public ResponseEntity<MessageVo> downloadOS() throws IOException  {
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
//    @PostMapping("/error")
//    public ResponseEntity<MessageVo> error() throws IOException  {
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
//    @PostMapping("/forcedLogin")
//    public ResponseEntity<MessageVo> forcedLogin() throws IOException  {
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
//    @PostMapping("/group")
//    public ResponseEntity<MessageVo> group() throws IOException  {
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
//    @PostMapping("/index")
//    public ResponseEntity<MessageVo> index() throws IOException  {
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
//    @PostMapping("/intergrated")
//    public ResponseEntity<MessageVo> intergrated() throws IOException  {
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
//    @PostMapping("/login")
//    public ResponseEntity<MessageVo> login() throws IOException  {
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
//    @PostMapping("/loginCancel")
//    public ResponseEntity<MessageVo> loginCancel() throws IOException  {
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
    
//    @PostMapping("/logincheck")
//    public ResponseEntity<MessageVo> logincheck() throws IOException  {
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
    
	@PostMapping("/logincheck")
	public TokenVo login(@RequestBody AuthVo authVo) {

		TokenVo tokenVo = authService.login(authVo);
		
		return tokenVo;
	}
	
//    @PostMapping("/logout")
//    public ResponseEntity<MessageVo> logout() throws IOException  {
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
//    @PostMapping("/nss_changepassword")
//    public ResponseEntity<MessageVo> nss_changepassword() throws IOException  {
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
//    @PostMapping("/nss_login_check")
//    public ResponseEntity<MessageVo> nss_login_check() throws IOException  {
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
//    @PostMapping("/nss_logout")
//    public ResponseEntity<MessageVo> nss_logout() throws IOException  {
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
//    @PostMapping("/report")
//    public ResponseEntity<MessageVo> report() throws IOException  {
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
//    @PostMapping("/sessionTimeCheck")
//    public ResponseEntity<MessageVo> sessionTimeCheck() throws IOException  {
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
//    @PostMapping("/sessionTimeOut")
//    public ResponseEntity<MessageVo> sessionTimeOut() throws IOException  {
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
//    @PostMapping("/single")
//    public ResponseEntity<MessageVo> single() throws IOException  {
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
//    @PostMapping("/user")
//    public ResponseEntity<MessageVo> user() throws IOException  {
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
