package kr.nexg.esm.administrator.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.administrator.dto.AdministratorVo;
import kr.nexg.esm.administrator.mapper.AdministratorMapper;
import kr.nexg.esm.common.util.EnumUtil;
import kr.nexg.esm.nexgesm.mariadb.User;
import kr.nexg.esm.util.Validation;
import kr.nexg.esm.util.config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdministratorService {
	
	@Autowired
	AdministratorMapper administratorMapper;
	
	@Autowired
	User.User1 user;
	
	@Autowired
	User.UserGroup userGroup;
	
	public List<Map<String, Object>> getUserInfo(Map<String, String> paramMap) throws Exception {
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rs_adminID = (String)config.setValue(rsDatas, "adminID", "1");
		
		AdministratorVo administratorVo = new AdministratorVo();
		administratorVo.setAdminID(rs_adminID);
		
		List<Map<String, Object>> list = administratorMapper.getUserInfo(administratorVo);
		
		String recent_fail_device = EnumUtil.blank(String.valueOf(list.get(0).get("recent_fail_device")));
		String resource_top5 = EnumUtil.blank(String.valueOf(list.get(0).get("resource_top5")));
		String week_log_stats = EnumUtil.blank(String.valueOf(list.get(0).get("week_log_stats")));
		String week_fail_state = EnumUtil.blank(String.valueOf(list.get(0).get("week_fail_state")));
		
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
		map.put("recent_fail_device", AdministratorEnum.activeState.valueOf("_"+recent_fail_device).getVal());
		map.put("resource_top5", AdministratorEnum.activeState.valueOf("_"+resource_top5).getVal());
		map.put("week_log_stats", AdministratorEnum.activeState.valueOf("_"+week_log_stats).getVal());
		map.put("week_fail_state", AdministratorEnum.activeState.valueOf("_"+week_fail_state).getVal());
		map.put("device_sort", String.valueOf(list.get(0).get("device_sort")));
		map.put("device_order", String.valueOf(list.get(0).get("device_order")));
		map.put("popupTime", String.valueOf(list.get(0).get("popup_time")));
		map.put("allow_ip1", String.valueOf(list.get(0).get("allow_ip1")));
		map.put("allow_ip2", String.valueOf(list.get(0).get("allow_ip2")));
		
		List<Map<String, Object>> result = new ArrayList<>();
		result.add(map);
		return result;
	}
	
	public List<Map<String, Object>> getUser(Map<String, Object> paramMap) throws Exception{
		
		
		String sessionId = (String) paramMap.get("sessionId");
		String limit = (String) paramMap.get("limit");
		String page = (String) paramMap.get("page");
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rs_search = (String)config.setValue(rsDatas, "search", "");
		
		AdministratorVo administratorVo = new AdministratorVo();
		administratorVo.setSessionId(sessionId);
		administratorVo.setSearch(rs_search);
		administratorVo.setLimit(Integer.parseInt(limit));
		administratorVo.setPage(Integer.parseInt(page));
		
		List<Map<String, Object>> list = administratorMapper.getUser(administratorVo);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = new LinkedHashMap<>();
			String ipInfo = "";
			if(list.get(i).get("ip1") != null) {
				ipInfo = (String) list.get(i).get("ip1");
			}
			if(list.get(i).get("ip2") != null) {
				if(!"".equals(ipInfo)) {
					ipInfo = ipInfo + "," + list.get(i).get("ip2");
				}else {
					ipInfo = (String) list.get(i).get("ip2");
				}
			}
			
			String setting = EnumUtil.blank(String.valueOf(list.get(i).get("role1")));
			String device = EnumUtil.blank(String.valueOf(list.get(i).get("role2")));
			String monitoring = EnumUtil.blank(String.valueOf(list.get(i).get("role3")));
			String statistic = EnumUtil.blank(String.valueOf(list.get(i).get("role4")));
			String report = EnumUtil.blank(String.valueOf(list.get(i).get("role5")));
			String analysis = EnumUtil.blank(String.valueOf(list.get(i).get("role6")));
			String log = EnumUtil.blank(String.valueOf(list.get(i).get("role7")));
			String policy = EnumUtil.blank(String.valueOf(list.get(i).get("role2")));
			String topology = EnumUtil.blank(String.valueOf(list.get(i).get("role2")));
			String productManager = EnumUtil.blank(String.valueOf(list.get(i).get("role9")));
			String deviceSetting = EnumUtil.blank(String.valueOf(list.get(i).get("role10")));
			
			map.put("adminID", String.valueOf(list.get(i).get("id")));
			map.put("login", String.valueOf(list.get(i).get("login")));
			map.put("adminName", list.get(i).get("name"));
			map.put("adminGroupName", list.get(i).get("user_group_name"));
			map.put("ip", String.valueOf(ipInfo));
			map.put("setting", AdministratorEnum.activeState.valueOf("_"+setting).getVal());
			map.put("device", AdministratorEnum.activeState.valueOf("_"+device).getVal());
			map.put("defMode", list.get(i).get("def_mode"));
			map.put("sessionTimeout", list.get(i).get("session_time"));
			map.put("alarm", list.get(i).get("alarm"));
			map.put("popupTime", list.get(i).get("popup_time"));
			map.put("monitoring", AdministratorEnum.activeState.valueOf("_"+monitoring).getVal());
			map.put("statistic", AdministratorEnum.activeState.valueOf("_"+statistic).getVal());
			map.put("report", AdministratorEnum.activeState.valueOf("_"+report).getVal());
			map.put("analysis", AdministratorEnum.activeState.valueOf("_"+analysis).getVal());
			map.put("log", AdministratorEnum.activeState.valueOf("_"+log).getVal());
			map.put("policy", AdministratorEnum.activeState.valueOf("_"+policy).getVal());
			map.put("topology", AdministratorEnum.activeState.valueOf("_"+topology).getVal());
			map.put("productManager", AdministratorEnum.activeState.valueOf("_"+productManager).getVal());
			map.put("deviceSetting", AdministratorEnum.activeState.valueOf("_"+deviceSetting).getVal());
			map.put("deviceGroupNames", list.get(i).get("dg_name"));
			map.put("deviceGroupIDs", list.get(i).get("dg_id"));
			map.put("totalCount", list.get(i).get("totalCount"));
			
			result.add(map);
		}
		
		return result;
	}
	
	public List<String> delUser(Map<String, Object> paramMap) throws Exception{
		
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		List<Integer> rs_adminIDs = (List<Integer>)config.setValue(rsDatas, "adminIDs", new ArrayList<>());
		
		List<String> rs_adminID_list = rs_adminIDs.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
		
		List<String> admin_names = new ArrayList<>();
		if(rs_adminIDs.size() > 0) {
			for(String id : rs_adminID_list) {
				List<Map<String, Object>> list = user.get_user_info(id);
				admin_names.add((String) list.get(0).get("login"));
			}
			
			String adminIds = String.join(",", rs_adminID_list);
			administratorMapper.delUser(adminIds);
		}
		
		return admin_names;
	}
	
	public List<Map<String, Object>> selectUserGroup(Map<String, Object> paramMap) throws Exception {
		
		String datas = (String) paramMap.get("datas");
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		List<Integer> rs_adminGroupIDs = (List<Integer>)config.setValue(rsDatas, "adminGroupIDs", new ArrayList<>());
		
		List<String> rs_adminGroupID_list = rs_adminGroupIDs.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
		
		AdministratorVo administratorVo = new AdministratorVo();
		administratorVo.setAdminGroupIDs(rs_adminGroupID_list);
		
		return administratorMapper.selectUserGroup(administratorVo);
	}
	
	public List<String> delUserGroup(Map<String, Object> paramMap) throws Exception {
		
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		List<Integer> rs_adminGroupIDs = (List<Integer>)config.setValue(rsDatas, "adminGroupIDs", new ArrayList<>());
		
		List<String> rs_adminGroupID_list = rs_adminGroupIDs.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
		
		List<String> user_group_names = new ArrayList<>();
		if(rs_adminGroupID_list.size() > 0) {
			for(String id : rs_adminGroupID_list) {
				List<Map<String, Object>> list = userGroup.get_user_group_info(id);
				user_group_names.add((String) list.get(0).get("name"));
			}
			
			String adminGroupIds = String.join(",", rs_adminGroupID_list);
			administratorMapper.delUserGroup(adminGroupIds);
		}
		
		return user_group_names;
	}
	
	public int setUserInfo(Map<String, Object> paramMap) throws Exception {
		
		String datas = (String) paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rs_adminID = (String) config.setValue(rsDatas, "adminID", "null");
		String rs_adminName = (String) config.setValue(rsDatas, "adminName", "");
		String rs_desc = (String) config.setValue(rsDatas, "desc", "");
		String rs_adminGroupID = (String) config.setValue(rsDatas, "adminGroupID", "");
		List<Integer> rs_deviceGroupIDs = (List<Integer>) config.setValue(rsDatas, "deviceGroupIDs", new ArrayList<>());
		String rs_defMode = (String) config.setValue(rsDatas, "defMode", "");
		String rs_sessionTime = (String) config.setValue(rsDatas, "sessionTimeout", "");
		String rs_alarm = (String) config.setValue(rsDatas, "alarm", "");
		String rs_popupTime = (String) config.setValue(rsDatas, "popupTime", "7");
		String rs_login = (String) config.setValue(rsDatas, "login", "");
		String rs_pwd = (String) config.setValue(rsDatas, "pwd", "");
		String rs_newPwd = (String) config.setValue(rsDatas, "newPwd", "");
		String rs_active = (String) config.setValue(rsDatas, "active", "");
		String rs_allow_ip1 = (String) config.setValue(rsDatas, "allow_ip1", "");
		String rs_allow_ip2 = (String) config.setValue(rsDatas, "allow_ip2", "");
		String rs_device_state = (String) config.setValue(rsDatas, "device_state", "");
		String rs_recent_fail_device = (String) config.setValue(rsDatas, "recent_fail_device", "");
		String rs_resource_top5 = (String) config.setValue(rsDatas, "resource_top5", "");
		String rs_week_log_stats = (String) config.setValue(rsDatas, "week_log_stats", "");
		String rs_week_fail_state = (String) config.setValue(rsDatas, "week_fail_state", "");
		String rs_device_sort = (String) config.setValue(rsDatas, "device_sort", "0");
		String rs_device_order = (String) config.setValue(rsDatas, "device_order", "0");
		String rs_email = (String) config.setValue(rsDatas, "adminEmail", "");
		String rs_expire_date = (String) config.setValue(rsDatas, "adminExpireDate", "");
		String rs_lifetime = (String) config.setValue(rsDatas, "adminLifetime", "365");
		String rs_password_expire_cycle = (String) config.setValue(rsDatas, "passwordExpireCycle", "90");
		String rs_device_id = (String) config.setValue(rsDatas, "monitorDeviceID", "0");
		String rs_pwdInit = (String) config.setValue(rsDatas, "pwdInit", "");
		
		AdministratorVo administratorVo = new AdministratorVo();
		
		Validation.userAdd_adminName(rs_adminName);
		
		List<Map<String, Object>> adminGroupList = administratorMapper.selectAdminGroup(rs_adminGroupID);
		
		if(rs_adminGroupID == "" && rs_adminID != "null") {
			List<Map<String, Object>> rs_adminGroupID_list = administratorMapper.selectAdminGroup(rs_adminID);
			rs_adminGroupID = Integer.toString((Integer) rs_adminGroupID_list.get(0).get("group_id"));
			rs_adminGroupID = !rs_adminGroupID.isBlank() ? rs_adminGroupID : "";
		}else {
			Validation.userAdd_adminGroupID(adminGroupList);
		}
		
		List<Map<String, Object>> deviceGroupList = new ArrayList<Map<String,Object>>();
		if(rs_deviceGroupIDs.size() > 0) {
			List<String> rs_deviceGroupID_list = rs_deviceGroupIDs.stream()
	                .map(Object::toString)
	                .collect(Collectors.toList());
			administratorVo.setDeviceGroupIDs(rs_deviceGroupID_list);
			deviceGroupList = administratorMapper.selectDeviceGroup(administratorVo);
		}
		Validation.userAdd_deviceGroupIDs(deviceGroupList);
		
		Validation.userAdd_defMode(rs_defMode);
		
		Validation.userAdd_sessionTime(rs_sessionTime);
		
		Validation.userAdd_alarm(rs_alarm);
		
		Validation.userAdd_user_id(rs_login);
		
		return 0;
		
	}
	
	
}
