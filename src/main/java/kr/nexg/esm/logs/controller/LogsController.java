package kr.nexg.esm.logs.controller;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
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

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import kr.nexg.esm.common.MongoClientManager;
import kr.nexg.esm.common.dto.MessageVo;
import kr.nexg.esm.common.dto.MongoVo;
import kr.nexg.esm.logs.dto.LogsVo;
import kr.nexg.esm.logs.service.LogsService;
import lombok.extern.slf4j.Slf4j;

/**
 * 로그
 * 
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/logs")
public class LogsController {
	
	@Value("${logbox.path}")
    private String logboxPath;
	
	@Value("${mongo.host}")
    private String mongoHost;
	
	@Value("${mongo.port}")
    private int mongoPort;
	
	@Value("${mongo.username:}")
    private String mongoUsername;
	
	@Value("${mongo.password:}")
    private String mongoPassword;
	
	@Autowired
	LogsService logsService;

	/**
	* 메인 > 탑 메뉴 메인 > 초기 대시보드
	* 대쉬보드, 최근 장애 장비
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/lastFailDevice")
    public ResponseEntity<MessageVo> lastFailDevice(@RequestBody LogsVo logsVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        
        logsVo.setSessionId(sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = logsService.lastFailDevice(logsVo);
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
	* 메인 > 탑 메뉴 메인 > 초기 대시보드
	* 주간로그 발생 통계
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/weeklyLog")
    public ResponseEntity<MessageVo> weeklyLog(@RequestBody LogsVo logsVo) {
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = logsService.weeklyLog(logsVo);
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
	* logbox 파일 다운로드 처리
	 * @throws Exception 
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/logBoxDownload")
    public ResponseEntity<Resource> logBoxDownload(@RequestBody LogsVo logsVo) throws Exception {
    	
		HttpHeaders headers= new HttpHeaders();
        
        String rs_id = logsVo.getId();
        
        Resource resource = null;
        
        try {
        	Path filePath = Paths.get(logboxPath + rs_id + ".csv");
        	resource = new InputStreamResource(Files.newInputStream(filePath));
        	headers.setContentType(MediaType.parseMediaType("application/csv"));
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename(rs_id+".csv").build());
            
		} catch (Exception e) {
			log.error("Error : ", e);
			throw new Exception();
		}
        
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
	
	/**
	* -
	* -
	 * @throws Exception 
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/logBoxList")
    public ResponseEntity<MessageVo> logBoxList(@RequestBody LogsVo logsVo) throws Exception {
    	
		HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
            
            Map<String, Object> result = logsService.logBoxList(logsVo);
        	List<Map<String, Object>> list = (List<Map<String, Object>>) result.get("result");
            int totalCount = (int) result.get("totalCount");
        	
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
	 * @throws Exception 
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/delLogBox")
    public ResponseEntity<MessageVo> delLogBox(@RequestBody LogsVo logsVo) throws Exception {
    	
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        logsVo.setSessionId(sessionId);
        
        logsVo.setPath(logboxPath);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	logsService.delLogBox(logsVo);
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("삭제가 완료되었습니다.")
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
	
	/**
	* 메인 > 탑 메뉴 메인 > 초기 대시보드
	* 장비 장애 로그 패널
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/etcLogs")
    public ResponseEntity<MessageVo> etcLogs(@RequestBody LogsVo logsVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        logsVo.setSessionId(sessionId);
    	
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	List<Map<String, Object>> list = logsService.etcLogs(logsVo);
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
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/etcDownload")
    public ResponseEntity<MessageVo> etcDownload(@RequestBody LogsVo logsVo) {
		
		SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        
        String sessionId = authentication.getName();
        logsVo.setSessionId(sessionId);
		
    	HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
        	
        	Map<String, String> result = logsService.etcDownload(logsVo);
        	
        	message = MessageVo.builder()
                	.success("true")
                	.message("로그 > 보관함에 로그가 저장되었습니다.")
                	.totalCount(0)
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
	
	/**
	* -
	* -
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/logs")
    public ResponseEntity<MessageVo> logs(@RequestBody LogsVo logsVo) {
		
		MongoVo mongoVo = new MongoVo();
		mongoVo.setMongoHost(mongoHost);
		mongoVo.setMongoPort(mongoPort);
		mongoVo.setMongoUsername(mongoUsername);
		mongoVo.setMongoPassword(mongoPassword);
//		MongoClientManager mongoClientManager = new MongoClientManager(mongoHost, mongoPort, mongoUsername, mongoPassword);
//		MongoClient mongoClient = mongoClientManager.getMongoClient();
//        MongoDatabase database = mongoClient.getDatabase("db_20240321");
//        MongoCollection<Document> collection = database.getCollection("doc_00");
//        
//        MongoCursor<Document> cursor = collection.find()
//        	    .limit(3)
//        	    .iterator();
//        try {
//            while (cursor.hasNext()) {
//                Document document = cursor.next();
//                System.out.println(document.toJson());
//            }
//        } finally {
//            cursor.close();
//        }
//
//        // 연결을 닫습니다.
//        mongoClientManager.close();
		
		HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        
        MessageVo message;
        
        try {
            
        	List<Map<String, Object>> list = logsService.logs(logsVo, mongoVo);
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
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/download")
    public ResponseEntity<MessageVo> download(@RequestBody LogsVo logsVo) {
		return null;
    }
	
	/**
	* -
	* -
	* 
	* @ param LogsVo
	* @ return ResponseEntity
	*/
	@PostMapping("/recent_ipslogs")
    public ResponseEntity<MessageVo> recent_ipslogs(@RequestBody LogsVo logsVo) {
		return null;
    }
	
}
