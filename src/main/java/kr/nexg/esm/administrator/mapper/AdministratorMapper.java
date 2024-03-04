package kr.nexg.esm.administrator.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.administrator.dto.AdministratorVo;

@Repository
@Mapper
public interface AdministratorMapper {
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo);
	
	public List<Map<String, Object>> getUser(AdministratorVo vo);
	
	public int delUser(String adminIds);
	
	public List<Map<String, Object>> selectAdminGroup(String adminGroupID);
	
	public List<Map<String, Object>> selectUserGroup(AdministratorVo vo);
	
	public int delUserGroup(String adminGroupIds);
	
	public List<Map<String, Object>> selectDeviceGroup(AdministratorVo vo);
	
	public List<Map<String, Object>> selectDeviceList(String deviceId);
	
	public List<Map<String, Object>> selectUserByID(String adminId);
	
	
}
