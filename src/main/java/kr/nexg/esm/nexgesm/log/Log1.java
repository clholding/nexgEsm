package kr.nexg.esm.nexgesm.log;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

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
			MongoClientManager mongoClientManager = new MongoClientManager(mongoHost, mongoPort, mongoUsername, mongoPassword);	//몽고DB connection 연결
			MongoClient mongoClient = mongoClientManager.getMongoClient();
	        
			String start = (String) paramMap.get("start");
			String end = (String) paramMap.get("end");
			int skip = (int) paramMap.get("skip");
			int limit = (int) paramMap.get("limit");
			int count = 0;
			LocalDateTime localDateTime = DateUtil.getStringToLocalDateTime(end, "yyyy-MM-dd HH:mm:ss");
			
			
//			while(true) {
				String yyyyMMdd = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
				String HH = localDateTime.format(DateTimeFormatter.ofPattern("HH"));
				MongoDatabase database = mongoClient.getDatabase("db_" + yyyyMMdd);
		        MongoCollection<Document> collection = database.getCollection("doc_" + HH);
		        
		        Instant start_instant = DateUtil.getUtcTime(start, "yyyy-MM-dd HH:mm:ss");
		        Instant end_instant = DateUtil.getUtcTime(end, "yyyy-MM-dd HH:mm:ss");
		        
		        Document query = new Document("time",
	                    new Document("$gte", start_instant)
	                    .append("$lte", end_instant)
		        		);
		        
		        FindIterable<Document> result = collection.find(query)
		        		.skip(skip)
		        	    .limit(limit)
		        	    .sort(Sorts.descending("_id"));
		        try {
		        	for (Document doc : result) {
		                System.out.println(doc.toJson());
		            }
		        } finally {
			        mongoClientManager.close();
		        }
//			}
	        
			return null;
		}
		
	}
	
}
