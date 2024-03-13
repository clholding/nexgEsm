package kr.nexg.esm.monitoring.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.monitoring.dto.MonitoringVo;
import kr.nexg.esm.monitoring.mapper.MonitoringMapper;
import kr.nexg.esm.nexgesm.mariadb.Monitor;
import kr.nexg.esm.util.mode_convert;

@Service
public class MonitoringService {
	
	@Autowired
	MonitoringMapper monitoringMapper;
	
	@Autowired
	Monitor.DeviceMonitor deviceMonitor;
	
	public List<Map<String, Object>> esmDevice(MonitoringVo monitoringVo) throws Exception{
		
		return null;
	}
	
	public List<Map<String, Object>> list(MonitoringVo monitoringVo) throws Exception{
		
		String sessionId = monitoringVo.getSessionId();
		String rs_mode = monitoringVo.getMode();
		String rs_groupID = monitoringVo.getGroupID();
		
		
		List<String> rs_deviceIDs = monitoringVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		
		if(rs_groupID.isBlank()) {
			rs_deviceIDs = new ArrayList<String>();
		}else {
			int mode = mode_convert.convert_modedata(rs_mode);
			rs_deviceIDs = monitoringMapper.getGroupToDeviceListByLogin(rs_groupID, sessionId, Integer.toString(mode));
		}
		
		List<Map<String, Object>> result = new ArrayList<>();
		
//		for(int i=0; i<list.size(); i++) {
//			
//			Map<String, Object> map = new LinkedHashMap<>();
//			String failCount = String.valueOf(list.get(i).get("fail_count"));
//			String totalCount = String.valueOf(list.get(i).get("total_count"));
//			String inspectCount = String.valueOf(list.get(i).get("inspect_count"));
//			int normalCount = Integer.parseInt(totalCount) - Integer.parseInt(failCount) - Integer.parseInt(inspectCount);
//			map.put("totalCount", Integer.parseInt(totalCount));
//			map.put("failCount", Integer.parseInt(failCount));
//			map.put("normalCount", normalCount);
//			map.put("inspectCount", Integer.parseInt(inspectCount));
			
//			result.add(map);
//		}
//		
//		return result;
		return null;
	}
	
}
