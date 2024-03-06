package kr.nexg.esm.global.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface GlobalMapper {
	
	public List<Map<String, Object>> devices(String parentId);
	
	
}
