package kr.nexg.esm.nexgesm.mariadb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogMapper {
	
	public void addAuditLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getLastFailDevice(Map<String, Object> map);
	
	public List<Map<String, Object>> getDeviceListByLogin(String user, int type, String mode);
	
	public List<Map<String, Object>> getRealtimeEvent(String ids, int interval);
	
	public List<Map<String, Object>> getInputLog(String deviceIds);
	
	public List<Map<String, Object>> getRebootLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getFailLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getEsmAuditLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getAlarmLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getResourceLog(Map<String, Object> map);
	
	public List<Map<String, Object>> getCommandLog(Map<String, Object> map);
	
	public int addLogBox(Map<String, Object> map);
	
	
}
