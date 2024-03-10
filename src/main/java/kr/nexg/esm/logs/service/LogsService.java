package kr.nexg.esm.logs.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	public List<Map<String, Object>> lastFailDevice(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rs_page = (String)config.setValue(rsDatas, "page", "1");
		String rs_viewCount = (String)config.setValue(rsDatas, "viewCount", "10");
		List<String> rs_deviceIDs = (List<String>)config.setValue(rsDatas, "deviceIDs", new ArrayList<>());
		String deviceIds = String.join(",", rs_deviceIDs);
		
		String rs_mode = (String) paramMap.get("mode");
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
