package kr.nexg.esm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.dto.AdministratorVo;

@Repository
@Mapper
public interface AdministratorMapper {
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo);
	
	
}
