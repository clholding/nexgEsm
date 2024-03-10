package kr.nexg.esm.logs.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface LogsMapper {
	
	public List<Map<String, Object>> lastFailDevice(String parentId);
	
	
}
