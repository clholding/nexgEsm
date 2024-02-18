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
@RequestMapping("/devices")
public class DevicesController {

    @PostMapping("/addPrivateNetwork")
    public ResponseEntity<MessageVo> addPrivateNetwork() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        MessageVo message = MessageVo.builder()
            	.status(StatusEnum.OK)
            	.message("성공 코드")
            	.data("")
            	.build();
    	
        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/agentDownload")
    public ResponseEntity<MessageVo> agentDownload() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/applyDevice")
    public ResponseEntity<MessageVo> applyDevice() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/applyDeviceInterface")
    public ResponseEntity<MessageVo> applyDeviceInterface() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/changePrivateNetwork")
    public ResponseEntity<MessageVo> changePrivateNetwork() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/checkManagedCode")
    public ResponseEntity<MessageVo> checkManagedCode() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/delCandidate")
    public ResponseEntity<MessageVo> delCandidate() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/delDeviceInterface")
    public ResponseEntity<MessageVo> delDeviceInterface() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/delDeviceNGroup")
    public ResponseEntity<MessageVo> delDeviceNGroup() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/deviceAll")
    public ResponseEntity<MessageVo> deviceAll() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/deviceCandidate")
    public ResponseEntity<MessageVo> deviceCandidate() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getAlarmDeviceGroupListNDeviceListAll")
    public ResponseEntity<MessageVo> getAlarmDeviceGroupListNDeviceListAll() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getBwtDeviceGroupListNDeviceListAll")
    public ResponseEntity<MessageVo> getBwtDeviceGroupListNDeviceListAll() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceEixInfoList")
    public ResponseEntity<MessageVo> getDeviceEixInfoList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceFailInfo")
    public ResponseEntity<MessageVo> getDeviceFailInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceFailList")
    public ResponseEntity<MessageVo> getDeviceFailList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceGroupInfo")
    public ResponseEntity<MessageVo> getDeviceGroupInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceGroupList")
    public ResponseEntity<MessageVo> getDeviceGroupList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceInfo")
    public ResponseEntity<MessageVo> getDeviceInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceInfoList")
    public ResponseEntity<MessageVo> getDeviceInfoList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceInterface")
    public ResponseEntity<MessageVo> getDeviceInterface() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceInterfaceList")
    public ResponseEntity<MessageVo> getDeviceInterfaceList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceListByUser")
    public ResponseEntity<MessageVo> getDeviceListByUser() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceSimpleInfo")
    public ResponseEntity<MessageVo> getDeviceSimpleInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceStatus")
    public ResponseEntity<MessageVo> getDeviceStatus() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceTrackInfoList")
    public ResponseEntity<MessageVo> getDeviceTrackInfoList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getDeviceVrrpStateInfoList")
    public ResponseEntity<MessageVo> getDeviceVrrpStateInfoList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getPrivateNetworkList")
    public ResponseEntity<MessageVo> getPrivateNetworkList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getProductList")
    public ResponseEntity<MessageVo> getProductList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/getTreeInfo")
    public ResponseEntity<MessageVo> getTreeInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/removePrivateNetwork")
    public ResponseEntity<MessageVo> removePrivateNetwork() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/searchDeviceInfoList")
    public ResponseEntity<MessageVo> searchDeviceInfoList() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setDeviceGroup")
    public ResponseEntity<MessageVo> setDeviceGroup() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setDeviceGroupInfo")
    public ResponseEntity<MessageVo> setDeviceGroupInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setDeviceInfo")
    public ResponseEntity<MessageVo> setDeviceInfo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setDeviceInterface")
    public ResponseEntity<MessageVo> setDeviceInterface() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setDeviceNGroupName")
    public ResponseEntity<MessageVo> setDeviceNGroupName() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/setFailMemo")
    public ResponseEntity<MessageVo> setFailMemo() throws IOException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	MessageVo message = MessageVo.builder()
    			.status(StatusEnum.OK)
    			.message("성공 코드")
    			.data("")
    			.build();
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    @PostMapping("/updateDeviceInterface")
    public ResponseEntity<MessageVo> updateDeviceInterface() throws IOException  {
    	
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
