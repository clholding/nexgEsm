package kr.nexg.esm.devices.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.devices.dto.DevicesVo;
import kr.nexg.esm.devices.mapper.DevicesMapper;
import kr.nexg.esm.util.config;
import kr.nexg.esm.util.mode_convert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
	public List<Map<String, Object>> deviceAll(Map<String,String> paramMap) throws IOException, ParseException{
		
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        
    	String datas = paramMap.get("datas");
    	String mode = paramMap.get("mode");
    	
    	List<Map<String, Object>> group_list = null;
//    	List<Map[]> group_list;
    	List<Map<String, Object>> dev_list = null;
    	
        String schType = "all";
        String rsAuth = "0";
        String rsExmode = null;
        String rsMode = "ESM";
        
        try {
        	
            Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);

            schType = (String)config.setValue(rsDatas, "type", "all");  // all, group, detail
            rsAuth = (String)config.setValue(rsDatas, "auth", "0");
            rsExmode = (String)config.setValue(rsDatas, "mode", "None");
            rsMode = mode;  // FW, VForce, SW
            
        } catch (Exception e) {
            schType = "all";
            rsAuth = "0";
            rsMode = "ESM";  // FW, VForce, SW
        }
    	
        if ("1".equals(rsAuth)) {
            int modeValue = 0;
            if (!"None".equals(rsExmode)) {
                if ("ALL".equals(rsExmode)) {
                    modeValue = 0;
                }
            } else {
                modeValue = mode_convert.convert_modedata(rsMode);
            }

            group_list = new ArrayList<>();
            
            DevicesVo devicesVo = new DevicesVo();
            devicesVo.setId(authentication.getName());
            devicesVo.setMode(modeValue);
            
            List<Map<String, Object>> temp = devicesMapper.getDeviceGroupByLogin(devicesVo);

            Map<String, Object> newElement = new HashMap<>();
            newElement.put("id", 0);
            newElement.put("name", "전체");
            newElement.put("group_id", 0);
            newElement.put("1", 0);

            // Adding the new Map to the list
            group_list.add(newElement);
            
            log.info("group_list : "+group_list.toString());
            
            List<Integer> hashList = new ArrayList<>();
            for (Map<String, Object> el : temp) {
                hashList.add((Integer) el.get(0));
            }

            int pid = 0;
            for (Map<String, Object> el : temp) {
                pid = (Integer) el.get("group_id");
                if ((Integer) el.get("group_id") > 0 && !hashList.contains((Integer) el.get("group_id"))) {
                    pid = 0;
                }

                List<Map<String, Object>> children = new ArrayList<>();
                
                Map<String, Object> element = new HashMap<>();
                element.put("code1", el.get("code1"));
                element.put("state", el.get("state"));
                element.put("fail", el.get("fail"));
                element.put("name", el.get("name"));
                element.put("code2", el.get("code2"));
                element.put("total", el.get("total"));
                element.put("id", el.get("id"));
                element.put("children", children);

                // Adding the new Map to the list
                group_list.add(element);
                
            }

            dev_list = devicesMapper.getDeviceListByLogin(devicesVo);
            
        } else {
            group_list = devicesMapper.getDeviceGroup();
            dev_list = devicesMapper.getDeviceList();
        }
        
        log.info("group_list : "+group_list);
        log.info("dev_list : "+dev_list);

        List<Map<String, Object>> result = new ArrayList<>();
        
        Map<String, Object> groupMap = new LinkedHashMap<>();
        groupMap.put("text", group_list.get(0).get("name"));
        groupMap.put("state", group_list.get(0).get("1"));
        groupMap.put("id", group_list.get(0).get("id"));

        List<Map<String, Object>> children = loadChildGroup(group_list, dev_list);
        groupMap.put("children", children);

        result.add(groupMap);
        
        return result;
	};
	
    private static List<Map<String, Object>> loadChildGroup(List<Map<String, Object>> groupList, List<Map<String, Object>> devList) {
        List<Map<String, Object>> result = new ArrayList<>();

        for (Map<String, Object> group : groupList) {

        	if(!"0".equals(String.valueOf(group.get("id")))) {
        		
        		log.info(""+group.get("id"));
	            Map<String, Object> groupMap = new LinkedHashMap<>();
	            groupMap.put("text", group.get("name"));
	            groupMap.put("state", group.get("1"));
	            groupMap.put("id", group.get("id"));
	
	            List<Map<String, Object>> children = loadChildDevice(devList, (int) group.get("id"));
	            groupMap.put("children", children);
	
	            result.add(groupMap);
        	}
        }

        return result;
    }

    private static List<Map<String, Object>> loadChildDevice(List<Map<String, Object>> devList, int groupId) {
        List<Map<String, Object>> children = new ArrayList<>();

        for (Map<String, Object> dev : devList) {
            if (dev.get("group_id").equals(groupId)) {
                Map<String, Object> devMap = new LinkedHashMap<>();
                devMap.put("id", dev.get("id"));
                devMap.put("leaf", dev.get("true"));
                devMap.put("text", dev.get("name"));
                devMap.put("ip", dev.get("ip"));
                devMap.put("state", 1);
                devMap.put("serial", dev.get("serial"));
                devMap.put("registerDate", dev.get("cdate"));
                devMap.put("active", dev.get("active"));
                devMap.put("log", dev.get("log"));
                devMap.put("alarm", dev.get("alarm"));
                devMap.put("code1", dev.get("code1"));
                devMap.put("code2", dev.get("code2"));
                devMap.put("os", dev.get("os"));
            	
//                List<Map<String, Object>> emptyChildren = new ArrayList<>();
//                devMap.put("children", emptyChildren);  // Dev nodes don't have further children

                children.add(dev);
            }
        }

        return children;
    }

    /*
     * 제품정보 리스트
     */
	public List<Map<String, Object>> getDeviceGroupList() throws IOException, ParseException{
		
		return devicesMapper.getDeviceGroup();
	}
	

	public Map<String, Object> getDeviceGroupInfo(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String id = (String)config.setValue(rsDatas, "id", null);
		
		log.info("id : "+id);
		
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setId(id);		
		
		return devicesMapper.getDeviceGroupInfo(devicesVo);
	}
	
	/*
	 * 장비 정보 조회
	 */
	public Map<String, Object> getDeviceInfo(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String id = (String)config.setValue(rsDatas, "deviceID", null);
		
        log.info("id : "+id);

        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setId(id);		
        
		return devicesMapper.getDeviceInfo(devicesVo);
	}
	
    /*
     * 장비 리스트 정보 조회
     */
	public List<Map<String, Object>> getDeviceInfoList(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(datas);
        JsonNode deviceIDsNode = jsonNode.get("deviceIDs");
        
        StringBuilder deviceIDs = new StringBuilder();
        if (deviceIDsNode != null && deviceIDsNode.isArray()) {
        	int i = 0;
            for (JsonNode idNode : deviceIDsNode) {
                String idValue = idNode.get("id").asText();
                if(i == 0) {
                	deviceIDs.append(idValue);
                }else {
                	deviceIDs.append(","+idValue);
                }
                
                i++;
            }
        }
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setDeviceIDs(deviceIDs.toString());
//		// 결과 출력
		log.info("deviceIDs : "+devicesVo.getDeviceIDs());
		
		return devicesMapper.getDeviceInfoList(devicesVo);
	}
	
    /*
     * 제품정보 리스트
     */
	public List<Map<String, Object>> getProductList(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String type = (String)config.setValue(rsDatas, "type", null);
		
        log.info("type : "+type);

        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setType(type);		
		
		return devicesMapper.getProductList(devicesVo);
	}
	
	/*
	 * 제품실패 정보
	 */
	public List<Map<String, Object>> getDeviceFailInfo(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String failID = (String)config.setValue(rsDatas, "failID", "");
		
		log.info("failID : "+failID);
		
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setFailID(failID);		
		
		return devicesMapper.getDeviceFailInfo(devicesVo);
	}
	
	/*
	 * 메모내용 수정
	 */
	public int setFailMemo(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String failID = (String)config.setValue(rsDatas, "failID", "");
		String type = (String)config.setValue(rsDatas, "type", "3");
		String memo = (String)config.setValue(rsDatas, "memo", "");
		
		log.info("failID : "+failID);
		log.info("type : "+type);
		log.info("memo : "+memo);
		
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setFailID(failID);		
		devicesVo.setType(type);		
		devicesVo.setMemo(memo);		
		
		return devicesMapper.setFailMemo(devicesVo);
	}

}

