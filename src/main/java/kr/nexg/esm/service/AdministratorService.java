package kr.nexg.esm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.dto.AdministratorVo;
import kr.nexg.esm.mapper.AdministratorMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdministratorService {
	
	@Autowired
	AdministratorMapper administratorMapper;
	
	public List<Map<String, Object>> getUserInfo(AdministratorVo vo) {
		return administratorMapper.getUserInfo(vo);
		
	}
	
	
}
