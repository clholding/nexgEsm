package kr.nexg.esm.nexgesm.mariadb;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kr.nexg.esm.nexgesm.mariadb.mapper.LogMapper;

@Component
public class Log {
	
	@Autowired
	LogMapper logMapper;
	
	@Component
	public class EsmAuditLog {
		public void esmlog(int level, String user, String host, String action) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("level", level);
			map.put("user", user);
			map.put("host", host);
			map.put("action", action);
			logMapper.addAuditLog(map);
		}
	}
}
