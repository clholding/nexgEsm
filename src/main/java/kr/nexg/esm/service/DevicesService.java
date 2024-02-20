package kr.nexg.esm.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.dto.DevicesRVo;
import kr.nexg.esm.dto.DevicesVo;
import kr.nexg.esm.mapper.DevicesMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
	public List<DevicesRVo> getProductList(Map<String,String> paramMap) throws IOException{
		
    	String datas = paramMap.get("datas");
    	
        // ObjectMapper 생성
        ObjectMapper objectMapper = new ObjectMapper();

        // JSON 데이터 파싱
        JsonNode jsonNode = objectMapper.readTree(datas);

        // 특정 키에 대한 값(value) 꺼내기
        String type = jsonNode.get("type").asText();
        
        // 결과 출력
        log.info("type : "+type);

    	DevicesVo devicesVo = new DevicesVo();
    	devicesVo.setType(type);
    	
		return devicesMapper.getProductList(devicesVo);
	};
}
