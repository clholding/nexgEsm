package kr.nexg.esm.global.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.common.util.EnumUtil;
import kr.nexg.esm.global.mapper.GlobalMapper;
import kr.nexg.esm.util.config;
import kr.nexg.esm.util.mode_convert;

@Service
public class GlobalService {
	
	@Autowired
	GlobalMapper globalMapper;
	
	public List<Map<String, Object>> devices(Map<String, Object> paramMap) throws Exception{
		
		String parentId = (String) paramMap.get("node");
		parentId = !parentId.isBlank() ? parentId : "0";
		
		List<Map<String, Object>> list = globalMapper.devices(parentId);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("id", String.valueOf(list.get(i).get("id")));
			map.put("text", list.get(i).get("name"));
			map.put("leaf", list.get(i).get("leaf"));
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getUserInfoByLogin(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		
		List<Map<String, Object>> list = globalMapper.getUserInfoByLogin(sessionId);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("an", String.valueOf(list.get(i).get("name")));
			map.put("gn", String.valueOf(list.get(i).get("group_name")));
			map.put("login", sessionId);
			
			Map<String, Object> authInfo = new LinkedHashMap<>();
			String setting = EnumUtil.blank(String.valueOf(list.get(i).get("role1")));
			String device = EnumUtil.blank(String.valueOf(list.get(i).get("role2")));
			String monitoring = EnumUtil.blank(String.valueOf(list.get(i).get("role3")));
			String statistic = EnumUtil.blank(String.valueOf(list.get(i).get("role4")));
			String report = EnumUtil.blank(String.valueOf(list.get(i).get("role5")));
			String analysis = EnumUtil.blank(String.valueOf(list.get(i).get("role6")));
			String log = EnumUtil.blank(String.valueOf(list.get(i).get("role7")));
			String policy = EnumUtil.blank(String.valueOf(list.get(i).get("role8")));
			String topology = EnumUtil.blank(String.valueOf(list.get(i).get("role2")));
			String productManager = EnumUtil.blank(String.valueOf(list.get(i).get("role9")));
			String deviceSetting = EnumUtil.blank(String.valueOf(list.get(i).get("role10")));
			
			authInfo.put("setting", AdministratorEnum.activeState.valueOf("_"+setting).getVal());
			authInfo.put("device", AdministratorEnum.activeState.valueOf("_"+device).getVal());
			authInfo.put("monitoring", AdministratorEnum.activeState.valueOf("_"+monitoring).getVal());
			authInfo.put("statistic", AdministratorEnum.activeState.valueOf("_"+statistic).getVal());
			authInfo.put("report", AdministratorEnum.activeState.valueOf("_"+report).getVal());
			authInfo.put("analysis", AdministratorEnum.activeState.valueOf("_"+analysis).getVal());
			authInfo.put("log", AdministratorEnum.activeState.valueOf("_"+log).getVal());
			authInfo.put("policy", AdministratorEnum.activeState.valueOf("_"+policy).getVal());
			authInfo.put("topology", AdministratorEnum.activeState.valueOf("_"+topology).getVal());
			authInfo.put("productManager", AdministratorEnum.activeState.valueOf("_"+productManager).getVal());
			authInfo.put("deviceSetting", AdministratorEnum.activeState.valueOf("_"+deviceSetting).getVal());
			map.put("authInfo", authInfo);
			
			Map<String, Object> dashBoard = new LinkedHashMap<>();
			String device_state = EnumUtil.blank(String.valueOf(list.get(i).get("device_state")));
			String recent_fail_device = EnumUtil.blank(String.valueOf(list.get(i).get("recent_fail_device")));
			String resource_top5 = EnumUtil.blank(String.valueOf(list.get(i).get("resource_top5")));
			String week_log_stats = EnumUtil.blank(String.valueOf(list.get(i).get("week_log_stats")));
			String week_fail_state = EnumUtil.blank(String.valueOf(list.get(i).get("week_fail_state")));
			
			dashBoard.put("device_state", AdministratorEnum.activeState.valueOf("_"+device_state).getVal());
			dashBoard.put("recent_fail_device", AdministratorEnum.activeState.valueOf("_"+recent_fail_device).getVal());
			dashBoard.put("resource_top5", AdministratorEnum.activeState.valueOf("_"+resource_top5).getVal());
			dashBoard.put("week_log_stats", AdministratorEnum.activeState.valueOf("_"+week_log_stats).getVal());
			dashBoard.put("week_fail_state", AdministratorEnum.activeState.valueOf("_"+week_fail_state).getVal());
			map.put("dashBoard", dashBoard);
			
			Map<String, Object> leftMenu = new LinkedHashMap<>();
			
			leftMenu.put("device_sort", String.valueOf(list.get(i).get("device_sort")));
			leftMenu.put("device_order", String.valueOf(list.get(i).get("device_order")));
			map.put("leftMenu", leftMenu);
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getDeviceStatusByLogin(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		String rs_mode = (String) paramMap.get("mode");
		int mode = mode_convert.convert_modedata(rs_mode);
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
		
		List<Map<String, Object>> list = globalMapper.getDeviceStatusByLogin(sessionId, Integer.toString(mode));
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("totalCount", String.valueOf(list.get(i).get("dcount")));
			map.put("failCount", String.valueOf(list.get(i).get("fcount")));
			map.put("currentTime", currentTime);
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getDeviceFaultStatus(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		String rs_mode = (String) paramMap.get("mode");
		int mode = mode_convert.convert_modedata(rs_mode);
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		List<String> rs_deviceIDs = (List<String>) config.setValue(rsDatas, "deviceIDs", new ArrayList<>());
		String deviceIds = String.join(",", rs_deviceIDs);
		
		List<Map<String, Object>> list = globalMapper.getDeviceFaultStatus(sessionId, deviceIds ,Integer.toString(mode));
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			String failCount = String.valueOf(list.get(i).get("fail_count"));
			String totalCount = String.valueOf(list.get(i).get("total_count"));
			String inspectCount = String.valueOf(list.get(i).get("inspect_count"));
			int normalCount = Integer.parseInt(totalCount) - Integer.parseInt(failCount) - Integer.parseInt(inspectCount);
			map.put("totalCount", Integer.parseInt(totalCount));
			map.put("failCount", Integer.parseInt(failCount));
			map.put("normalCount", normalCount);
			map.put("inspectCount", Integer.parseInt(inspectCount));
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getAllDeviceFaultStatus(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		String datas = (String) paramMap.get("datas");
		
		//rs_deviceIDs = "0" 을 넘기면 현재 로그인한 계정에 대한 전체 장애장비 조회가 가능하다.
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		List<String> rs_deviceIDs = (List<String>) config.setValue(rsDatas, "deviceIDs", new ArrayList<>());
		String deviceIds = String.join(",", rs_deviceIDs);
		
		List<Map<String, Object>> list = globalMapper.getAllDeviceFaultStatus(sessionId, deviceIds);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			
			Map<String, Object> map0 = new LinkedHashMap<>();
			Map<String, Object> map1 = new LinkedHashMap<>();
			Map<String, Object> map2 = new LinkedHashMap<>();
			Map<String, Object> map4 = new LinkedHashMap<>();
			String failCount = String.valueOf(list.get(i).get("fail_count"));
			String inspectCount = String.valueOf(list.get(i).get("inspect_count"));
			String totalCount = String.valueOf(list.get(i).get("total_count"));
			String fwfailCount = String.valueOf(list.get(i).get("fwfail_count"));
			String fwinspectCount = String.valueOf(list.get(i).get("fwinspect_count"));
			String fwtotalCount = String.valueOf(list.get(i).get("fwtotal_count"));
			String utmfailCount = String.valueOf(list.get(i).get("utmfail_count"));
			String utminspectCount = String.valueOf(list.get(i).get("utminspect_count"));
			String utmtotalCount = String.valueOf(list.get(i).get("utmtotal_count"));
			String swfailCount = String.valueOf(list.get(i).get("swfail_count"));
			String swinspectCount = String.valueOf(list.get(i).get("swinspect_count"));
			String swtotalCount = String.valueOf(list.get(i).get("swtotal_count"));
			String m2mgfailCount = String.valueOf(list.get(i).get("m2mgfail_count"));
			String m2mginspectCount = String.valueOf(list.get(i).get("m2mginspect_count"));
			String m2mgtotalCount = String.valueOf(list.get(i).get("m2mgtotal_count"));
			
			int normalCount0 = Integer.parseInt(totalCount) - Integer.parseInt(inspectCount) - Integer.parseInt(failCount);
			int normalCount1 = Integer.parseInt(fwtotalCount) - Integer.parseInt(fwinspectCount) - Integer.parseInt(fwfailCount);
			int normalCount2 = Integer.parseInt(utmtotalCount) - Integer.parseInt(utminspectCount) - Integer.parseInt(utmfailCount);
			int normalCount4 = Integer.parseInt(m2mgtotalCount) - Integer.parseInt(m2mginspectCount) - Integer.parseInt(m2mgfailCount);
			
			map0.put("total", Integer.parseInt(totalCount));
			map0.put("inspect", Integer.parseInt(inspectCount));
			map0.put("fail", Integer.parseInt(failCount));
			map0.put("normal", normalCount0);
			map0.put("type", 0);
			
			map1.put("total", Integer.parseInt(fwtotalCount));
			map1.put("inspect", Integer.parseInt(fwinspectCount));
			map1.put("fail", Integer.parseInt(fwfailCount));
			map1.put("normal", normalCount1);
			map1.put("type", 1);
			
			map2.put("total", Integer.parseInt(utmtotalCount));
			map2.put("inspect", Integer.parseInt(utminspectCount));
			map2.put("fail", Integer.parseInt(utmfailCount));
			map2.put("normal", normalCount2);
			map2.put("type", 2);
			
			map4.put("total", Integer.parseInt(m2mgtotalCount));
			map4.put("inspect", Integer.parseInt(m2mginspectCount));
			map4.put("fail", Integer.parseInt(m2mgfailCount));
			map4.put("normal", normalCount4);
			map4.put("type", 4);
			
			result.add(map0);
			result.add(map1);
			result.add(map2);
			result.add(map4);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getAlarmMsg(Map<String, Object> paramMap) throws Exception{
		
		String sessionId = (String) paramMap.get("sessionId");
		String rs_mode = (String) paramMap.get("mode");
		int mode = mode_convert.convert_modedata(rs_mode);
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentTime = format.format(new Date());
		
        List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
        String userStatus = globalMapper.selectUserStatus(sessionId);
        if(Integer.parseInt(userStatus) == 0) {
        	
        }else {
        	globalMapper.updateHbtime(sessionId);
        }
		
		return null;
	}
	
}
