package kr.nexg.esm.nexgesm.mariadb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.nexg.esm.nexgesm.mariadb.mapper.MonitorMapper;

@Component
public class Monitor {
	
	@Autowired
	MonitorMapper monitorMapper;
	
	@Component
	public class TopN {
		public List<Map<String, Object>> get_topn(String fid, int type, int topn) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("fid", fid);
			map.put("type", type);
			map.put("topn", topn);
			return monitorMapper.getMonitorTopN(map);
		}
	}
	
	@Component
	public class DeviceMonitor {
		public List<Map<String, Object>> get_monitor(String fid) {
			return monitorMapper.getDeviceStatus(fid);
		}
	}
	
	@Component
	public class DeviceRealtimeMonitor {
		public List<Map<String, Object>> get_alarminfo(String fid) {
			return null;
		}
		
		public List<Map<String, Object>> get_managed_nic(String fid) {
			return null;
		}
		
		public List<Map<String, Object>> get_monitoring_nic(String fid) {
			return null;
		}
		
		public List<Map<String, Object>> get_sysinfo(String fid) {
			return null;
		}
		
		public List<Map<String, Object>> get_monitor(String id, String ip, String mode) {
			return null;
		}
		
		public List<Map<String, Object>> get_interface(String id, String ip, String mode) {
			return null;
		}
		
		public List<Map<String, Object>> get_traffic(String ids, String ips, String names, String mode) {
			return null;
		}
	}
	
}
