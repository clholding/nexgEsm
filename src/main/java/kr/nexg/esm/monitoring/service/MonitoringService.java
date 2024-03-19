package kr.nexg.esm.monitoring.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.common.util.CommonUtil;
import kr.nexg.esm.monitoring.dto.MonitorEnum;
import kr.nexg.esm.monitoring.dto.MonitoringVo;
import kr.nexg.esm.monitoring.mapper.MonitoringMapper;
import kr.nexg.esm.nexgesm.common.EsmConfig;
import kr.nexg.esm.nexgesm.mariadb.Monitor;
import kr.nexg.esm.util.mode_convert;

@Service
public class MonitoringService {
	
	@Autowired
	MonitoringMapper monitoringMapper;
	
	@Autowired
	EsmConfig esmConfig;
	
	@Autowired
	Monitor.DeviceMonitor deviceMonitor;
	
	@Autowired
	Monitor.TopN topN;
	
	public int convertValue(String val) {
		if(val.isBlank()) {
			return 0;
		}else {
			return Integer.parseInt(val);
		}
	}
	
	public int convertValue(int val) {
		if(val < 0) {
			return 0;
		}else {
			return val;
		}
	}
	
	public long convertValue(long val) {
		if(val < 0) {
			return 0;
		}else {
			return val;
		}
	}
	
	public float convertValue(float val) {
		if(val < 0) {
			return 0;
		}else {
			return val;
		}
	}
	
	public Map<String, String> convertStatusInfo(String type, String totalVal, String val, String alarmVal) throws Exception {
		Map<String, String> result = new HashMap<>();
		
		float m_totalVal = 0;
		if(totalVal != null && !"".equals(totalVal)) {
			m_totalVal = Float.parseFloat(totalVal);
		}
		
		float m_val = 0;
		if(val != null && !"".equals(val)) {
			m_val = Float.parseFloat(val);
		}
		
		float m_alarmVal = 0;
		if(alarmVal != null && !"".equals(alarmVal)) {
			m_alarmVal = Float.parseFloat(alarmVal);
		}
		
		String m_status = "0";
		if("cpu".equals(type)) {
			if(m_val > m_alarmVal && m_alarmVal > 0) {
				m_status = "1";
			}
			m_val = (float) ((Math.round(m_val*100)) / 100.0);	//소수점 2자리
		}else if("mem".equals(type)) {
			float used = m_totalVal - m_val;
			if(used > 0) {
				m_val = (used / m_totalVal) * 100;
			}
			m_val = (float) ((Math.round(m_val*100)) / 100.0);	//소수점 2자리
			if(m_val > m_alarmVal && m_alarmVal > 0) {
				m_status = "1";
			}
			m_val = used;
		}else if(type.contains("disk0 disk1")) {
			float used = m_val;
			if(m_val > 0) {
				m_val = (used / m_totalVal) * 100;
			}
			m_val = (float) ((Math.round(m_val*100)) / 100.0);	//소수점 2자리
			if(m_val > m_alarmVal && m_alarmVal > 0) {
				m_status = "1";
			}
			m_val = (int) used;
		}else if("tunnel".equals(type)) {
			if(m_val < m_alarmVal && m_alarmVal > 0) {
				m_status = "1";
			}
			m_val = (int) m_val;
		}else {
			if(m_val > m_alarmVal && m_alarmVal > 0) {
				m_status = "1";
			}
			m_val = (int) m_val;
		}
		
		result.put("num", m_val > 0 ? Float.toString(m_val) : "0");
		result.put("state", m_status);
		
		return result;
	}
	
