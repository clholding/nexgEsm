package kr.nexg.esm.nexgesm.log;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import kr.nexg.esm.common.MongoClientManager;
import kr.nexg.esm.common.dto.MongoVo;
import kr.nexg.esm.common.util.DateUtil;
import kr.nexg.esm.nexgesm.mariadb.mapper.LogMapper;

@Component
public class Log1 {
	
	@Autowired
	LogMapper logMapper;
	
	@Component
	public class Meta {
		
//		private List<Integer> fid;
//		private String hostip;
//		private String start;
//		private String end;
//		private List<String> fip; 
//		private int log; 
//		private String header; 
//		private int skip; 
//		private int limit;
//		
//		public Meta(List<Integer> fid, String hostip, String start, String end, List<String> fip, int log, String header, int skip, int limit) {
//			this.fid = fid;
//			this.hostip = hostip;
//			this.start = start;
//			this.end = end;
//			this.fip = fip;
//			this.log = log;
//			this.header = header;
//			this.skip = skip;
//			this.limit = limit;
//		}
		
		public List<Map<String, Object>> log_query(Map<String, Object> paramMap, MongoVo mongoVo) throws Exception {
			String mongoHost = mongoVo.getMongoHost();
			String mongoPort = Integer.toString(mongoVo.getMongoPort());
			String mongoUsername = mongoVo.getMongoUsername();
			String mongoPassword = mongoVo.getMongoPassword();
			MongoClientManager mongoClientManager = new MongoClientManager(mongoHost, mongoPort, mongoUsername, mongoPassword);
			MongoClient mongoClient = mongoClientManager.getMongoClient();
	        MongoDatabase database = mongoClient.getDatabase("db_20240321");
	        MongoCollection<Document> collection = database.getCollection("doc_00");
	        
	        String sDate = "2024-03-20 23:59:59";
	        Instant instant = DateUtil.getUtcTime(sDate, "yyyy-MM-dd HH:mm:ss");
	        
	        Document query = new Document("time",
                    new Document("$gte", Date.from(instant)));
	        
	        MongoCursor<Document> cursor = collection.find(query)
	        	    .limit(3)
	        	    .iterator();
	        try {
	            while (cursor.hasNext()) {
	                Document document = cursor.next();
	                System.out.println(document.toJson());
	            }
	        } finally {
	            cursor.close();
		        mongoClientManager.close();
	        }
	
	        
			return null;
		}
		
	}
	
}
