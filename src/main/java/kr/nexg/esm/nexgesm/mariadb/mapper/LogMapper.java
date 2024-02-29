package kr.nexg.esm.nexgesm.mariadb.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogMapper {
	
	public void addAuditLog(Map<String, Object> map);
	
}
