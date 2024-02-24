package kr.nexg.esm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.dto.DevicesVo;

@Repository
@Mapper
public interface DevicesMapper {
	
	public List<Map<String, Object>> getDeviceGroupByLogin(DevicesVo devicesVo);
	public List<Map<String, Object>> getDeviceListByLogin(DevicesVo devicesVo);
	public List<Map<String, Object>> getDeviceGroup();
	public List<Map<String, Object>> getDeviceList();
	
	public List<Map<String, Object>> getDeviceInfoList(DevicesVo devicesVo);
	
	public List<Map<String, Object>> getProductList(DevicesVo devicesVo);
	
	
}
