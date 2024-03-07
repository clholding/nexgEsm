package kr.nexg.esm.devices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.devices.dto.DevicesVo;
import kr.nexg.esm.devices.mapper.DevicesMapper;
import kr.nexg.esm.util.Validation;
import kr.nexg.esm.util.config;
import kr.nexg.esm.util.mode_convert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
	  private static final Map<String, String> auditType = new HashMap<>();

	  static {
	    auditType.put("delDeviceInterface", "장비 인터페이스 리스트 삭제");
	    auditType.put("setDeviceInterface", "장비 인터페이스 리스트 추가");
	    auditType.put("updateDeviceInterface", "장비 인터페이스 정보 수정");
	    auditType.put("delDeviceNGroup", "장비/그룹 정보 삭제");
	    auditType.put("setDeviceNGroupName", "장비/그룹 이름 수정");
	    auditType.put("setDeviceInfo", "장비정보 ");
	    auditType.put("setDeviceGroupInfo", "장비/그룹정보 ");
	    auditType.put("setDeviceGroup", "장비/그룹 이동");
	  }
	  
    public static Map<String, String> convertStatusInfo(String type, String totalVal, String val, String alarmVal) {
        Map<String, String> result = new HashMap<>();

        log.info("type : "+type+", totalVal : "+totalVal+", val : "+val+", alarmVal : "+alarmVal);
        float m_val = (val == "null") ? 0 : Float.parseFloat(val);
        float m_alarmVal = (alarmVal == "null") ? 0 : Float.parseFloat(alarmVal);
        float m_totalVal = (totalVal == "null") ? 0 : Float.parseFloat(totalVal);

        String m_status = "1";

        if ("cpu".equals(type)) {
            if (m_val > m_alarmVal && m_alarmVal > 0) {
                m_status = "0";
            }
            m_val = Math.round(m_val * 100) / 100.0f;
        } else if (type.equals("traffic") || type.equals("session") || type.equals("tunnel")) {
            if (m_val > m_alarmVal && m_alarmVal > 0) {
                m_status = "0";
            }
            m_val = Math.round(m_val);
        } else {
            if (m_val > 0) {
                m_val = (m_totalVal - m_val) / m_totalVal * 100;
            }
            m_val = Math.round(m_val * 100) / 100.0f;
            if (m_val > m_alarmVal && m_alarmVal > 0) {
                m_status = "0";
            }
        }

        result.put("num", (m_val >= 0) ? Float.toString(m_val) : "0");
        result.put("state", (m_val < 0) ? "2" : m_status);

        return result;
    }
    
    public void setAuditInfo(String fn, String yn, String datasJson) throws JSONException {
        String auditMenu = "[장비관리]";
        String state = "성공";
        int auditlevel = 6;

        if ("false".equals(yn)) {
          state = "실패";
          auditlevel = 4;
        }

        if ("setDeviceNGroupName".equals(fn)) {
          auditMenu = "[토폴로지]";
        }

        JSONObject datas = new JSONObject(datasJson);
        String eMsg = auditMenu + auditType.get(fn); // Assuming auditType is a Map<String, String>

        String dInfo = "";

        if ("setDeviceInfo".equals(fn)) {
          dInfo = "추가";
          if (!"".equals(datas.getString("id"))) {
            dInfo = "수정";
          }
        } else if ("setDeviceGroupInfo".equals(fn)) {
          dInfo = "추가";
          if (!"null".equals(datas.getString("id"))) {
            dInfo = "수정";
          }
        }

        if (datas.has("dn")) {
//          eMsg = eMsg + dInfo + " " + state + " " + emsgToStr(datas); // Assuming emsgToStr is a method
        } else {
          if (!"setDeviceGroup".equals(fn)) {
//            eMsg = eMsg + dInfo + " " + state + " " + emsgToStr(datas); // Assuming emsgToStr is a method
          } else if ("setDeviceGroup".equals(fn)) {
            String tmpPrint = "";
            List<String> tmpArray = new ArrayList<>();
            
            DevicesVo devicesVo = new DevicesVo();
            devicesVo.setPGroupID(String.valueOf(datas.getString("pGroupID")));
            
            List<Map<String, Object>> tmpParentSearch = devicesMapper.deviceGroupNames(devicesVo);
            String tmpParent = String.valueOf(tmpParentSearch.get(0).get("name"));
            eMsg = eMsg + dInfo + " " + state + " ";
            
            if (datas.has("groupIDs") && datas.getJSONArray("groupIDs").length() > 0) {
              String tmpWhere = String.join(",", String.valueOf(datas.getJSONArray("groupIDs")));
              
              devicesVo = new DevicesVo();
              devicesVo.setGroupIDs(tmpWhere);
              
              tmpParentSearch = devicesMapper.deviceGroupNames(devicesVo);
              
              for (Map<String, Object> tmp : tmpParentSearch) {
                tmpArray.add(String.valueOf(tmp.get("name")));
              }
              eMsg = eMsg + "그룹 " + String.join(",", tmpArray) + " 를 그룹 " + tmpParent + " 로 이동하였습니다. ";
            }
            
            if (datas.has("deviceIDs") && datas.getJSONArray("deviceIDs").length() > 0) {
              tmpArray.clear();
              String tmpWhere = String.join(",", String.valueOf(datas.getJSONArray("deviceIDs")));
              
              devicesVo = new DevicesVo();
              devicesVo.setDeviceIDs(tmpWhere);
              
              tmpParentSearch = devicesMapper.deviceNames(devicesVo);
              
              for (Map<String, Object> tmp : tmpParentSearch) {
            	 tmpArray.add(String.valueOf(tmp.get("name")));
              }
              eMsg = eMsg + "장비 " + String.join(",", tmpArray) + " 를 그룹 " + tmpParent + " 로 이동하였습니다.";
            }
          }
        }

//        EsmAuditLog e = new EsmAuditLog();
//        e.esmlog(auditlevel, session.id, request.client, eMsg);
      }    
    
    /*
     * DeviceFinder
     */
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
                hashList.add((Integer) el.get("id"));
            }
            log.info("hashList : "+hashList.toString());

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
	
    /*
     * DeviceFinder > 개별정보 > 장비미리보기
     */  
	public List<Map<String, Object>> getDeviceStatus(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		String rsDeviceIDs = "";
		
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
		

        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setDeviceIDs(rsDeviceIDs);
        
		List<Map<String, Object>> result = new ArrayList<>();
		List<Map<String, Object>> list = devicesMapper.getDeviceStatus(devicesVo);
		for (Map<String, Object> vo : list) {
			
			
	        Map<String, String> statusCpu = convertStatusInfo("cpu", "0", String.valueOf(vo.get("cpu")), String.valueOf(vo.get("cpu_alarm")));
	        Map<String, String> statusMem = convertStatusInfo("mem", String.valueOf(vo.get("mem_total")), String.valueOf(vo.get("mem_avail")), String.valueOf(vo.get("mem_alarm")));
	        Map<String, String> statusDisk = convertStatusInfo("disk", String.valueOf(vo.get("disk0_total")), Integer.toString((Integer.parseInt(String.valueOf(vo.get("disk0_total"))) - Integer.parseInt(String.valueOf(vo.get("disk0_used"))))), String.valueOf(vo.get("disk0_alarm")));
	        Map<String, String> statusTraffic = convertStatusInfo("traffic", "0", String.valueOf(vo.get("rx_bytes")), String.valueOf(vo.get("rbytes_alarm")));
	        Map<String, String> statusSession = convertStatusInfo("session", "0", String.valueOf(vo.get("session")), String.valueOf(vo.get("session_alarm")));
	        Map<String, String> statusTunnel = convertStatusInfo("tunnel", "0", String.valueOf(vo.get("tunnel")), String.valueOf(vo.get("tunnel_alarm")));
	        
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("id", vo.get("device_id"));
			map.put("name", vo.get("name1"));
			map.put("imgPath", "/static/images/productType_2.png");
			map.put("deviceState", vo.get("status1"));
			map.put("active", vo.get("active"));
			map.put("rsrp", vo.get("rsrp"));
			map.put("location", vo.get("location"));
			map.put("tunnel_info", vo.get("tunnel_info"));
			map.put("status", Map.of(
	                "cpu", statusCpu,
	                "mem", statusMem,
	                "disk", statusDisk,
	                "traffic", statusTraffic,
	                "session", statusSession,
	                "tunnel", statusTunnel
	        ));
			
			result.add(map);
		}

		return result;
		
	}
	
	
    /*
     * 장비관리 > 장비추가리스트
     */
	public List<Map<String, Object>> deviceCandidate(Map<String,String> paramMap) throws IOException, ParseException{
		
		String rsMode = paramMap.get("mode");
		
		int mode = mode_convert.convert_modedata(rsMode);
		
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setMode(mode);
		
		return devicesMapper.deviceCandidate(devicesVo);
	}
	
    /*
     * 메인 화면 > 탑메뉴 > 시스템 설정 > 알람 > 장비/그릅 임계치 설정
     */	
	public List<Map<String, Object>> getAlarmDeviceGroupListNDeviceListAll(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		List<Map<String, Object>> group_list = null;
		List<Map<String, Object>> dev_list = null;
		
		String schType = "all";
		String rsAuth = "0";
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		
		schType = (String)config.setValue(rsDatas, "type", "all");  // all, group, detail
		rsAuth = (String)config.setValue(rsDatas, "auth", "0");
			
		
		if ("1".equals(rsAuth)) {
			
			group_list = new ArrayList<>();
			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			
			DevicesVo devicesVo = new DevicesVo();
			devicesVo.setSessionId(authentication.getName());
			
			List<Map<String, Object>> temp = devicesMapper.getAlarmDeviceGroupOfLogin(devicesVo);
			
			Map<String, Object> newElement = new HashMap<>();
			newElement.put("id", 0);
			newElement.put("name", "전체");
			newElement.put("group_id", 0);
			newElement.put("state", 0);
			
			// Adding the new Map to the list
			group_list.add(newElement);
			
			log.info("group_list : "+group_list.toString());
			
			List<Integer> hashList = new ArrayList<>();
			for (Map<String, Object> el : temp) {
				hashList.add((Integer) el.get("id"));
			}
			
			log.info("hashList : "+hashList);
			
			int pid = 0;
			for (Map<String, Object> el : temp) {
				pid = (Integer) el.get("group_id");
				log.info("pid : "+pid);
				if ((Integer) el.get("group_id") > 0 && !hashList.contains((Integer) el.get("group_id"))) {
					pid = 0;
				}
				
//				List<Map<String, Object>> children = new ArrayList<>();
				
				Map<String, Object> element = new HashMap<>();
				element.put("id", el.get("id"));
				element.put("name", el.get("name"));
				element.put("group_id", pid);
				element.put("state", el.get("state"));
//				element.put("children", children);
				
				// Adding the new Map to the list
				group_list.add(element);
				
			}
			
			log.info("group_list2 : "+group_list.toString());
			
			dev_list = devicesMapper.getAlarmDeviceListOfLogin(devicesVo);
			
		} else {
			group_list = devicesMapper.getDeviceGroup();
			dev_list = devicesMapper.getDeviceList();
		}
		
		log.info("group_list : "+group_list);
		log.info("dev_list : "+dev_list);
		
		List<Map<String, Object>> result = new ArrayList<>();
		
		Map<String, Object> groupMap = new LinkedHashMap<>();
		groupMap.put("text", group_list.get(0).get("name"));
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
	            groupMap.put("state", group.get("state"));
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
                devMap.put("eixs", dev.get("eixs"));
                devMap.put("tracks", dev.get("tracks"));
                devMap.put("vrrps", dev.get("vrrps"));
                devMap.put("active", dev.get("active"));
                devMap.put("serial", dev.get("serial"));
                devMap.put("id", dev.get("id"));
                devMap.put("code1", dev.get("code1"));
                devMap.put("code2", dev.get("code2"));
                devMap.put("intfs", dev.get("intfs"));
                devMap.put("leaf", dev.get("leaf"));
                devMap.put("state", dev.get("status"));
                devMap.put("text", dev.get("text"));
                devMap.put("ip", dev.get("ip"));
                devMap.put("intlist", dev.get("intlist"));
            	
//                List<Map<String, Object>> emptyChildren = new ArrayList<>();
//                devMap.put("children", emptyChildren);  // Dev nodes don't have further children

                children.add(devMap);
            }
        }

        return children;
    }

    /*
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 > 그룹
     */
	public List<Map<String, Object>> getDeviceGroupList() throws IOException, ParseException{
		
		return devicesMapper.getDeviceGroup();
	}
	

    /*
     * DeviceFinder > 그룹정보 > 정보 > 기본정보
     */
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
     * DeviceFinder > 개별정보 > 정보 > 기본정보
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 
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
     * DeviceFinder > 개별정보 > 정보 > 인터페이스
     */
	public List<Map<String, Object>> getDeviceInterface(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String rsId = (String)config.setValue(rsDatas, "deviceID", "");
		String rsIsActive = (String)config.setValue(rsDatas, "isActive", "");
	    
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setId(rsId);
		devicesVo.setIsActive(rsIsActive);
		
		List<Map<String, Object>> result = new ArrayList<>();
		List<Map<String, Object>> list = devicesMapper.getDeviceInterface(devicesVo);
		for (Map<String, Object> vo : list) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("id", vo.get("id"));
			map.put("name", vo.get("name"));
			map.put("active", vo.get("active"));
			map.put("ip", vo.get("id"));
			map.put("netmask", vo.get("netmask"));
			map.put("mac", vo.get("mac"));
			map.put("location", vo.get("location"));
			map.put("desc", vo.get("desc"));
			map.put("state", vo.get("status"));
			map.put("wan", vo.get("wan"));
			
			result.add(map);
		}

		return result;
	}
	
    /*
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 제품명
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
	 * DeviceFinder > 개별정보 > 정보 > 장애내역
	 */  
	public List<Map<String, Object>> getDeviceFailList(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		String rsPage = paramMap.get("page");
		String rsLimit = paramMap.get("limit");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		String deviceID = (String)config.setValue(rsDatas, "deviceID", "");
		
		int rsStart = (Integer.parseInt(rsPage) - 1) * Integer.parseInt(rsLimit);
		
		log.info("deviceID : "+deviceID);
		
		DevicesVo devicesVo = new DevicesVo();
		devicesVo.setDeviceID(deviceID);
		devicesVo.setPage(String.valueOf(rsStart));
		devicesVo.setLimit(rsLimit);
		
		return devicesMapper.getDeviceFailList(devicesVo);
	}
	
	/*
	 * 메인화면 > SideBar > 자산이력
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
	 * DeviceFinder > 개별정보 > 정보 > 기본정보 > 관리번호 중복체크
	 */
	public Map<String, Object> checkManagedCode(Map<String,String> paramMap) throws IOException, ParseException{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		
	    String rsType 			= (String)config.setValue(rsDatas, "type", "0");
	    String rsCode1			= (String)config.setValue(rsDatas, "code1", "");
	    String rsCode2			= (String)config.setValue(rsDatas, "code2", "");
	    	    
        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setType(rsType);
        devicesVo.setCode1(rsCode1);
        devicesVo.setCode2(rsCode2);
        
	    int cnt = devicesMapper.checkManagedCode(devicesVo);
	    log.info("cnt : "+cnt);
	    
	    String message = "";
	    String success = "";
	    
	    if(cnt == 0) {
	    	success = "false";
	    	if("0".equals(rsType)) {
	    		message = "동일한 관리번호 및 일련번호가 존재합니다.";
	    	}else {
	    		message = "동일한 관리번호 및 등급이 존재합니다.";
	    	}
	    }else {
	    	success = "true";
	    	if("0".equals(rsType)) {
	    		message = "관리번호 및 일련번호가 확인되었습니다.";
	    	}else {
	    		message = "관리번호 및 등급이 확인되었습니다.";
	    	}	    	
	    }
	    
        Map<String, Object> map = new HashMap<String,Object>(); 
        map.put("message", message);
        map.put("success", success);
        
		return map;
	}
	
    /*
     * DeviceFinder > 그룹정보 > 정보 > 기본정보 > 저장
     */
	public Map<String, Object> setDeviceGroupInfo(Map<String,String> paramMap) throws Exception{
		
		String datas = paramMap.get("datas");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		
	    String rsGroupID 		= (String)config.setValue(rsDatas, "id", null);
	    String rsName			= (String)config.setValue(rsDatas, "name", "");
	    	    
//	    # 유효성 검사
   	    Validation.groupAdd_name(rsName);
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
     * DeviceFinder -> 개별정보 -> 정보 -> 기본정보 -> 저장
     */
	public Map<String, Object> setDeviceInfo(Map<String,String> paramMap) throws Exception{
		
		String datas = paramMap.get("datas");
		String rsSetType = paramMap.get("setType");
		String rsMode = paramMap.get("mode");
		
		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
		
	    int mode = mode_convert.convert_modedata(rsMode);

	    String rsOverwriteid 	 = (String)config.setValue(rsDatas, "overwriteid", "");   // 교체할 장비 id
	    String rsGid			 = (String)config.setValue(rsDatas, "pGroupID", "0");  	  // 상위그룹id[필수]
//	    # 유효성 검사
	    
        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setId(rsGid);
	    int rsGidCnt = devicesMapper.deviceGroupCnt(devicesVo);
	    Validation.deviceAdd_gid(rsGidCnt);
	    
	    String rsId 			 = (String)config.setValue(rsDatas, "id", null);  	  	  // 장비 id (신규추가인 경우 NULL)
	    String rsName 			 = (String)config.setValue(rsDatas, "dn", "");  		  // 장비이름[필수]
//	    # 유효성 검사
	    Validation.deviceAdd_name(rsName);
	    String rsDesc			 = (String)config.setValue(rsDatas, "desc", "");  		  // 장비설명
	    String rsIp				 = (String)config.setValue(rsDatas, "ip", "");  		  // 장비ip[필수]
//	    # 유효성 검사
	    Validation.deviceAdd_ip(rsIp);
	    String rsActive			 = (String)config.setValue(rsDatas, "active", "0");  	  // 활성화 여부(0:비활성화, 1:활성화)
	    String rsLog 			 = (String)config.setValue(rsDatas, "log", "0");  		  // 원본로그저장 여부(0:비활성화, 1:활성화)
	    String rsAlarm 			 = (String)config.setValue(rsDatas, "alarm", "0");  	  // 이벤트 이메일 전송 여부(0:비활성화, 1:활성화)
	    String rsLicence		 = (String)config.setValue(rsDatas, "licence", "");  	  // 라이센스
	    String rsSerial 		 = (String)config.setValue(rsDatas, "serial", "");  	  // 시리얼
//	    ## 유효성 검사
	    Validation.deviceAdd_serial(rsSerial);
		String rsOs 			 = (String)config.setValue(rsDatas, "os", "");  		  // 장비os
		String rsHostname 		 = (String)config.setValue(rsDatas, "hostname", "");  	  // 장비 호스트이름
		String rsCompany 		 = (String)config.setValue(rsDatas, "company", "");  	  // 고객사[필수]
		String rsCustomer 		 = (String)config.setValue(rsDatas, "customer", "");  	  // 고객이름[필수]
		String rsEmail 			 = (String)config.setValue(rsDatas, "email", "");  		  // 이메일
		String rsZip 			 = (String)config.setValue(rsDatas, "zip", "");  		  // 우편번호
		String rsAddress 		 = (String)config.setValue(rsDatas, "address", "");  	  // 주소
		String rsPhone1  		 = (String)config.setValue(rsDatas, "phone1", ""); 		  // 전화1
		String rsPhone2 		 = (String)config.setValue(rsDatas, "phone2", "");  	  // 전화2
		String rsFax			 = (String)config.setValue(rsDatas, "fax", "");  		  // fax
		String rsMid 			 = (String)config.setValue(rsDatas, "mid", "");  		  // 관리계정 id
		String rsMpass 			 = (String)config.setValue(rsDatas, "mpass", "");  		  // 관리계정 pw
	    String rsProductId 		 = (String)config.setValue(rsDatas, "product_id", "2");   // 제품id
	    
	    devicesVo = new DevicesVo();
        devicesVo.setId(rsProductId);
	    int rsProductIdCnt = devicesMapper.productCnt(devicesVo);	    
//	    ## 유효성 검사
	    Validation.deviceAdd_product(rsProductIdCnt);
	    String rsSnmpVersion	 = (String)config.setValue(rsDatas, "snmp_version", "");   // snmp 버전
	    String rsSnmpCommunity   = (String)config.setValue(rsDatas, "snmp_community", ""); // snmp community
	    String rsSnmpUseInherit  = (String)config.setValue(rsDatas, "snmp_use_inherit", "0");  // snmp use_inherit
	    String rsSnmpLevel 		 = (String)config.setValue(rsDatas, "snmp_level", "");     // snmp level
	    String rsSnmpUser 		 = (String)config.setValue(rsDatas, "snmp_user", "");  	   // snmp user
	    String rsSnmpAuthprot 	 = (String)config.setValue(rsDatas, "snmp_authprot", "");  // snmp authprot
	    String rsSnmpAuthpass 	 = (String)config.setValue(rsDatas, "snmp_authpass", "");  // snmp authpass
	    String rsSnmpPrivprot 	 = (String)config.setValue(rsDatas, "snmp_privprot", "");  // snmp privprot
	    String rsSnmpPrivpass 	 = (String)config.setValue(rsDatas, "snmp_privpass", "");  // snmp privpass
	    String rsMemo1 			 = (String)config.setValue(rsDatas, "memo1", "");  		   // memo1
	    String rsMemo2			 = (String)config.setValue(rsDatas, "memo2", "");  		   // memo2
	    String rsCode1			 = (String)config.setValue(rsDatas, "code1", "00000000");  // 장비 관리번호
	    String rsCode2			 = (String)config.setValue(rsDatas, "code2", "0000");      // 장비 일련번호
    
		String message = "장비가 추가되었습니다.";
		String success = "true";
		
		 try {
            int overDId = 0;
            String overDName = "";
            int overDGroupId = -1;
            int overDActive = 0;

            if (!"".equals(rsOverwriteid)) {
                // Fetch overwrite device details
            	
                devicesVo = new DevicesVo();
                devicesVo.setOverwriteid(rsOverwriteid);
                
            	Map<String, Object> map = devicesMapper.overwriteDevice(devicesVo);
                overDId = Integer.parseInt((String) map.get("id"));
                overDName = (String) map.get("name");
                overDGroupId = Integer.parseInt((String) map.get("group_id"));
                overDActive = Integer.parseInt((String) map.get("active"));
                
                log.info("overDGroupId=="+overDGroupId);
            }

            int totalDeviceCount = devicesMapper.totalDeviceCount();
            if ((overDId == 0 && totalDeviceCount > 4096) || (overDId > 0 && overDActive == 0 && totalDeviceCount > 4096)) {
                message = "최대 등록가능 장비 대수는 4096대 입니다.";
                success = "false";
            }

            int chkGroupMaximumChecker = 0;
            
            if (mode == 0) {
            	
            	devicesVo = new DevicesVo();
            	devicesVo.setId(rsGid);
            	
                chkGroupMaximumChecker = devicesMapper.groupMaximumChkecker(devicesVo); 
            } else {
            	
            	devicesVo = new DevicesVo();
            	devicesVo.setId(rsGid);
            	devicesVo.setType(String.valueOf(mode));
            	
                chkGroupMaximumChecker = devicesMapper.groupMaximumChkecker2(devicesVo); 
            }

            if (chkGroupMaximumChecker >= 150 && (overDGroupId != Integer.parseInt(rsGid))) {
                message = "하나의 그룹에 150개 이상의 장비가 추가 될수 없습니다.";
                success = "false";
            }

            if (!rsId.equals("null")) {
            	message = "장비가 수정되었습니다.";
            }

            if (rsId.equals("null")) {
                // Check Duplicated device name
            	
                devicesVo = new DevicesVo();
                devicesVo.setName(rsName);            	
            	Map<String, Object> map = devicesMapper.overwriteDevice(devicesVo);
                if (!map.isEmpty()) {
                    // Duplicated device name found
                    message = "동일한 장비 이름이 존재합니다.";
                    success = "false";                    
                }
            }

            if(rsId != null && overDId > 0) {
            	devicesVo = new DevicesVo();
            	devicesVo.setId(rsId);
            	devicesVo.setOverDId(String.valueOf(overDId));
            	devicesVo.setName(rsName);
            	devicesVo.setDesc(rsDesc);
            	devicesVo.setIp(rsIp);
            	devicesVo.setActive(rsActive);
            	devicesVo.setLog(rsLog);
            	devicesVo.setAlarm(rsAlarm);
            	devicesVo.setSerial(rsSerial);
            	devicesVo.setCompany(rsCompany);
            	devicesVo.setCustomer(rsCustomer);
            	devicesVo.setEmail(rsEmail);
            	devicesVo.setZip(rsZip);
            	devicesVo.setAddress(rsAddress);
            	devicesVo.setPhone1(rsPhone1);
            	devicesVo.setPhone2(rsPhone2);
            	devicesVo.setFax(rsFax);
            	devicesVo.setMid(rsMid);
            	devicesVo.setMpass(rsMpass);
            	devicesVo.setGid(rsGid);
            	devicesVo.setProductId(rsProductId);
            	devicesVo.setSnmpUseInherit(rsSnmpUseInherit);
            	devicesVo.setSnmpVersion(rsSnmpVersion);
            	devicesVo.setSnmpCommunity(rsSnmpCommunity);
            	devicesVo.setSnmpLevel(rsSnmpLevel);
            	devicesVo.setSnmpUser(rsSnmpUser);
            	devicesVo.setSnmpAuthprot(rsSnmpAuthprot);
            	devicesVo.setSnmpAuthpass(rsSnmpAuthpass);
            	devicesVo.setSnmpPrivprot(rsSnmpPrivprot);
            	devicesVo.setSnmpPrivpass(rsSnmpPrivpass);
            	devicesVo.setMemo1(rsMemo1);
            	devicesVo.setMemo2(rsMemo2);
            	devicesVo.setCode1(rsCode1);
            	devicesVo.setCode2(rsCode2);
            	
            	Map<String, Object> map = devicesMapper.overwriteDeviceInfo(devicesVo);
            	if(map.get("col").equals("0")) {
            		success = "false";       
            		if(map.get("col2").equals("0")) {
            			message = "동일한 라이센스 또는 시리얼을 가진 장비가 존재합니다.";
            		}else {
            			message = "동일한 장비 이름이 존재합니다.";
            		}
            	}else {
            		
            	}
            }else {
        		devicesVo = new DevicesVo();
            	devicesVo.setId(rsId);
            	devicesVo.setName(rsName);
            	devicesVo.setDesc(rsDesc);
            	devicesVo.setIp(rsIp);
            	devicesVo.setActive(rsActive);
            	devicesVo.setLog(rsLog);
            	devicesVo.setAlarm(rsAlarm);
            	devicesVo.setSerial(rsSerial);
            	devicesVo.setCompany(rsCompany);
            	devicesVo.setCustomer(rsCustomer);
            	devicesVo.setEmail(rsEmail);
            	devicesVo.setZip(rsZip);
            	devicesVo.setAddress(rsAddress);
            	devicesVo.setPhone1(rsPhone1);
            	devicesVo.setPhone2(rsPhone2);
            	devicesVo.setFax(rsFax);
            	devicesVo.setMid(rsMid);
            	devicesVo.setMpass(rsMpass);
            	devicesVo.setGid(rsGid);
            	devicesVo.setProductId(rsProductId);
            	devicesVo.setSnmpUseInherit(rsSnmpUseInherit);
            	devicesVo.setSnmpVersion(rsSnmpVersion);
            	devicesVo.setSnmpCommunity(rsSnmpCommunity);
            	devicesVo.setSnmpLevel(rsSnmpLevel);
            	devicesVo.setSnmpUser(rsSnmpUser);
            	devicesVo.setSnmpAuthprot(rsSnmpAuthprot);
            	devicesVo.setSnmpAuthpass(rsSnmpAuthpass);
            	devicesVo.setSnmpPrivprot(rsSnmpPrivprot);
            	devicesVo.setSnmpPrivpass(rsSnmpPrivpass);
            	devicesVo.setMemo1(rsMemo1);
            	devicesVo.setMemo2(rsMemo2);
            	devicesVo.setCode1(rsCode1);
            	devicesVo.setCode2(rsCode2);
        		
        		Map<String, Object> map = devicesMapper.setDeviceInfo(devicesVo);
        		
        		log.info("map : "+map);
        		
        		String col = String.valueOf(map.get("col"));
        		String col2 = String.valueOf(map.get("col2"));
        		
        		log.info("col : "+col);
        		log.info("col2 : "+col2);
            	if(col.equals("0")) {
            		success = "false";       
            		if(col2.equals("0")) {
            			message = "동일한 라이센스 또는 시리얼을 가진 장비가 존재합니다.";
            		}else {
            			message = "동일한 장비 이름이 존재합니다.";
            		}
            	}else {
            		
            		log.info("rsSetType : "+rsSetType);
            		
            		if(rsSetType != null && rsSetType.equals("Manual")) {
            			devicesVo = new DevicesVo();
            			devicesVo.setSerial(rsSerial);
            			devicesVo.setId(col2);
            			devicesMapper.updateDeviceInfo(devicesVo);
            		}
            	}
            }
            
        } catch (Exception e) {
            message = "db connection error";
            success = "false";
        }
	
        Map<String, Object> map = new HashMap<String,Object>(); 
        map.put("message", message);
        
		return map;
	}
	
	/*
	 * 메모내용 수정
	 */
//	public int setFailMemo(Map<String,String> paramMap) throws IOException, ParseException{
//		
//		String datas = paramMap.get("datas");
//		
//		Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
//		String failID = (String)config.setValue(rsDatas, "failID", "");
//		String type = (String)config.setValue(rsDatas, "type", "3");
//		String memo = (String)config.setValue(rsDatas, "memo", "");
//		
//		log.info("failID : "+failID);
//		log.info("type : "+type);
//		log.info("memo : "+memo);
//		
//		DevicesVo devicesVo = new DevicesVo();
//		devicesVo.setFailID(failID);		
//		devicesVo.setType(type);		
//		devicesVo.setMemo(memo);		
//		
//		return devicesMapper.setFailMemo(devicesVo);
//	}

}

