package kr.nexg.esm.logs.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.common.util.CustomMessageException;
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
	
	@Autowired
	Log.LogInput logInput;
	
	public String getLocalizedDayOfWeek(String date_string) {
		LocalDate date = LocalDate.parse(date_string);
		
		DayOfWeek dayOfWeek = date.getDayOfWeek();
		String dayOfWeek_kr = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.KOREAN);
		return "("+dayOfWeek_kr+")";
	}
	
	public List<Map<String, Object>> lastFailDevice(LogsVo logsVo) throws Exception{
		
		String sessionId = logsVo.getSessionId();
		int page = logsVo.getPage();
		int viewCount = logsVo.getViewCount();
		
		String rs_page = Integer.toString(page);
		String rs_viewCount = Integer.toString(viewCount);
		List<String> rs_deviceIDs = logsVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		
		String rs_mode = logsVo.getMode();
		int mode = mode_convert.convert_modedata(rs_mode);
		
		int skip = (page - 1) * (viewCount - 1);
		
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
	
	public List<Map<String, Object>> weeklyLog(LogsVo logsVo) throws Exception{
		
		List<String> rs_deviceIDs = logsVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		
		List<Map<String, Object>> list = logInput.get_data(deviceIds);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			String strDate = String.valueOf(list.get(i).get("dt"));
			String count = String.valueOf(list.get(i).get("count"));
			map.put("date", strDate + getLocalizedDayOfWeek(strDate));
			map.put("num", String.valueOf(list.get(i).get("count")));
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> etcLogs(LogsVo logsVo) throws Exception{
		
		int page = logsVo.getPage();
		int viewCount = logsVo.getViewCount();
		
		String rs_target = logsVo.getTarget();
		String rs_page = Integer.toString(page);
		String rs_viewCount = Integer.toString(viewCount);
		String rs_startDate = logsVo.getStartDate();
		String rs_endDate = logsVo.getEndDate();
		Map<String, String> rs_dictionaries = logsVo.getDictionaries();
		String rs_dn = logsVo.getDn();
		String rs_gn = logsVo.getGn();
		String rs_fip = logsVo.getFip();
		List<String> rs_deviceIDs = logsVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		
		String rs_mode = logsVo.getMode();
		int mode = mode_convert.convert_modedata(rs_mode);
		
		if(!rs_gn.isBlank()) {
		 	List<Map<String, Object>> rows = logsMapper.getGroupDeviceList(rs_gn, Integer.toString(mode));
		 	
		 	if(rows.size() > 0) {
		 		List<String> gn_ids = new ArrayList<String>();
		 		for(int i=0; i<rows.size(); i++) {
		 			gn_ids.add(String.valueOf(rows.get(i).get("id")));
		 		}
		 		rs_deviceIDs = gn_ids;
		 	}else {
		 		throw new CustomMessageException("not find deviceID in Group");
		 	}
		}
		
		if(!rs_dn.isBlank()) {
			List<String> rs_ids = new ArrayList<String>();
			rs_ids.add(rs_dn);
			rs_deviceIDs = rs_ids;
		}
		
		int skip = (page - 1) * (viewCount - 1);
		
		
//		List<Map<String, Object>> list = logInput.get_data(deviceIds);
		List<Map<String, Object>> result = new ArrayList<>();
		
//		for(int i=0; i<list.size(); i++) {
//			
//			Map<String, Object> map = new LinkedHashMap<>();
//			String strDate = String.valueOf(list.get(i).get("dt"));
//			String count = String.valueOf(list.get(i).get("count"));
//			map.put("date", strDate + getLocalizedDayOfWeek(strDate));
//			map.put("num", String.valueOf(list.get(i).get("count")));
//			
//			result.add(map);
//		}
		
		return result;
	}
	
}
