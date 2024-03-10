package kr.nexg.esm.nexgesm.mariadb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
	
	@Component
	public class FailLog {
		public List<Map<String, Object>> get_lastfaildevice(String user, int mode, String skip, String limit) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("user", user);
			map.put("mode", mode);
			map.put("skip", skip);
			map.put("limit", limit);
			List<Map<String, Object>> result = logMapper.getLastFailDevice(map);
			return result;
		}
	}
	
	@Component
	public class EventLog {
		public List<Map<String, Object>> get_event(String user, String mode, int interval_seconds) {
			List<Map<String, Object>> list = logMapper.getDeviceListByLogin(user, 1, mode);
			List<String> device_list_from_user = new ArrayList<String>();
			for(int i=0; i<list.size(); i++) {
				device_list_from_user.add((String) list.get(i).get("id"));
			}
			
			int interval = 5;
			if(interval_seconds != interval) {
				interval = interval_seconds;
			}
			String ids = String.join(",", device_list_from_user);
			List<Map<String, Object>> result = logMapper.getRealtimeEvent(ids, interval);
			
			return result;
		}
	}
}
