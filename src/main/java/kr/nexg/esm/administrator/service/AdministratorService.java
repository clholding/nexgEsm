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
import kr.nexg.esm.common.util.EnumUtil;
import kr.nexg.esm.nexgesm.mariadb.Log;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdministratorService {
	
	@Autowired
	AdministratorMapper administratorMapper;
	
	@Autowired
	Log.EsmAuditLog esmAuditLog;
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo) {
		List<Map<String, Object>> list = administratorMapper.getUserInfo(vo);
		
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
	
	public List<Map<String, Object>> getUser(AdministratorVo vo) {
		String sessionId = "admin";
		vo.setSessionId(sessionId);
		
		List<Map<String, Object>> list = administratorMapper.getUser(vo);
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
	
	public void delUser(AdministratorVo vo) {
		esmAuditLog.esmlog(6, "admin", "127.0.0.1", "Logout");
		
	}
	
	
}
