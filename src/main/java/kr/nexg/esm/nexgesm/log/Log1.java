package kr.nexg.esm.nexgesm.log;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		
		public List<Map<String, Object>> log_query(Map<String, Object> paramMap) {
			return null;
		}
		
	}
	
}
