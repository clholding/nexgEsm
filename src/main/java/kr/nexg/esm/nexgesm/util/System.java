package kr.nexg.esm.nexgesm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kr.nexg.esm.common.util.ProcessUtil;

public class System {
	
	public Map<String, String> get_version() {
		return null;
	}
	
	public String set_agent_id() {
		return "";
	}
	
	public void set_agent_id(int did, String agent_id) {
	}
	
	public List<Map<String, Object>> get_agent_id(String did) {
		return null;
	}
	
	public Map<String, Double> esm_status() {
		double cpuUsage = ProcessUtil.cpuUsagePercentMonitor();
		Map<String, Long> disk0 = ProcessUtil.diskUsageMonitor("C:\\Temp\\");
		Map<String, Long> disk1 = ProcessUtil.diskUsageMonitor("C:\\logs\\");
		Map<String, Long> mem = ProcessUtil.memoryUsageMonitor();
		
		Map<String, Double> result = new HashMap<>();
		result.put("cpu", cpuUsage);
		result.put("disk0_total", (double)(disk0.get("total")/1024));
		result.put("disk0_used", (double)(disk0.get("used")/1024));
		result.put("disk1_total", (double)(disk1.get("total")/1024));
		result.put("disk1_used", (double)(disk1.get("used")/1024));
		result.put("mem_total",(double)(mem.get("heapMax")/1024));
		result.put("mem_available", (double)(mem.get("heapAvail")/1024));
		return result;
	}
	
}
