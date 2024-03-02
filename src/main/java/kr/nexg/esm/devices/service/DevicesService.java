package kr.nexg.esm.devices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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
            
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            
            DevicesVo devicesVo = new DevicesVo();
            devicesVo.setSessionId(authentication.getName());
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
//        groupMap.put("state", group_list.get(0).get("state"));
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
	            groupMap.put("code1", group.get("code1"));
	            groupMap.put("state", group.get("state"));
	            groupMap.put("fail", group.get("fail"));
	            groupMap.put("text", group.get("name"));
	            groupMap.put("code2", group.get("code2"));
	            groupMap.put("total", group.get("total"));
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
                devMap.put("leaf", dev.get("leaf"));
                devMap.put("text", dev.get("text"));
                devMap.put("ip", dev.get("ip"));
                devMap.put("state", dev.get("state"));
                devMap.put("serial", dev.get("serial"));
                devMap.put("registerDate", dev.get("cdate"));
                devMap.put("active", dev.get("active"));
                devMap.put("log", dev.get("log"));
                devMap.put("alarm", dev.get("alarm"));
                devMap.put("code1", dev.get("code1"));
                devMap.put("code2", dev.get("code2"));
                devMap.put("os", dev.get("os"));
                devMap.put("vrrps", dev.get("vrrps"));
                devMap.put("intfs", dev.get("intfs"));
                devMap.put("intlist", dev.get("intlist"));
                devMap.put("eixs", dev.get("eixs"));
            	
