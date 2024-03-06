package kr.nexg.esm.global.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.nexg.esm.global.mapper.GlobalMapper;

@Service
public class GlobalService {
	
	@Autowired
	GlobalMapper globalMapper;
	
	public List<Map<String, Object>> devices(Map<String, Object> paramMap) throws Exception{
		
		String parentId = (String) paramMap.get("node");
		parentId = !parentId.isBlank() ? parentId : "0";
		
		List<Map<String, Object>> list = globalMapper.devices(parentId);
		List<Map<String, Object>> result = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = new LinkedHashMap<>();
			
			map.put("id", String.valueOf(list.get(i).get("id")));
			map.put("text", list.get(i).get("name"));
			map.put("leaf", list.get(i).get("leaf"));
			
			result.add(map);
		}
		
		return result;
	}
	
}
