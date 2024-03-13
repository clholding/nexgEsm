package kr.nexg.esm.logs.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.logs.dto.LogsVo;
import kr.nexg.esm.logs.mapper.LogsMapper;
import kr.nexg.esm.nexgesm.mariadb.Log;
import kr.nexg.esm.util.config;
import kr.nexg.esm.util.mode_convert;

@Service
public class LogsService {
	
	@Autowired
	LogsMapper logsMapper;

	@Autowired
	Log.FailLog failLog;
	
	public List<Map<String, Object>> lastFailDevice(LogsVo logsVo) throws Exception{
		
		String sessionId = logsVo.getSessionId();
		int page = logsVo.getPage();
		int limit = logsVo.getLimit();
		
		String rs_page = Integer.toString(page);
		String rs_viewCount = Integer.toString(limit);
		List<String> rs_deviceIDs = logsVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		
		String rs_mode = logsVo.getMode();
		int mode = mode_convert.convert_modedata(rs_mode);
		
		int skip = (Integer.parseInt(rs_page) - 1) * (Integer.parseInt(rs_viewCount) - 1);
		
		List<Map<String, Object>> list = failLog.get_lastfaildevice(sessionId, mode, Integer.toString(skip), rs_viewCount);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("time", String.valueOf(list.get(i).get("date")));
			map.put("gn", String.valueOf(list.get(i).get("device_group_name")));
			map.put("dn", String.valueOf(list.get(i).get("device_name")));
			map.put("fip", String.valueOf(list.get(i).get("ip")));
			map.put("type", String.valueOf(list.get(i).get("fail_name")));
			map.put("info", String.valueOf(list.get(i).get("info")));
			map.put("deviceID", String.valueOf(list.get(i).get("id")));
			
			result.add(map);
		}
		
		return result;
	}
	
}