//                List<Map<String, Object>> emptyChildren = new ArrayList<>();
//                devMap.put("children", emptyChildren);  // Dev nodes don't have further children

                children.add(devMap);
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
		devicesVo.setSessionId(id);		
		
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
        devicesVo.setSessionId(id);		
        
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
            for (JsonNode idNode : deviceIDsNode) {
                if (deviceIDs.length() > 0) {
                    deviceIDs.append(',');
                }
                deviceIDs.append(idNode.get("id").asText());
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
	 * 장비 리스트 조건 검색
	 */
	public List<Map<String, Object>> searchDeviceInfoList(Map<String,String> paramMap) throws IOException, ParseException{
		
		String rsMode = paramMap.get("mode");
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rsGroupID = (String)config.setValue(rsDatas, "groupID", "");
		log.info("rsGroupID : "+rsGroupID);

		String rsDeviceIDs = "";
		int mode = 0;
		
		if("".equals(rsGroupID)) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        JsonNode jsonNode = objectMapper.readTree(datas);
	        JsonNode deviceIDsNode = jsonNode.get("deviceIDs");
	        
	        StringBuilder deviceIDs = new StringBuilder();
	        if (deviceIDsNode != null && deviceIDsNode.isArray()) {
	            for (JsonNode idNode : deviceIDsNode) {
	                if (deviceIDs.length() > 0) {
	                    deviceIDs.append(',');
	                }
	                deviceIDs.append(idNode.asText());
	            }
	        }
	        
	        rsDeviceIDs = deviceIDsNode.toString().replace("\"", "");
	        rsDeviceIDs = rsDeviceIDs.replace("[", "").replace("]", "");
	        log.info("deviceIDsNode : "+deviceIDsNode.toString());
			
		}else {
			
	        SecurityContext context = SecurityContextHolder.getContext();
	        Authentication authentication = context.getAuthentication();
//	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        
			mode = mode_convert.convert_modedata(rsMode);
			
			DevicesVo devicesVo = new DevicesVo();
			devicesVo.setGroupID(Integer.parseInt(rsGroupID));
			devicesVo.setSessionId(authentication.getName());
			devicesVo.setMode(mode);
			rsDeviceIDs = devicesMapper.getGroupToDeviceListByLogin(devicesVo);
		}
		
	    String rsDname 		= (String)config.setValue(rsDatas, "dn", "");  				// 장비이름
	    String rsGname 		= (String)config.setValue(rsDatas, "gn", "");  				// 그룹이름
	    String rsProductId  = (String)config.setValue(rsDatas, "product_id", null);     // 제품id
	    String rsIp 		= (String)config.setValue(rsDatas, "ip", "");  				// 장비ip
	    String rsSerial 	= (String)config.setValue(rsDatas, "serial", "");  		    // 시리얼
	    String rsOs 		= (String)config.setValue(rsDatas, "os", "");  				// 장비os
	    String rsAgent 		= (String)config.setValue(rsDatas, "agent", "");  			// agent 버전
	    String rsDesc 		= (String)config.setValue(rsDatas, "desc", "");  			// 장비설명
	    String rsCompany	= (String)config.setValue(rsDatas, "company", "");  		// 고객사
	    String rsPhone1 	= (String)config.setValue(rsDatas, "phone1", "");  		    // 전화1
	    String rsFax 		= (String)config.setValue(rsDatas, "fax", "");  			// fax
	    String rsZip 		= (String)config.setValue(rsDatas, "zip", "");  			// 우편번호
	    String rsAddress 	= (String)config.setValue(rsDatas, "address", "");  		// 주소
	    String rsCustomer 	= (String)config.setValue(rsDatas, "customer", "");  		// 고객이름
	    String rsPhone2 	= (String)config.setValue(rsDatas, "phone2", "");  			// 전화2
	    String rsEmail 		= (String)config.setValue(rsDatas, "email", "");  			// 이메일
		
	    int rsPage 			= Integer.valueOf((String) config.setValue(rsDatas, "page", "1"));
		int rsViewCount 	= Integer.valueOf((String) config.setValue(rsDatas, "viewCount", "25"));
	    rsPage 				= ((rsPage) - 1) * ((rsViewCount));
	    	    
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setDeviceIDs(rsDeviceIDs);
		devicesVo.setDname(rsDname);
		devicesVo.setGname(rsGname);
		devicesVo.setProductId(rsProductId);
		devicesVo.setIp(rsIp);
		devicesVo.setSerial(rsSerial);
		devicesVo.setOs(rsOs);
		devicesVo.setAgent(rsAgent);
		devicesVo.setDesc(rsDesc);
		devicesVo.setCompany(rsCompany);
		devicesVo.setPhone1(rsPhone1);
		devicesVo.setFax(rsFax);
		devicesVo.setZip(rsZip);
		devicesVo.setAddress(rsAddress);
		devicesVo.setCustomer(rsCustomer);
		devicesVo.setPhone2(rsPhone2);
		devicesVo.setEmail(rsEmail);
		devicesVo.setPage(String.valueOf(rsPage));
		devicesVo.setViewCount(String.valueOf(rsViewCount));
		
		return devicesMapper.searchDeviceInfoList(devicesVo);
	}
	
	/*
	 * 그룹 추가/수정
	 */
	public Map<String, Object> setDeviceGroupInfo(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		
	    String rsGroupID 		= (String)config.setValue(rsDatas, "id", null);
	    String rsName			= (String)config.setValue(rsDatas, "name", "");
	    	    
//	    	    validation.groupAdd_name(rs_name);
	    String rsDesc		    = (String)config.setValue(rsDatas, "desc", "");
	    String rsParentsGroupID = (String)config.setValue(rsDatas, "pGroupID", "");
	    String rsCode1 			= (String)config.setValue(rsDatas, "code1", "00000000");    // 그룹 관리번호
	    String rsCode2 			= (String)config.setValue(rsDatas, "code2", "0");    		// 등급 (1~10)
	    String rsCompany 		= (String)config.setValue(rsDatas, "company", "");  		// 고객사
	    String rsCustomer 		= (String)config.setValue(rsDatas, "customer", "");  		// 고객이름
	    String rsEmail 			= (String)config.setValue(rsDatas, "email", "");  			// 이메일
	    String rsZip 			= (String)config.setValue(rsDatas, "zip", "");  			// 우편번호
	    String rsAddress 		= (String)config.setValue(rsDatas, "address", "");  		// 주소
	    String rsPhone1 		= (String)config.setValue(rsDatas, "phone1", "");  			// 전화1
	    String rsPhone2 		= (String)config.setValue(rsDatas, "phone2", "");  			// 전화2
	    String rsFax 			= (String)config.setValue(rsDatas, "fax", "");  			// fax
	    String rsMemo1 			= (String)config.setValue(rsDatas, "memo1", "");  			// memo1
	    String rsMemo2 			= (String)config.setValue(rsDatas, "memo2", "");  			// memo2
	    
		String message = "장비/그룹이 수정되었습니다.";
	    
	    if(rsGroupID == null) {
	    	message = "장비/그룹이 추가되었습니다.";
	    }
	    
	    int gp = 0;
        if (!rsParentsGroupID.equals("")) {
        	gp = Integer.parseInt(rsParentsGroupID);
        }
        
        String topGroup = "전체";
        
        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setName(rsName);
        int cnt = devicesMapper.deviceGroupCnt(devicesVo);
        if ((cnt > 0 || rsName.equals(topGroup)) && rsGroupID == null) {
        	message = "동일한 장비 그룹 이름이 존재합니다.";
        }else {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            
        	devicesVo.setGroupID(Integer.parseInt(rsGroupID));
        	devicesVo.setDesc(rsDesc);
        	devicesVo.setName(rsName);
        	devicesVo.setGp(String.valueOf(gp));
        	devicesVo.setSessionId(authentication.getName());
        	devicesVo.setCode1(rsCode1);
        	devicesVo.setCode2(rsCode2);
        	devicesVo.setCompany(rsCompany);
        	devicesVo.setCustomer(rsCustomer);
        	devicesVo.setEmail(rsEmail);
        	devicesVo.setZip(rsZip);
        	devicesVo.setAddress(rsAddress);
        	devicesVo.setPhone1(rsPhone1);
        	devicesVo.setPhone2(rsPhone2);
        	devicesVo.setFax(rsFax);
        	devicesVo.setMemo1(rsMemo1);
        	devicesVo.setMemo2(rsMemo2);
        	Map<String, Object> map = devicesMapper.setDeviceGroupInfo(devicesVo);
        	if("0".equals(map.get("select_id"))) {
        		message = "동일한 장비 그룹 이름이 존재합니다.";
        	}
        }
        
        Map<String, Object> map = new HashMap<String,Object>(); 
        map.put("message", message);
        
		return map;
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

