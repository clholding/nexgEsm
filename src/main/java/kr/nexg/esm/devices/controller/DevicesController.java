package kr.nexg.esm.devices.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.nexg.esm.common.StatusEnum;
import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.util.ClientIpUtil;
import kr.nexg.esm.devices.dto.DevicesVo;
import kr.nexg.esm.devices.service.DevicesService;
import kr.nexg.esm.nexgesm.command.Device;
import kr.nexg.esm.nexgesm.mariadb.Log;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/devices")
public class DevicesController {

	@Autowired
	DevicesService devicesService;
	
	@Autowired
	Log.EsmAuditLog esmAuditLog;
	
	@Autowired
	Device device;
	
//    @PostMapping("/addPrivateNetwork")
//    public ResponseEntity<MessageVo> addPrivateNetwork() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//        MessageVo message = MessageVo.builder()
//            	.status(StatusEnum.OK)
//            	.message("")
//            	.entitys("")
//            	.build();
//    	
//        return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/agentDownload")
//    public ResponseEntity<MessageVo> agentDownload() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
	/*
	 * TopNav > 적용
	 */
    @PostMapping("/applyDevice")
    public ResponseEntity<MessageVo> applyDevice(HttpServletRequest request) throws IOException  {
    	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
            int totalCount = 0;
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys("")
                	.build();
        	
        	device.apply_device(sessionId, clientIp);
        	
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
    
//    @PostMapping("/applyDeviceInterface")
//    public ResponseEntity<MessageVo> applyDeviceInterface() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/changePrivateNetwork")
//    public ResponseEntity<MessageVo> changePrivateNetwork() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 

	/*
	 * DeviceFinder > 개별정보 > 정보 > 기본정보 > 관리번호 중복체크
	 */
    @PostMapping("/checkManagedCode")
    public ResponseEntity<MessageVo> checkManagedCode(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        Map<String, Object> result = devicesService.checkManagedCode(devicesVo);
        
        try {
        	
            int totalCount = 0;
        	message = MessageVo.builder()
                	.success(String.valueOf(result.get("success")))
                	.message(String.valueOf(result.get("message")))
                	.totalCount(totalCount)
                	.entitys("")
                	.build();
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
					.success(String.valueOf(result.get("success")))
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
		} 
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    /*
     * DeviceTree > 설정 > 장비추가 > 삭제(리스트에서 장비 삭제)
     */      
    @PostMapping("/delCandidate")
    public ResponseEntity<MessageVo> delCandidate(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.delCandidate(devicesVo);
            int totalCount = 0;
            
            if("0".equals(result.get("col"))) {
            	
            	message = MessageVo.builder()
                    	.success("false")
                    	.message("장비 삭제 실패")
                    	.totalCount(totalCount)
                    	.entitys("")
                    	.build();
            }
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("장비 삭제 성공")
                	.totalCount(totalCount)
                	.entitys(result)
                	.build();
        	
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("db connection error")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}  
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
//    
//    @PostMapping("/delDeviceInterface")
//    public ResponseEntity<MessageVo> delDeviceInterface() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
    /*
     * DeviceTree > 설정 트리 > 특정 장비를 선택 삭제
     */    
    @PostMapping("/delDeviceNGroup")
    public ResponseEntity<MessageVo> delDeviceNGroup(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.delDeviceNGroup(devicesVo);
            int totalCount = 0;
        	
        	message = MessageVo.builder()
                	.success(String.valueOf(result.get("success")))
                	.message(String.valueOf(result.get("message")))
                	.totalCount(totalCount)
                	.entitys("")
                	.build();
        	
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("no request datas")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		} 
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    /*
     * DeviceFinder
     * 장비관리 > 장비 추가 > 대상장비 선택
     */
    @PostMapping("/deviceAll")
    public ResponseEntity<MessageVo> deviceAll(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.deviceAll(devicesVo);
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
    
    /*
     * 장비관리 > 장비추가리스트
     */
    @PostMapping("/deviceCandidate")
    public ResponseEntity<MessageVo> deviceCandidate(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.deviceCandidate(devicesVo);
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
    
    /*
     * 메인 화면 > 탑메뉴 > 시스템 설정 > 알람 > 장비/그릅 임계치 설정
     */
    @PostMapping("/getAlarmDeviceGroupListNDeviceListAll")
    public ResponseEntity<MessageVo> getAlarmDeviceGroupListNDeviceListAll(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getAlarmDeviceGroupListNDeviceListAll(devicesVo);
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
//    
//    @PostMapping("/getBwtDeviceGroupListNDeviceListAll")
//    public ResponseEntity<MessageVo> getBwtDeviceGroupListNDeviceListAll() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/getDeviceEixInfoList")
//    public ResponseEntity<MessageVo> getDeviceEixInfoList() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 

//	/*
//	 * 제품실패 정보
//	 */
//    @PostMapping("/getDeviceFailInfo")
//    public ResponseEntity<MessageVo> getDeviceFailInfo(@RequestParam Map<String,String> paramMap) throws IOException, ParseException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//
//    	
//        MessageVo message;
//        
//        try {
//        	
//        	List<Map<String, Object>> list = devicesService.getDeviceFailInfo(paramMap);
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
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
	/*
	 * DeviceFinder > 개별정보 > 정보 > 장애내역
	 */    
    @PostMapping("/getDeviceFailList")
    public ResponseEntity<MessageVo> getDeviceFailList(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getDeviceFailList(devicesVo);
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
	            	.message("장애내역 조회중 오류가 발생하였습니다.")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}    	
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    /*
     * DeviceFinder > 그룹정보 > 정보 > 기본정보
     */
    @PostMapping("/getDeviceGroupInfo")
    public ResponseEntity<MessageVo> getDeviceGroupInfo(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.getDeviceGroupInfo(devicesVo);
            int totalCount = 0;
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(result)
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
    
    /*
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 > 그룹
     * 장비관리 > 장비 추가 > 그룹
     */
    @PostMapping("/getDeviceGroupList")
    public ResponseEntity<MessageVo> getDeviceGroupList() throws IOException, ParseException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getDeviceGroupList();
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
    
    /*
     * DeviceFinder > 개별정보 > 정보 > 기본정보
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 
     */
    @PostMapping("/getDeviceInfo")
    public ResponseEntity<MessageVo> getDeviceInfo(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.getDeviceInfo(devicesVo);
            int totalCount = 0;
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("")
                	.totalCount(totalCount)
                	.entitys(result)
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
    

    @PostMapping("/getDeviceInfoList")
    public ResponseEntity<MessageVo> getDeviceInfoList(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getDeviceInfoList(devicesVo);
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
    
    /*
     * DeviceFinder > 개별정보 > 정보 > 인터페이스
     */
    @PostMapping("/getDeviceInterface")
    public ResponseEntity<MessageVo> getDeviceInterface(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getDeviceInterface(devicesVo);
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
    
//    @PostMapping("/getDeviceInterfaceList")
//    public ResponseEntity<MessageVo> getDeviceInterfaceList() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/getDeviceListByUser")
//    public ResponseEntity<MessageVo> getDeviceListByUser() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/getDeviceSimpleInfo")
//    public ResponseEntity<MessageVo> getDeviceSimpleInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
    
    /*
     * DeviceFinder > 개별정보 > 장비미리보기
     */    
    @PostMapping("/getDeviceStatus")
    public ResponseEntity<MessageVo> getDeviceStatus(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getDeviceStatus(devicesVo);
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
//    
//    @PostMapping("/getDeviceTrackInfoList")
//    public ResponseEntity<MessageVo> getDeviceTrackInfoList() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/getDeviceVrrpStateInfoList")
//    public ResponseEntity<MessageVo> getDeviceVrrpStateInfoList() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
//    @PostMapping("/getPrivateNetworkList")
//    public ResponseEntity<MessageVo> getPrivateNetworkList() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
    /*
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 제품명
     * DeviceFinder > 그룹 상세정보 > 자간관리 > 검색 > 추가검색조건 > 제품명
     * 장비관리 > 장비 추가 > 제품명
     */
    @PostMapping("/getProductList")
    public ResponseEntity<MessageVo> getProductList(@RequestBody DevicesVo devicesVo) throws IOException, ParseException  {
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.getProductList(devicesVo);
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
    
//    @PostMapping("/getTreeInfo")
//    public ResponseEntity<MessageVo> getTreeInfo() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/removePrivateNetwork")
//    public ResponseEntity<MessageVo> removePrivateNetwork() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
	/*
	 * 메인화면 > SideBar > 자산이력
	 */
    @PostMapping("/searchDeviceInfoList")
    public ResponseEntity<MessageVo> searchDeviceInfoList(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = devicesService.searchDeviceInfoList(devicesVo);
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
    
    /*
     * DeviceTree > 설정 > 특정 장비 그룹 지정
     */    
    @PostMapping("/setDeviceGroup")
    public ResponseEntity<MessageVo> setDeviceGroup(@RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.setDeviceGroup(devicesVo);
        	
        	message = MessageVo.builder()
                	.success(String.valueOf(result.get("success")))
                	.message(String.valueOf(result.get("message")))
                	.totalCount(Integer.parseInt((String) result.get("total")))
                	.entitys("")
                	.build();
        	
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("no request datas")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
		}
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    /*
     * DeviceFinder > 그룹정보 > 정보 > 기본정보 > 저장
     */
    @PostMapping("/setDeviceGroupInfo")
    public ResponseEntity<MessageVo> setDeviceGroupInfo(HttpServletRequest request, @RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.setDeviceGroupInfo(devicesVo);
        	
        	String eMsg = (String) result.get("eMsg");
        	String success = (String) result.get("success");
        	
            int totalCount = 0;
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message(String.valueOf(result.get("message")))
                	.totalCount(totalCount)
                	.entitys("")
                	.build();
			
        	if("success".equals(success)) {
        		esmAuditLog.esmlog(6, sessionId, clientIp, eMsg);
        	}else {
        		esmAuditLog.esmlog(4, sessionId, clientIp, eMsg);
        	}
        	
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(4, sessionId, clientIp, "A failure has occurred.");
		} 
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
    /*
     * DeviceFinder -> 개별정보 -> 정보 -> 기본정보 -> 저장
     */
    @PostMapping("/setDeviceInfo")
    public ResponseEntity<MessageVo> setDeviceInfo(HttpServletRequest request, @RequestBody DevicesVo devicesVo) throws IOException  {
    	
    	log.info("devicesVo : "+devicesVo);
    	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        String clientIp = ClientIpUtil.getClientIP(request);
        
    	HttpHeaders headers= new HttpHeaders();
    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
    	
        MessageVo message;
        
        try {
        	
        	Map<String, Object> result = devicesService.setDeviceInfo(devicesVo);
        	
        	String eMsg = (String) result.get("eMsg");
        	String success = (String) result.get("success");
        	
            int totalCount = 0;
        	
        	message = MessageVo.builder()
                	.success(success)
                	.message(String.valueOf(result.get("message")))
                	.totalCount(totalCount)
                	.entitys("")
                	.build();
        	
        	if("success".equals(success)) {
        		esmAuditLog.esmlog(6, sessionId, clientIp, eMsg);
        	}else {
        		esmAuditLog.esmlog(4, sessionId, clientIp, eMsg);
        	}
        	
		} catch (Exception e) {
			log.error("Error : ", e);
			message = MessageVo.builder()
	            	.success("false")
	            	.message("")
	            	.errMsg(e.getMessage())
	            	.errTitle("")
	            	.build();
			
			esmAuditLog.esmlog(4, sessionId, clientIp, "A failure has occurred.");
		} 
    	
    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
    	
    } 
    
//    @PostMapping("/setDeviceInterface")
//    public ResponseEntity<MessageVo> setDeviceInterface() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
//    
//    @PostMapping("/setDeviceNGroupName")
//    public ResponseEntity<MessageVo> setDeviceNGroupName() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 

	/*
	 * 메모내용 수정
	 */
//    @PostMapping("/setFailMemo")
//    public ResponseEntity<MessageVo> setFailMemo(@RequestParam Map<String,String> paramMap) throws IOException, ParseException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	
//        MessageVo message;
//        
//        try {
//        	
//        	int result = devicesService.setFailMemo(paramMap);
//            int totalCount = 0;
//        	
//        	message = MessageVo.builder()
//                	.success("true")
//                	.message("메모내용이 입력되었습니다.")
//                	.totalCount(totalCount)
//                	.entitys(result)
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
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
    
//    @PostMapping("/updateDeviceInterface")
//    public ResponseEntity<MessageVo> updateDeviceInterface() throws IOException  {
//    	
//    	HttpHeaders headers= new HttpHeaders();
//    	headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//    	
//    	MessageVo message = MessageVo.builder()
//    			.status(StatusEnum.OK)
//    			.message("")
//    			.entitys("")
//    			.build();
//    	
//    	return new ResponseEntity<>(message, headers, HttpStatus.OK);
//    	
//    } 
}

