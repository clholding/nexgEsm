package kr.nexg.esm.global.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GlobalMapper {
	
	public List<Map<String, Object>> devices(String parentId);
	
	public List<Map<String, Object>> getUserInfoByLogin(String sessionId);
	
	public List<Map<String, Object>> getDeviceStatusByLogin(String sessionId, String mode);
	
	public List<Map<String, Object>> getDeviceFaultStatus(String sessionId, String deviceIds, String mode);
	
	public List<Map<String, Object>> getAllDeviceFaultStatus(String sessionId, String deviceIds);
	
	String selectUserStatus(String sessionId);
	
	int updateHbtime(String sessionId);
	
	String getApplyStatus();
	
	
}
