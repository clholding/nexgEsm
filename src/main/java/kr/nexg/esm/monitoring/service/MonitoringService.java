package kr.nexg.esm.monitoring.service;

import java.util.ArrayList;
import java.util.HashMap;
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
		String deviceIds = "";
		
		if(rs_groupID.isBlank()) {
			deviceIds = "";
		}else {
			int mode = mode_convert.convert_modedata(rs_mode);
			deviceIds = monitoringMapper.getGroupToDeviceListByLogin(rs_groupID, sessionId, Integer.toString(mode));
//			deviceIds = String.join(",", rs_deviceIDs);
		}
		
		int cnt = 0;
		List<Map<String, Object>> deviceMonitorList = new ArrayList<>();
		if(!"".equals(deviceIds)) {
			deviceMonitorList = deviceMonitor.get_monitor(deviceIds);
		}
		
		int did = 0;
		String ips_str = "";
		for(int i=0; i<deviceMonitorList.size(); i++) {
			Map<String, String> interfaces = new HashMap<>();
			List<String> ips = new ArrayList<>();
			cnt = 1;
			String device_id = String.valueOf(deviceMonitorList.get(i).get("device_id"));
			List<Map<String, Object>> intStatusList = monitoringMapper.getIntStatus(device_id);
			for(int j=0; j<intStatusList.size(); j++) {
				if(cnt > 8) {	// 인터페이스는 최대 8개까지만
					break;
				}
				cnt += 1;
				String name = (String) intStatusList.get(j).get("name");
				String status = String.valueOf(intStatusList.get(j).get("status"));
				String ip = (String) intStatusList.get(j).get("ip");
				if(status == null || status.isBlank()) {
					interfaces.put(name, "0");
				}else {
					interfaces.put(name, status);
					if(ip != null && !ip.isBlank()) {
						ips.add(ip);
					}
				}
			}
			ips_str = String.join(" ", ips);
			
			
			//track
			List<Map<String, String>> tracks = new ArrayList<>();
			cnt = 1;
			List<Map<String, Object>> trackStatusList = monitoringMapper.getTrackStatus(device_id);
			for(int j=0; j<trackStatusList.size(); j++) {
				if(cnt > 8) {	//TRACK 최대 8개까지만
					break;
				}
				cnt += 1;
				Map<String, String> track = new HashMap<>();
				track.put("name", (String) trackStatusList.get(j).get("num"));
				track.put(ips_str, String.valueOf(trackStatusList.get(j).get("status")));
				tracks.add(track);
			}
			
			//eix
			List<Map<String, String>> eixs = new ArrayList<>();
			cnt = 1;
			List<Map<String, Object>> eixStatusList = monitoringMapper.eixStatus(device_id);
			
			for(int j=0; j<eixStatusList.size(); j++) {
				if(cnt > 8) {	//EIX 최대 8개까지만
					break;
				}
				cnt += 1;
				String identity = (String) eixStatusList.get(j).get("identity");
				String peerip = (String) eixStatusList.get(j).get("peerip");
				String status = (String) eixStatusList.get(j).get("status");
				String uptime = (String) eixStatusList.get(j).get("uptime");
				String ifname = (String) eixStatusList.get(j).get("ifname");
				int updown = 0;
				
				if(status.indexOf("up") > -1) {
					updown = 1;
				}else if(status.indexOf("down") > -1) {
					updown = 0;
				}else {
					updown = Integer.parseInt(status);
				}
				Map<String, String> eix = new HashMap<>();
				eix.put("identity", identity);
				eix.put("peerip", peerip);
				eix.put("status", status);
				eix.put("uptime", status);
				eix.put("ifname", ifname);
				eix.put("updown", Integer.toString(updown));
				eixs.add(eix);
			}
			
			//vrrp
			List<Map<String, String>> vrrps = new ArrayList<>();
			cnt = 1;
			List<Map<String, Object>> vrrpStatusList = monitoringMapper.vrrpStatus(device_id);
			
			for(int j=0; j<vrrpStatusList.size(); j++) {
				if(cnt > 8) {	//vrrp 최대 8개까지만
					break;
				}
				cnt += 1;
				String name = (String) vrrpStatusList.get(j).get("name");
				String status = (String) vrrpStatusList.get(j).get("status");
				String ip = (String) vrrpStatusList.get(j).get("ip");
				int master = 1;
				
				if(status.indexOf("master") > -1) {
					master = 1;
				}else {
					master = 0;
				}
				Map<String, String> vrrp = new HashMap<>();
				vrrp.put("name", name);
				vrrp.put("status", status);
				vrrp.put("ip", ip);
				vrrp.put("master", Integer.toString(master));
				vrrps.add(vrrp);
			}
			
			String redudant_power1 = "";
			String redudant_power2 = "";
			
			int power = Integer.parseInt(String.valueOf(deviceMonitorList.get(i).get("power")));
			if(power == 0) {
				redudant_power1 = "1";
				redudant_power2 = "1";
			}else if(power == 1) {
				redudant_power1 = "1";
				redudant_power2 = "0";
			}else if(power == 2) {
				redudant_power1 = "0";
				redudant_power2 = "1";
			}
			
			String _cert_expire_time = "";
			String _cert_state = "";
			String _cert_info = "";
			String cert_expire_time = (String) deviceMonitorList.get(i).get("cert_expire_time");
			String cert_alarm = String.valueOf(deviceMonitorList.get(i).get("cert_expire_time"));
			String cert = (String) deviceMonitorList.get(i).get("cert_expire_time");
			if(cert_expire_time == null || cert_expire_time.isBlank()) {
				_cert_expire_time = "";
		        _cert_state = "";
		        _cert_info = "";
			}else {
				_cert_expire_time = cert_expire_time;
		        _cert_state = cert_alarm;
		        _cert_info = cert;
			}
			
			//console, npc power
			List<Map<String, String>> consoles = new ArrayList<>();
			Map<String, String> console = new HashMap<>();
			
			String console1 = String.valueOf(deviceMonitorList.get(i).get("console1"));
			String console2 = String.valueOf(deviceMonitorList.get(i).get("console2"));
			String console3 = String.valueOf(deviceMonitorList.get(i).get("console3"));
			String console4 = String.valueOf(deviceMonitorList.get(i).get("console4"));
			
			if(console1 != null && !"null".equals(console1) && !console1.isBlank()) {
				console.put("name", "console #1");
				console.put("status", console1);
				consoles.add(console);
			}
			if(console2 != null && !"null".equals(console2) && !console2.isBlank()) {
				console.put("name", "console #2");
				console.put("status", console2);
				consoles.add(console);
			}
			if(console3 != null && !"null".equals(console3) && !console3.isBlank()) {
				console.put("name", "console #3");
				console.put("status", console3);
				consoles.add(console);
			}
			if(console4 != null && !"null".equals(console4) && !console4.isBlank()) {
				console.put("name", "console #4");
				console.put("status", console4);
				consoles.add(console);
			}
			
			List<Map<String, String>> powers = new ArrayList<>();
			Map<String, String> powerMap = new HashMap<>();
			
			String power1 = String.valueOf(deviceMonitorList.get(i).get("power1"));
			String power2 = String.valueOf(deviceMonitorList.get(i).get("power2"));
			String power3 = String.valueOf(deviceMonitorList.get(i).get("power3"));
			String power4 = String.valueOf(deviceMonitorList.get(i).get("power4"));
			
			if(power1 != null && !"null".equals(power1) && !power1.isBlank()) {
				powerMap.put("name", "power #1");
				powerMap.put("status", power1);
				powers.add(powerMap);
			}
			if(power2 != null && !"null".equals(power2) && !power2.isBlank()) {
				powerMap.put("name", "power #2");
				powerMap.put("status", power2);
				powers.add(powerMap);
			}
			if(power3 != null && !"null".equals(power3) && !power3.isBlank()) {
				powerMap.put("name", "power #3");
				powerMap.put("status", power3);
				powers.add(powerMap);
			}
			if(power4 != null && !"null".equals(power4) && !power4.isBlank()) {
				powerMap.put("name", "power #4");
				powerMap.put("status", power4);
				powers.add(powerMap);
			}
			
			
			List<Map<String, Object>> failInterfaceList = monitoringMapper.getFailInterface(device_id);
			String longtime_down = String.valueOf(failInterfaceList.get(0).get("longtime_down"));
			String updown_repeat = String.valueOf(failInterfaceList.get(0).get("updown_repeat"));
			
			
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
