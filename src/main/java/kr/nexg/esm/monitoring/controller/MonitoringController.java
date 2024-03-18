package kr.nexg.esm.monitoring.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.protobuf.InvalidProtocolBufferException;

import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.monitoring.dto.MonitoringVo;
import kr.nexg.esm.monitoring.service.MonitoringService;
import kr.nexg.esm.nexgesm.emsg.Nexgfw_pb2;
import lombok.extern.slf4j.Slf4j;

/**
 * 모니터링
 * 
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/monitoring")
public class MonitoringController {
	
	@Autowired
	MonitoringService monitoringService;

	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/esmDevice")
    public ResponseEntity<MessageVo> esmDevice(@RequestBody MonitoringVo monitoringVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();

    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = monitoringService.esmDevice(monitoringVo);
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
	* -
	* -
	 * @throws InvalidProtocolBufferException 
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/device")
    public ResponseEntity<MessageVo> device(@RequestBody MonitoringVo monitoringVo) throws InvalidProtocolBufferException {
		// 객체 생성 및 수정
		int type = Nexgfw_pb2.CommandType.CT_GET.getNumber();
		int code = Nexgfw_pb2.CommandCode.CC_SYSINFO.getNumber();
		Nexgfw_pb2.Command command = Nexgfw_pb2.Command.newBuilder()
				.setType(type)
				.setCode(code)
				.setEsmId("00001111222233335")
				.build();
		
		// 객체 직렬화
		byte[] byteArray = command.toByteArray();

		// 객체 역직렬화
		Nexgfw_pb2.Command deserializedUserProfile = Nexgfw_pb2.Command.parseFrom(byteArray);
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/interface")
    public ResponseEntity<MessageVo> _interface(@RequestBody MonitoringVo monitoringVo) {
		
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/traffic")
    public ResponseEntity<MessageVo> traffic(@RequestBody MonitoringVo monitoringVo) {
		
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/list")
    public ResponseEntity<MessageVo> list(@RequestBody MonitoringVo monitoringVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        monitoringVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = monitoringService.list(monitoringVo);
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
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getDeviceInterfaceInfo")
    public ResponseEntity<MessageVo> getDeviceInterfaceInfo(@RequestBody MonitoringVo monitoringVo) {
		
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/getInterMonInfo")
    public ResponseEntity<MessageVo> getInterMonInfo(@RequestBody MonitoringVo monitoringVo) {
		
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/setInterMonInfo")
    public ResponseEntity<MessageVo> setInterMonInfo(@RequestBody MonitoringVo monitoringVo) {
		
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param MonitoringVo
	* @ return ResponseEntity
	*/
	@PostMapping("/topN")
    public ResponseEntity<MessageVo> topN(@RequestBody MonitoringVo monitoringVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        monitoringVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = monitoringService.topN(monitoringVo);
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
	
}
