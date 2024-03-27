package kr.nexg.esm.nexgesm.mariadb;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import kr.nexg.esm.logs.dto.LogsConstants;
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
		
		public List<Map<String, Object>> get_log(String sdate, String edate, String skip, String limit, String level, String user, String msg) {
			Map<String, Object> map = new HashMap<>();
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			map.put("level", level);
			map.put("user", user);
			map.put("msg", msg);
			List<Map<String, Object>> list = logMapper.getEsmAuditLog(map);
			
			return list;
		}
		
		public String get_log_save(String user, String info, String sdate, String edate, String level) {
			int logType = LogsConstants.LOG_TYPE_ESMAUDIT;
			Map<String, Object> map = new HashMap<>();
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			map.put("level", level);
			map.put("user", user);
			map.put("msg", "");
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getEsmAuditLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'RebootLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
		}
	}
	
	@Component
	public class ResourceLog {
		public List<Map<String, Object>> get_log(String fid, String sdate, String edate, String skip, String limit) {
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			List<Map<String, Object>> list = logMapper.getResourceLog(map);
			
			return list;
		}
		
		public String get_log_save(String user, String info, String fid, String sdate, String edate) {
			int logType = LogsConstants.LOG_TYPE_RESOURCE;
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getResourceLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'ResourceLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
		}
	}
	
	@Component
	public class CommandLog {
		public List<Map<String, Object>> get_log(String fid, String sdate, String edate, String skip, String limit) {
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			List<Map<String, Object>> list = logMapper.getCommandLog(map);
			
			return list;
		}
		
		public String get_log_save(String user, String info, String fid, String sdate, String edate) {
			int logType = LogsConstants.LOG_TYPE_COMMAND;
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getCommandLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'CommandLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
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
		
		public List<Map<String, Object>> get_log(String fid, String sdate, String edate, String skip, String limit, String type, int mode) {
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			map.put("type", type);
			map.put("mode", mode);
			List<Map<String, Object>> list = logMapper.getFailLog(map);
			
			return list;
		}
		
		public String get_log_save(String user, String info, String fid, String sdate, String edate, String type) {
			int logType = LogsConstants.LOG_TYPE_FAIL;
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			map.put("type", type);
			map.put("mode", 0);
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getFailLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'FailLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
		}
	}
	
	@Component
	public class RebootLog {
		public List<Map<String, Object>> get_log(String fid, String sdate, String edate, String skip, String limit) {
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			List<Map<String, Object>> list = logMapper.getRebootLog(map);
			
			return list;
				
		}
		
		public String get_log_save(String user, String info, String fid, String sdate, String edate) {
			int logType = LogsConstants.LOG_TYPE_REBOOT;
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getRebootLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'RebootLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
		}
	}
	
	@Component
	public class AlarmLog {
		public List<Map<String, Object>> get_log(String fid, String sdate, String edate, String skip, String limit, String level) {
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			map.put("level", level);
			List<Map<String, Object>> list = logMapper.getAlarmLog(map);
			
			return list;
		}
		
		public String get_log_save(String user, String info, String fid, String sdate, String edate, String level) {
			int logType = LogsConstants.LOG_TYPE_ALARM;
			Map<String, Object> map = new HashMap<>();
			map.put("fid", fid);
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", "0");
			map.put("limit", LogsConstants.LOGSAVE_RECORD_LIMIT);
			map.put("level", level);
			
			Map<String, Object> boxMap = new HashMap<>();
			boxMap.put("logType", logType);
			boxMap.put("user", user);
			boxMap.put("info", info);
			int select_id = (int) logMapper.addLogBox(boxMap).get("select_id");
			String logbox_id = Integer.toString(select_id);
			List<Map<String, Object>> list = logMapper.getAlarmLog(map);
			
			//### TO-DO ###
//			from ..command.background import Background
//		      bg = Background()
//		      downinfo = dict()
//		      downinfo['query'] = save_call_string
//		      downinfo['id'] = logbox_id
//		      downinfo['dbtype'] = 'AlarmLog'
//
//		      bg.run_command(CST_DOWN_ETCLOG, json.dumps(downinfo))
			
			return logbox_id;
		}
	}
	
	@Component
	public class LogBox {
		public List<Map<String, Object>> get_log_box(String sdate, String edate, String skip, String limit) {
			Map<String, Object> map = new HashMap<>();
			map.put("sdate", sdate);
			map.put("edate", edate);
			map.put("skip", skip);
			map.put("limit", limit);
			return logMapper.getLogBox(map);
		}
		
		public Map<String, Object> del_log_box(List<String> ids, String path, String user) throws FileNotFoundException {
			for(String id : ids) {
				String log_file = path + id + ".csv";
				File file = new File(log_file);
				if(file.exists()) {
					file.delete();
				}else {
					throw new FileNotFoundException("file not found!");
				}
			}
			return logMapper.delLogBox(String.join(",", ids), user);
		}
	}
	
	@Component
	public class LogInput {
		public List<Map<String, Object>> get_data(String fid) {
			return logMapper.getInputLog(fid);
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
