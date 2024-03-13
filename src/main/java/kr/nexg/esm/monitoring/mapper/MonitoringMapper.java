package kr.nexg.esm.monitoring.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MonitoringMapper {
	
	public List<Map<String, Object>> devices(String parentId);
	
	public String getGroupToDeviceListByLogin(String groupId, String sessionId, String mode);
	
	public List<Map<String, Object>> getIntStatus(String deviceId);
	
	public List<Map<String, Object>> getTrackStatus(String deviceId);
	
	public List<Map<String, Object>> eixStatus(String deviceId);
	
	public List<Map<String, Object>> vrrpStatus(String deviceId);
	
	public List<Map<String, Object>> getFailInterface(String deviceId);
	
	
}
