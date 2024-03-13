package kr.nexg.esm.nexgesm.mariadb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MonitorMapper {
	
	public List<Map<String, Object>> getDeviceStatus(String deviceIds);
	
	
}
