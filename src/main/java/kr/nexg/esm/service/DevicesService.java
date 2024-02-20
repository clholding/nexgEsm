package kr.nexg.esm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.dto.DevicesRVo;
import kr.nexg.esm.dto.DevicesVo;
import kr.nexg.esm.mapper.DevicesMapper;

@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
	public List<DevicesRVo> getProductList(DevicesVo devicesVo){
		
		return devicesMapper.getProductList(devicesVo);
	};
}
