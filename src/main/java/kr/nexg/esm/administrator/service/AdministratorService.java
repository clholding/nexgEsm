package kr.nexg.esm.administrator.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.administrator.dto.AdministratorVo;
import kr.nexg.esm.administrator.mapper.AdministratorMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdministratorService {
	
	@Autowired
	AdministratorMapper administratorMapper;
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo) {
		List<Map<String, Object>> list = administratorMapper.getUserInfo(vo);
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("adminID", String.valueOf(list.get(0).get("id")));
		map.put("adminName", list.get(0).get("name"));
		map.put("login", list.get(0).get("login"));
		map.put("desc", list.get(0).get("desc"));
		map.put("adminGroupID", String.valueOf(list.get(0).get("group_id")));
		map.put("deviceGroupIDs", list.get(0).get("dg_idd"));
		map.put("deviceGroupNames", list.get(0).get("dg_name"));
		map.put("defMode", list.get(0).get("def_mode"));
		map.put("sessionTimeout", list.get(0).get("session_time"));
		map.put("alarm", String.valueOf(list.get(0).get("alarm")));
		map.put("active", String.valueOf(list.get(0).get("active")));
		map.put("adminEmail", String.valueOf(list.get(0).get("email")));
		map.put("adminExpireDate", String.valueOf(list.get(0).get("login_expire_date")));
		map.put("adminLifetime", String.valueOf(list.get(0).get("login_active_lifetime")));
		map.put("passwordExpireCycle", String.valueOf(list.get(0).get("pwd_expire_cycle")));
		map.put("monitorDeviceID", String.valueOf(list.get(0).get("device_id")));
		map.put("device_state", String.valueOf(list.get(0).get("device_state")));
		map.put("recent_fail_device", AdministratorEnum.activeState.valueOf("_"+list.get(0).get("recent_fail_device")).getVal());
		map.put("resource_top5", AdministratorEnum.activeState.valueOf("_"+list.get(0).get("resource_top5")).getVal());
		map.put("week_log_stats", AdministratorEnum.activeState.valueOf("_"+list.get(0).get("week_log_stats")).getVal());
		map.put("week_fail_state", AdministratorEnum.activeState.valueOf("_"+list.get(0).get("week_fail_state")).getVal());
		map.put("device_sort", String.valueOf(list.get(0).get("device_sort")));
		map.put("device_order", String.valueOf(list.get(0).get("device_order")));
		map.put("popupTime", String.valueOf(list.get(0).get("popup_time")));
		map.put("allow_ip1", String.valueOf(list.get(0).get("allow_ip1")));
		map.put("allow_ip2", String.valueOf(list.get(0).get("allow_ip2")));
		
		List<Map<String, Object>> result = new ArrayList<>();
		result.add(map);
		return result;
	}
	
	public List<Map<String, Object>> getUser(AdministratorVo vo) {
		return administratorMapper.getUser(vo);
	}
	
	
}
