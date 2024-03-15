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
	
}
