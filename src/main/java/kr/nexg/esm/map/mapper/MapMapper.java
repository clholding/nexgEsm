package kr.nexg.esm.map.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.dto.AdministratorVo;

@Repository
@Mapper
public interface MapMapper {
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo);
	
	
}