	public List<Map<String, Object>> esmDevice(MonitoringVo monitoringVo) throws Exception{
		kr.nexg.esm.nexgesm.util.System s = new kr.nexg.esm.nexgesm.util.System();
		Map<String, Double> resultMap = s.esm_status();
		
		double cpu = resultMap.get("cpu");
		double disk0_total = resultMap.get("disk0_total");
		double disk0_used = resultMap.get("disk0_used");
		double disk1_total = resultMap.get("disk1_total");
		double disk1_used = resultMap.get("disk1_used");
		double mem_total = resultMap.get("mem_total");
		double mem_avail = resultMap.get("mem_available");
		
		Map<String, Object> cpuMap = new LinkedHashMap<>();
		cpuMap.put("type", "CPU");
		cpuMap.put("total", "100");
		cpuMap.put("num", Double.toString(cpu));
		
		Map<String, Object> memoryMap = new LinkedHashMap<>();
		memoryMap.put("type", "Memory");
		memoryMap.put("total", Double.toString(mem_total));
		memoryMap.put("num", Double.toString(mem_avail));
		
		Map<String, Object> disk0Map = new LinkedHashMap<>();
		disk0Map.put("type", "Disk0");
		disk0Map.put("total", Double.toString(disk0_total));
		disk0Map.put("num", Double.toString(disk0_used));
		
		Map<String, Object> disk1Map = new LinkedHashMap<>();
		disk1Map.put("type", "Disk1");
		disk1Map.put("total", Double.toString(disk1_total));
		disk1Map.put("num", Double.toString(disk1_used));
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		result.add(cpuMap);
		result.add(memoryMap);
		result.add(disk0Map);
		result.add(disk1Map);
		
		return result;
	}
	
