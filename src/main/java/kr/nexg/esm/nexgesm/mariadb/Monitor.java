package kr.nexg.esm.nexgesm.mariadb;

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
	public class DeviceMonitor {
		public List<Map<String, Object>> get_monitor(String fid) {
			return monitorMapper.getDeviceStatus(fid);
		}
	}
	
}
