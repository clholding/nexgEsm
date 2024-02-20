package kr.nexg.esm.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.nexg.esm.common.StatusEnum;
import kr.nexg.esm.dto.MessageVo;


@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

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
//    @PostMapping("/checkAgentIntegrity")
//    public ResponseEntity<MessageVo> checkAgentIntegrity() throws IOException  {
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
//    @PostMapping("/getAlarmInfo")
//    public ResponseEntity<MessageVo> getAlarmInfo() throws IOException  {
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
//    @PostMapping("/getConfigBackupInfo")
//    public ResponseEntity<MessageVo> getConfigBackupInfo() throws IOException  {
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
//    @PostMapping("/getConfigBackupList")
//    public ResponseEntity<MessageVo> getConfigBackupList() throws IOException  {
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
//    @PostMapping("/getDeviceIntegrityInfo")
//    public ResponseEntity<MessageVo> getDeviceIntegrityInfo() throws IOException  {
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
//    @PostMapping("/getDeviceRegisterConfig")
//    public ResponseEntity<MessageVo> getDeviceRegisterConfig() throws IOException  {
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
//    @PostMapping("/getIntegrityInfo")
//    public ResponseEntity<MessageVo> getIntegrityInfo() throws IOException  {
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
//    @PostMapping("/getInterfaceConfig")
//    public ResponseEntity<MessageVo> getInterfaceConfig() throws IOException  {
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
//    @PostMapping("/getLogDiskInfo")
//    public ResponseEntity<MessageVo> getLogDiskInfo() throws IOException  {
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
//    @PostMapping("/getNtpInfo")
//    public ResponseEntity<MessageVo> getNtpInfo() throws IOException  {
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
//    @PostMapping("/getSmtpEventInfo")
//    public ResponseEntity<MessageVo> getSmtpEventInfo() throws IOException  {
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
//    @PostMapping("/getSmtpInfo")
//    public ResponseEntity<MessageVo> getSmtpInfo() throws IOException  {
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
//    @PostMapping("/getSnmpInfo")
//    public ResponseEntity<MessageVo> getSnmpInfo() throws IOException  {
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
//    @PostMapping("/getSnmpTrapInfo")
//    public ResponseEntity<MessageVo> getSnmpTrapInfo() throws IOException  {
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
//    @PostMapping("/getSystemConfigBackupInfo")
//    public ResponseEntity<MessageVo> getSystemConfigBackupInfo() throws IOException  {
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
//    @PostMapping("/getSystemConfigBackupList")
//    public ResponseEntity<MessageVo> getSystemConfigBackupList() throws IOException  {
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
//    @PostMapping("/getSystemInfo")
//    public ResponseEntity<MessageVo> getSystemInfo() throws IOException  {
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
//    @PostMapping("/setAlarmInfo")
//    public ResponseEntity<MessageVo> setAlarmInfo() throws IOException  {
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
//    @PostMapping("/setDeviceRegisterConfig")
//    public ResponseEntity<MessageVo> setDeviceRegisterConfig() throws IOException  {
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
//    @PostMapping("/setInterfaceConfig")
//    public ResponseEntity<MessageVo> setInterfaceConfig() throws IOException  {
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
//    @PostMapping("/setLogDiskInfo")
//    public ResponseEntity<MessageVo> setLogDiskInfo() throws IOException  {
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
//    @PostMapping("/setSnmpInfo")
//    public ResponseEntity<MessageVo> setSnmpInfo() throws IOException  {
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
//    @PostMapping("/updateIntegrity")
//    public ResponseEntity<MessageVo> updateIntegrity() throws IOException  {
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
