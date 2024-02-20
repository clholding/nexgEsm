package kr.nexg.esm.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.dto.DevicesRVo;
import kr.nexg.esm.dto.DevicesVo;

@Repository
@Mapper
public interface DevicesMapper {
	
	public List<DevicesRVo> getProductList(DevicesVo devicesVo);
	
	
}
