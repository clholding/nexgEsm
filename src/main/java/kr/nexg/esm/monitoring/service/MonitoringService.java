package kr.nexg.esm.monitoring.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.monitoring.dto.MonitoringVo;
import kr.nexg.esm.monitoring.mapper.MonitoringMapper;

@Service
public class MonitoringService {
	
	@Autowired
	MonitoringMapper monitoringMapper;
	
	public List<Map<String, Object>> esmDevice(MonitoringVo monitoringVo) throws Exception{
		
		return null;
	}
	
}