	public List<Map<String, Object>> list(MonitoringVo monitoringVo) throws Exception{
		
		String sessionId = monitoringVo.getSessionId();
		String rs_mode = monitoringVo.getMode();
		String rs_groupID = monitoringVo.getGroupID();
		
		List<Map<String, Object>> result = new ArrayList<>();
		
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
				track.put("status", String.valueOf(trackStatusList.get(j).get("status")));
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
			
			String console1 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("console1")));
			String console2 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("console2")));
			String console3 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("console3")));
			String console4 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("console4")));
			
			if(console1 != null && !"".equals(console1)) {
				console.put("name", "console #1");
				console.put("status", console1);
				consoles.add(console);
			}
			if(console2 != null && !"".equals(console2)) {
				console.put("name", "console #2");
				console.put("status", console2);
				consoles.add(console);
			}
			if(console3 != null && !"".equals(console3)) {
				console.put("name", "console #3");
				console.put("status", console3);
				consoles.add(console);
			}
			if(console4 != null && !"".equals(console4)) {
				console.put("name", "console #4");
				console.put("status", console4);
				consoles.add(console);
			}
			
			List<Map<String, String>> powers = new ArrayList<>();
			Map<String, String> powerMap = new HashMap<>();
			
			String power1 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("power1")));
			String power2 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("power2")));
			String power3 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("power3")));
			String power4 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("power4")));
			
			if(power1 != null && !"".equals(power1)) {
				powerMap.put("name", "power #1");
				powerMap.put("status", power1);
				powers.add(powerMap);
			}
			if(power2 != null && !"".equals(power2)) {
				powerMap.put("name", "power #2");
				powerMap.put("status", power2);
				powers.add(powerMap);
			}
			if(power3 != null && !"".equals(power3)) {
				powerMap.put("name", "power #3");
				powerMap.put("status", power3);
				powers.add(powerMap);
			}
			if(power4 != null && !"".equals(power4)) {
				powerMap.put("name", "power #4");
				powerMap.put("status", power4);
				powers.add(powerMap);
			}
			
			
			List<Map<String, Object>> failInterfaceList = monitoringMapper.getFailInterface(device_id);
			String longtime_down = CommonUtil.setEmptyString(String.valueOf(failInterfaceList.get(0).get("longtime_down")));
			String updown_repeat = CommonUtil.setEmptyString(String.valueOf(failInterfaceList.get(0).get("updown_repeat")));
			
			String gn = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("name")));
			String dn = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("name1")));
			String d_ip = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("ip")));
			String cpu = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cpu")));
			String cpu_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cpu_alarm")));
			String mem_total = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("mem_total")));
			String mem_avail = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("mem_avail")));
			String mem_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("mem_alarm")));
			String disk0_total = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk0_total")));
			String disk0_used = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk0_used")));
			String disk0_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk0_alarm")));
			String disk1_total = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk1_total")));
			String disk1_used = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk1_used")));
			String disk1_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("disk1_alarm")));
			String session = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("session")));
			String session_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("session_alarm")));
			String tunnel = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tunnel")));
			String tunnel_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tunnel_alarm")));
			String rtt = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rtt")));
			String rtt_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rtt_alarm")));
			String rx_bytes = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rx_bytes")));
			String rbytes_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rbytes_alarm")));
			String rx_pkts = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rx_pkts")));
			String rpkts_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rpkts_alarm")));
			String rx_errs = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rx_errs")));
			String rx_drops = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rx_drops")));
			String tx_bytes = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tx_bytes")));
			String tbytes_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tbytes_alarm")));
			String tx_pkts = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tx_pkts")));
			String tpkts_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tpkts_alarm")));
			String tx_errs = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tx_errs")));
			String tx_drops = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tx_drops")));
			String updowntime = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("updowntime")));
			String status0 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("status0")));
			String status1 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("status1")));
			String cpu_temp = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cpu_temp")));
			String cpu_temp_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cpu_temp_alarm")));
			String system_temp = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("system_temp")));
			String system_temp_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("system_temp_alarm")));
			String code1 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("code1")));
			String code2 = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("code2")));
			String hostname = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("hostname")));
			String product_id = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("product_id")));
			String agent = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("agent")));
			String os = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("os")));
			String active = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("active")));
			String host = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("host")));
			String host_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("host_alarm")));
			String rsrp = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("rsrp")));
			String tunnel_info = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("tunnel_info")));
			String lte_vender = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("lte_vender")));
			String lte_number = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("lte_number")));
			String duration = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("duration")));
			String cps = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cps")));
			String cps_alarm = CommonUtil.setEmptyString(String.valueOf(deviceMonitorList.get(i).get("cps_alarm")));
			
			Map<String, String> _cpu = convertStatusInfo("cpu", "0", cpu, cpu_alarm);
			Map<String, String> _mem = convertStatusInfo("mem", mem_total, mem_avail, mem_alarm);  // total-avail
			Map<String, String> _disk0 = convertStatusInfo("disk0", disk0_total, disk0_used, disk0_alarm); // used
			Map<String, String> _disk1 = convertStatusInfo("disk1", disk1_total, disk1_used, disk1_alarm);  // used
			Map<String, String> _session = convertStatusInfo("session", "0", session, session_alarm);
			Map<String, String> _host = convertStatusInfo("host", "0", host, host_alarm);
			Map<String, String> _tunnel = convertStatusInfo("tunnel", "0", tunnel, tunnel_alarm);
			Map<String, String> _cps = convertStatusInfo("cps", "0", cps, cps_alarm);
			Map<String, String> _rtt = convertStatusInfo("rtt", "0", rtt, rtt_alarm);
			Map<String, String> _rx_byte = convertStatusInfo("rx_byte", "0", rx_bytes, rbytes_alarm);
			Map<String, String> _rx_pkt = convertStatusInfo("rx_pkt", "0", rx_pkts, rpkts_alarm);
			Map<String, String> _rx_err = convertStatusInfo("rx_err", "0", rx_errs, "0");
			Map<String, String> _rx_drops = convertStatusInfo("rx_drops", "0", rx_drops, "0");
			Map<String, String> _tx_byte = convertStatusInfo("tx_byte", "0", tx_bytes, tbytes_alarm);
			Map<String, String> _tx_pkt = convertStatusInfo("tx_pkt", "0", tx_pkts, tpkts_alarm);
			Map<String, String> _tx_err = convertStatusInfo("rx_drops", "0", tx_errs, "0");
			Map<String, String> _tx_drops = convertStatusInfo("rx_drops", "0", tx_drops, "0");
			Map<String, String> _cpu_temp = convertStatusInfo("cpu_temp","0", cpu_temp, cpu_temp_alarm);
			Map<String, String> _system_temp = convertStatusInfo("system_temp","0", system_temp, system_temp_alarm);
			
			List<Map<String, String>> states_list = new ArrayList<Map<String,String>>();
			states_list.add(_cpu);
			states_list.add(_mem);
			states_list.add(_disk0);
			states_list.add(_disk1);
			states_list.add(_session);
			states_list.add(_host);
			states_list.add(_tunnel);
			states_list.add(_cps);
			states_list.add(_rtt);
			states_list.add(_rx_byte);
			states_list.add(_rx_pkt);
			states_list.add(_rx_err);
			states_list.add(_rx_drops);
			states_list.add(_tx_byte);
			states_list.add(_tx_pkt);
			states_list.add(_tx_err);
			states_list.add(_tx_drops);
			states_list.add(_cpu_temp);
			states_list.add(_system_temp);
			
			String status = status0;
			
			if("1".equals(status1)) {
				for(int k=0; k<states_list.size(); k++) {
					if("1".equals(states_list.get(k).get("state"))) {
			            status = "경고";
			            break;
					}
				}
			}
			
			Map<String, Object> statesMap = new HashMap<String, Object>();
			statesMap.put("cpu",_cpu);
			statesMap.put("mem",_mem);
			statesMap.put("disk0",_disk0);
			statesMap.put("disk1",_disk1);
			statesMap.put("session",_session);
			statesMap.put("host",_host);
			statesMap.put("tunnel",_tunnel);
			statesMap.put("cps",_cps);
			statesMap.put("rtt",_rtt);
			statesMap.put("rx_byte",_rx_byte);
			statesMap.put("rx_pkt",_rx_pkt);
			statesMap.put("rx_err",_rx_err);
			statesMap.put("rx_drops",_rx_drops);
			statesMap.put("tx_byte",_tx_byte);
			statesMap.put("tx_pkt",_tx_pkt);
			statesMap.put("tx_err",_tx_err);
			statesMap.put("tx_drops",_tx_drops);
			statesMap.put("cpu_temp",_cpu_temp);
			statesMap.put("system_temp",_system_temp);
			
			String code = "";
			if(!code1.isBlank() && !code2.isBlank()) {
				code = code1 + "-" + code2;
			}
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("deviceID", device_id);
			resultMap.put("gn", gn);
			resultMap.put("dn", dn);
			resultMap.put("ip", d_ip);
			resultMap.put("code", code);
			resultMap.put("hn", hostname);
			resultMap.put("product_id", product_id);
			resultMap.put("agent", agent);
			resultMap.put("os", os);
			resultMap.put("rsrp", rsrp);
			resultMap.put("tunnel_info", tunnel_info);
			resultMap.put("lte_vender", lte_vender);
			resultMap.put("lte_number", lte_number);
			resultMap.put("duration", duration);
			resultMap.put("states", statesMap);
			resultMap.put("interfaces", interfaces);
			resultMap.put("ips", ips_str);
			resultMap.put("eixs", eixs);
			resultMap.put("tracks", tracks);
			resultMap.put("vrrps", vrrps);
			
			resultMap.put("redudant_power1", redudant_power1);
			resultMap.put("redudant_power2", redudant_power2);
			resultMap.put("cert_expire_time", _cert_expire_time);
			resultMap.put("cert_state", _cert_state);
			resultMap.put("cert_info", _cert_info);
			resultMap.put("consoles", consoles);
			resultMap.put("powers", powers);
			resultMap.put("time", updowntime);
			resultMap.put("status", status);
			resultMap.put("longtime_down", longtime_down);
			resultMap.put("updown_repeat", updown_repeat);
			
			result.add(resultMap);
		}
		
		return result;
	}
	
	public List<Map<String, Object>> getDeviceInterfaceInfo(MonitoringVo monitoringVo) throws Exception{
		
		String rs_deviceID = monitoringVo.getDeviceID();
		
		List<Map<String, Object>> list = monitoringMapper.getDeviceInterfaceInfo(rs_deviceID);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> resultMap = new LinkedHashMap<>();
			String name = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("name")));
			int status = (int)list.get(i).get("status");
			String ip = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("ip")));
			String netmask = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("netmask")));
			String mac = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("mac")));
			String desc = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("desc")));
			
			resultMap.put("name", name);
			resultMap.put("status", status);
			resultMap.put("ip", ip);
			resultMap.put("netmask", netmask);
			resultMap.put("mac", mac);
			resultMap.put("desc", desc);
			
			result.add(resultMap);
			
			
			}
		
		return result;
	}
	
	public List<Map<String, Object>> getInterMonInfo(MonitoringVo monitoringVo) throws Exception{
		
		String rs_uid = monitoringVo.getUid();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String monitoring_intergrated = monitoringMapper.getInterMonitorTable(rs_uid);
		monitoring_intergrated = CommonUtil.setEmptyString(String.valueOf(monitoring_intergrated));
		if(monitoring_intergrated.isBlank()) {
			resultMap.put("FW", new ArrayList<>());
			resultMap.put("UTM", new ArrayList<>());
			resultMap.put("SW", new ArrayList<>());
		}else {
			resultMap = new ObjectMapper().readValue(monitoring_intergrated, Map.class);
		}
		List<Map<String, Object>> result = new ArrayList<>();
		result.add(resultMap);
		
		return result;
	}
	
	public List<Map<String, Object>> topN(MonitoringVo monitoringVo) throws Exception{
		
		String rs_target = !monitoringVo.getTarget().isBlank() ? monitoringVo.getTarget() : "CPU";
		int rs_viewCount = monitoringVo.getViewCount();
		List<String> rs_deviceIDs = monitoringVo.getDeviceIDs();
		String deviceIds = String.join(",", rs_deviceIDs);
		int viewCount = 5;
		
		int res_type = Integer.parseInt(MonitorEnum.monitorType.valueOf(rs_target).getVal());
		
		if(rs_viewCount > 0) {
			viewCount = rs_viewCount; 
		}
		
		List<Map<String, Object>> list = topN.get_topn(deviceIds, res_type, viewCount);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> resultMap = new LinkedHashMap<>();
			String gn = (String) list.get(i).get("device_group_name");
			String dn = (String) list.get(i).get("device_name");
			long total = 0;
			int _total = 0;
			long used = 0;
			int num = 0;
			long dbm = 0;
			String rates = "";
			String deviceID = "";
			if(res_type >= 5 && res_type <= 8) {
				String fieldName = "free";
				if(res_type >= 7 || res_type == 5) {
					fieldName = "used";
				}
				if(res_type == 5) {
					total =  convertValue((long) list.get(i).get("mem_total"));
					used = (long) list.get(i).get("mem_used");
					rates = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("mem")));
					deviceID = String.valueOf(list.get(i).get("id"));
				}else if(res_type == 6) {
					total =  convertValue((long) list.get(i).get("smem_total"));
					used = (long) list.get(i).get("smem_used");
					rates = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("smem")));
					deviceID = String.valueOf(list.get(i).get("id"));
				}else if(res_type == 7) {
					total =  convertValue((long) list.get(i).get("disk0_total"));
					used = (long) list.get(i).get("disk0_used");
					rates = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("disk0")));
					deviceID = String.valueOf(list.get(i).get("id"));
				}else if(res_type == 8) {
					total =  convertValue((long) list.get(i).get("disk1_total"));
					used = (long) list.get(i).get("disk1_used");
					rates = CommonUtil.setEmptyString(String.valueOf(list.get(i).get("disk1")));
					deviceID = String.valueOf(list.get(i).get("id"));
				}
				resultMap.put("gn", gn);
				resultMap.put("dn", dn);
				resultMap.put("total", total);
				resultMap.put("used", used);
				resultMap.put("rates", !rates.isBlank() ? rates : "0");
				resultMap.put("deviceID", deviceID);
				result.add(resultMap);
			}else {
				if(res_type == 9) {
					num = convertValue((int) list.get(i).get("session"));
					_total =  convertValue((int) list.get(i).get("total"));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("num", num);
					resultMap.put("total", _total);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else if(res_type == 10) {
					num = convertValue((int) list.get(i).get("tunnel"));
					_total =  convertValue((int) list.get(i).get("total"));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("num", num);
					resultMap.put("total", _total);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else if(res_type == 17) {
					num = convertValue((int) list.get(i).get("host"));
					_total =  convertValue((int) list.get(i).get("total"));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("num", num);
					resultMap.put("total", _total);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else if(res_type == 19) {
					num = convertValue((int) list.get(i).get("cps"));
					_total =  convertValue((int) list.get(i).get("total"));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("num", num);
					resultMap.put("total", _total);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else if(res_type == 18) {
					dbm = convertValue(CommonUtil.setEmptyString(String.valueOf(list.get(i).get("rsrp"))));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("dbm", dbm);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else if(res_type == 18) {
					dbm = convertValue(CommonUtil.setEmptyString(String.valueOf(list.get(i).get("rsrp"))));
					deviceID = String.valueOf(list.get(i).get("id"));
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("dbm", dbm);
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}else {
					for(String key : list.get(i).keySet()){
						if(!"device_name".equals(key) && !"device_group_name".equals(key) && !"id".equals(key)) {
							rates = CommonUtil.setEmptyString(String.valueOf(list.get(i).get(key)));
							deviceID = String.valueOf(list.get(i).get("id"));
							
							
						}
		                
		            }
					resultMap.put("gn", gn);
					resultMap.put("dn", dn);
					resultMap.put("rates", !rates.isBlank() ? rates : "0");
					resultMap.put("deviceID", deviceID);
					result.add(resultMap);
				}
			}
		}
		
		return result;
	}
	
}
