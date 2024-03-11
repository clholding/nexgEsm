package kr.nexg.esm.devices.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.administrator.dto.AdministratorEnum;
import kr.nexg.esm.devices.dto.DevicesVo;
import kr.nexg.esm.devices.mapper.DevicesMapper;
import kr.nexg.esm.nexgesm.command.Device;
import kr.nexg.esm.nexgesm.mariadb.Config;
import kr.nexg.esm.util.Validation;
import kr.nexg.esm.util.config;
import kr.nexg.esm.util.mode_convert;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DevicesService {
	
	@Autowired
	DevicesMapper devicesMapper;
	
	@Autowired
	Config.Config1 config1;
	
	@Autowired
	Device device;
	
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
    

//    public String emsgToStr(Map<String, String> emsg) throws JSONException {
//        List<String> tmpPrintArray = new ArrayList<>();
//        String rtnStr = "";
//
//        Map<String, String> keyDic = new HashMap<>();
//        keyDic.put("pGroupID", "부모그룹");
//        keyDic.put("deviceIDs", "장비ID");
//        keyDic.put("groupIDs", "그룹ID");
//        keyDic.put("desc", "설명");
//        keyDic.put("id", "ID");
//        keyDic.put("name", "이름");
//        keyDic.put("groupNames", "그룹");
//        keyDic.put("deviceNames", "장비");
//        keyDic.put("gn", "그룹");
//        keyDic.put("product_id", "제품명");
//        keyDic.put("dn", "장비명");
//        keyDic.put("active", "활성화");
//        keyDic.put("ip", "주소");
//        keyDic.put("licence", "라이선스");
//        keyDic.put("log", "원본 로그 저장");
//        keyDic.put("company", "고객사");
//        keyDic.put("phone2", "전화");
//        keyDic.put("fax", "FAX");
//        keyDic.put("zip", "우편번호");
//        keyDic.put("address", "주소");
//        keyDic.put("customer", "담당자");
//        keyDic.put("phone1", "핸드폰");
//        keyDic.put("email", "E-mail");
//        keyDic.put("alarm", "이벤트 이메일 전송");
//
//        for (String key : emsg.keySet()) {
//        	
//            String tmpPrint = "";
//            String replaceKey = keyDic.containsKey(key) ? keyDic.get(key) : key;
//
//            if ("pGroupID".equals(key)) {
//            	
//                DevicesVo devicesVo = new DevicesVo();
//                devicesVo.setPGroupID(emsg.get(key));
//                
//                List<Map<String, Object>> deviceGroupNames = devicesMapper.deviceGroupNames(devicesVo);
//                tmpPrint = String.valueOf(deviceGroupNames.get(0).get("name"));
//                
//            } else if ("deviceIDs".equals(key)) {
//                if ( emsg.get(key).length() > 0) {
//                    String tmpWhere = String.join(",", emsg.get(key));
//                    
//                    DevicesVo devicesVo = new DevicesVo();
//                    devicesVo.setRsDeviceIDs(tmpWhere);
//                    List<Map<String, Object>> deviceNames = devicesMapper.deviceNames(devicesVo);
//                    
//                    List<String> tmpArray = new ArrayList<>();
//                    for (Map<String, Object> tmp : deviceNames) {
//                        tmpArray.add(String.valueOf(tmp.get("name")));
//                    }
//                    tmpPrint = String.join(",", tmpArray);
//                }
//            } else if ("groupIDs".equals(key)) {
//            	if ( emsg.get(key).length() > 0) {
//                    String tmpWhere = String.join(",", emsg.get(key));
//                    
//                    DevicesVo devicesVo = new DevicesVo();
//                    devicesVo.setGroupIDs(tmpWhere);
//                    
//                    List<Map<String, Object>> deviceGroupNames = devicesMapper.deviceGroupNames(devicesVo);
//                    
//                    List<String> tmpArray = new ArrayList<>();
//                    for (Map<String, Object> tmp : deviceGroupNames) {
//                        tmpArray.add(String.valueOf(tmp.get("name")));
//                    }
//                    tmpPrint = String.join(",", tmpArray);
//                }
//            } else if ("alarm".equals(key) || "active".equals(key) || "log".equals(key)) {
//                if (Integer.parseInt(emsg.get(key)) == 1) {
//                	tmpPrintArray.add(key + '=' + "사용");
//                } else {
//                	tmpPrintArray.add(key + '=' + "미사용");
//                }
//            } else if ("product_id".equals(key)) {
//                String tmpDeviceNum = emsg.get(key);
//                
//                DevicesVo devicesVo = new DevicesVo();
//                devicesVo.setId(tmpDeviceNum);
//                devicesMapper.productName(devicesVo);
//                
//                String tmpSearch = devicesMapper.productName(devicesVo);
//                if ("".equals(tmpSearch)) {
//                    tmpPrint = "Unknown Device";
//                } else {
//                    tmpPrint = tmpSearch;
//                }
//            }
//
//            if (tmpPrint.isEmpty()) {
//            	if (emsg.get(key) instanceof List) {
//                    List<String> keyList = (List<String>) emsg.get(key);
//                    if (!keyList.isEmpty()) {
//                        tmpPrintArray.add(replaceKey + '=' + String.join(",", keyList));
//                    } else if (!key.equals("deviceNames") && !key.equals("groupNames") && !key.equals("deviceIDs") && !key.equals("groupIDs")) {
//                        tmpPrintArray.add(replaceKey + '=' + "");
//                    }
//                } else {
//                    if (!emsg.get(key).isEmpty() && !emsg.get(key).equals("null")) {
//                        tmpPrintArray.add(replaceKey + '=' + emsg.get(key));
//                    } else if (!key.equals("deviceNames") && !key.equals("groupNames") && !key.equals("deviceIDs") && !key.equals("groupIDs")) {
//                        tmpPrintArray.add(replaceKey + '=' + "");
//                    }
//                }
//            } else {
//                tmpPrintArray.add(replaceKey + '=' + tmpPrint);
//            }
//
//            rtnStr = String.join(", ", tmpPrintArray);
//        }
//        return rtnStr;
//    }
    
    public String emsgToStr(JSONObject datas) throws JSONException {
        List<String> tmpPrintArray = new ArrayList<>();
        String rtnStr = "";

        Map<String, String> keyDic = new HashMap<>();
        keyDic.put("pGroupID", "부모그룹");
        keyDic.put("deviceIDs", "장비ID");
        keyDic.put("groupIDs", "그룹ID");
        keyDic.put("desc", "설명");
        keyDic.put("id", "ID");
        keyDic.put("name", "이름");
        keyDic.put("groupNames", "그룹");
        keyDic.put("deviceNames", "장비");
        keyDic.put("gn", "그룹");
        keyDic.put("product_id", "제품명");
        keyDic.put("dn", "장비명");
        keyDic.put("active", "활성화");
        keyDic.put("ip", "주소");
        keyDic.put("licence", "라이선스");
        keyDic.put("log", "원본 로그 저장");
        keyDic.put("company", "고객사");
        keyDic.put("phone2", "전화");
        keyDic.put("fax", "FAX");
        keyDic.put("zip", "우편번호");
        keyDic.put("address", "주소");
        keyDic.put("customer", "담당자");
        keyDic.put("phone1", "핸드폰");
        keyDic.put("email", "E-mail");
        keyDic.put("alarm", "이벤트 이메일 전송");

        Iterator<String> keysIterator = datas.keys();
        while (keysIterator.hasNext()) {
        	
        	String key = keysIterator.next();
        	log.info("key = " + key);
        	
        	String replaceKey = keyDic.containsKey(key) ? keyDic.get(key) : key;
            log.info("replaceKey = "+ replaceKey);
            String tmpPrint = "";
            String tmpSearch;

            if ("pGroupID".equals(key)) {
            	
                DevicesVo devicesVo = new DevicesVo();
                devicesVo.setPGroupID(datas.getString("pGroupID"));
                
                List<Map<String, Object>> deviceGroupNames = devicesMapper.deviceGroupNames(devicesVo);
                tmpPrint = String.valueOf(deviceGroupNames.get(0).get("name"));
                
            } else if ("deviceIDs".equals(key)) {
                if ( datas.getString(key).length() > 0) {
                    String tmpWhere = String.join(",", datas.getString(key));
                    
                    DevicesVo devicesVo = new DevicesVo();
                    devicesVo.setRsDeviceIDs(tmpWhere);
                    List<Map<String, Object>> deviceNames = devicesMapper.deviceNames(devicesVo);
                    
                    List<String> tmpArray = new ArrayList<>();
                    for (Map<String, Object> tmp : deviceNames) {
                        tmpArray.add(String.valueOf(tmp.get("name")));
                    }
                    tmpPrint = String.join(",", tmpArray);
                }
            } else if ("groupIDs".equals(key)) {
            	if ( datas.getString(key).length() > 0) {
                    String tmpWhere = String.join(",", datas.getString(key));
                    
                    DevicesVo devicesVo = new DevicesVo();
                    devicesVo.setGroupIDs(tmpWhere);
                    
                    List<Map<String, Object>> deviceGroupNames = devicesMapper.deviceGroupNames(devicesVo);
                    
                    List<String> tmpArray = new ArrayList<>();
                    for (Map<String, Object> tmp : deviceGroupNames) {
                        tmpArray.add(String.valueOf(tmp.get("name")));
                    }
                    tmpPrint = String.join(",", tmpArray);
                }
            } else if ("alarm".equals(key) || "active".equals(key) || "log".equals(key)) {
                if (Integer.parseInt(datas.getString(key)) == 1) {
                	tmpPrintArray.add(key + '=' + "사용");
                } else {
                	tmpPrintArray.add(key + '=' + "미사용");
                }
            } else if ("product_id".equals(key)) {
                String tmpDeviceNum = datas.getString("product_id");
                
                DevicesVo devicesVo = new DevicesVo();
                devicesVo.setId(tmpDeviceNum);
                devicesMapper.productName(devicesVo);
                
                tmpSearch = devicesMapper.productName(devicesVo);
                if ("".equals(tmpSearch)) {
                    tmpPrint = "Unknown Device";
                } else {
                    tmpPrint = tmpSearch;
                }
            }

            if (tmpPrint.isEmpty()) {
                if (datas.get(key) instanceof List) {
                    List<String> keyList = (List<String>) datas.get(key);
                    if (!keyList.isEmpty()) {
                        tmpPrintArray.add(replaceKey + '=' + String.join(",", keyList));
                    } else if (!key.equals("deviceNames") && !key.equals("groupNames") && !key.equals("deviceIDs") && !key.equals("groupIDs")) {
                        tmpPrintArray.add(replaceKey + '=' + "");
                    }
                } else {
                    if (!datas.getString(key).isEmpty() && !datas.getString(key).equals("null")) {
                        tmpPrintArray.add(replaceKey + '=' + datas.getString(key));
                    } else if (!key.equals("deviceNames") && !key.equals("groupNames") && !key.equals("deviceIDs") && !key.equals("groupIDs")) {
                        tmpPrintArray.add(replaceKey + '=' + "");
                    }
                }
            } else {
                tmpPrintArray.add(replaceKey + '=' + tmpPrint);
            }

            rtnStr = String.join(", ", tmpPrintArray);
        }
        return rtnStr;
    }
    
    
    public String setAuditInfo(String fn, String yn, DevicesVo devicesVo) throws JSONException, JsonProcessingException, ParseException {
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

        String eMsg = auditMenu + auditType.get(fn); // Assuming auditType is a Map<String, String>
        String dInfo = "";

        if ("setDeviceInfo".equals(fn)) {
          dInfo = "추가";
          if (devicesVo.getId() !=null && !"".equals(devicesVo.getId())) {
            dInfo = "수정";
          }
        } else if ("setDeviceGroupInfo".equals(fn)) {
          dInfo = "추가";
          if (!"null".equals(devicesVo.getId())) {
            dInfo = "수정";
          }
        }

        if (devicesVo.getDn() != null && !"".equals(devicesVo.getDn())) {
        	
       	  ObjectMapper mapper = new ObjectMapper(); 
          String jsonString = mapper.writeValueAsString(devicesVo);
          
          JSONParser jsonParser = new JSONParser();
          JSONObject jsonObj = (JSONObject)jsonParser.parse(jsonString);
          
          log.info("==============="+jsonObj);
//          eMsg = eMsg + dInfo + " " + state + " " + emsgToStr(jsonObj); // Assuming emsgToStr is a method
        } else {
          if (!"setDeviceGroup".equals(fn)) {
       	    ObjectMapper mapper = new ObjectMapper(); 
            String jsonString = mapper.writeValueAsString(devicesVo);
          
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject)jsonParser.parse(jsonString);
            log.info("==============="+jsonObj);
            
//            eMsg = eMsg + dInfo + " " + state + " " + emsgToStr(jsonObj); // Assuming emsgToStr is a method
          } else if ("setDeviceGroup".equals(fn)) {
            String tmpPrint = "";
            List<String> tmpArray = new ArrayList<>();
            
            devicesVo.setPGroupID(String.valueOf(devicesVo.getPGroupID()));
            
            List<Map<String, Object>> tmpParentSearch = devicesMapper.deviceGroupNames(devicesVo);
            String tmpParent = String.valueOf(tmpParentSearch.get(0).get("name"));
            eMsg = eMsg + dInfo + " " + state + " ";
            
            if (devicesVo.getGroupIDs() != null) {
              String tmpWhere = String.join(",", devicesVo.getGroupIDs());
              
              devicesVo = new DevicesVo();
              devicesVo.setGroupIDs(tmpWhere);
              
              tmpParentSearch = devicesMapper.deviceGroupNames(devicesVo);
              
              for (Map<String, Object> tmp : tmpParentSearch) {
                tmpArray.add(String.valueOf(tmp.get("name")));
              }
              eMsg = eMsg + "그룹 " + String.join(",", tmpArray) + " 를 그룹 " + tmpParent + " 로 이동하였습니다. ";
            }
            
            if (devicesVo.getDeviceIDs() != null) {
              tmpArray.clear();
              String tmpWhere = String.join(",", devicesVo.getDeviceIDs());
              
              devicesVo = new DevicesVo();
              devicesVo.setRsDeviceIDs(tmpWhere);
              
              tmpParentSearch = devicesMapper.deviceNames(devicesVo);
              
              for (Map<String, Object> tmp : tmpParentSearch) {
            	 tmpArray.add(String.valueOf(tmp.get("name")));
              }
              eMsg = eMsg + "장비 " + String.join(",", tmpArray) + " 를 그룹 " + tmpParent + " 로 이동하였습니다.";
            }
          }
        }
        
        return eMsg;

//        EsmAuditLog e = new EsmAuditLog();
//        e.esmlog(auditlevel, session.id, request.client, eMsg);
      }    
    
    /*
     * DeviceFinder
     */
	public List<Map<String, Object>> deviceAll(DevicesVo devicesVo) throws IOException, ParseException{
		
    	List<Map<String, Object>> group_list = null;
    	List<Map<String, Object>> dev_list = null;
    	
        if ("1".equals(devicesVo.getAuth())) {
            int modeValue = 0;
            if (!"None".equals(devicesVo.getMode())) {
                if ("ALL".equals(devicesVo.getMode())) {
                    modeValue = 0;
                }
            } else {
                modeValue = mode_convert.convert_modedata(devicesVo.getMode());
            }

            group_list = new ArrayList<>();
            
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            
            devicesVo.setSessionId(authentication.getName());
            devicesVo.setMode(String.valueOf(modeValue));
            
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
        groupMap.put("id", group_list.get(0).get("id"));

        List<Map<String, Object>> children = loadChildGroup(group_list, dev_list);
        groupMap.put("children", children);

        result.add(groupMap);
        
        return result;
	};
	
    /*
     * DeviceFinder > 개별정보 > 장비미리보기
     */  
	public List<Map<String, Object>> getDeviceStatus(DevicesVo devicesVo) throws IOException, ParseException{
		
//		log.info("devicesVo : "+devicesVo.getDeviceIDs());
		String ids = String.join(",", devicesVo.getDeviceIDs());
//		log.info("ids : "+ids);
		devicesVo.setDeviceID(ids);
		
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
	public List<Map<String, Object>> deviceCandidate(DevicesVo devicesVo) throws IOException, ParseException{
		
		
		int mode = mode_convert.convert_modedata(String.valueOf(devicesVo.getMode()));
		
		return devicesMapper.deviceCandidate(mode);
	}
	
    /*
     * 메인 화면 > 탑메뉴 > 시스템 설정 > 알람 > 장비/그릅 임계치 설정
     */	
	public List<Map<String, Object>> getAlarmDeviceGroupListNDeviceListAll(DevicesVo devicesVo) throws IOException, ParseException{
		
		List<Map<String, Object>> group_list = null;
		List<Map<String, Object>> dev_list = null;
		
		if ("1".equals(devicesVo.getAuth())) {
			
			group_list = new ArrayList<>();
			
			SecurityContext context = SecurityContextHolder.getContext();
			Authentication authentication = context.getAuthentication();
//            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			
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
	public Map<String, Object> getDeviceGroupInfo(DevicesVo devicesVo) throws IOException, ParseException{
		
		devicesVo.setSessionId(devicesVo.getId());		
		
		return devicesMapper.getDeviceGroupInfo(devicesVo);
	}
	
    /*
     * DeviceFinder > 개별정보 > 정보 > 기본정보
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 
     */
	public Map<String, Object> getDeviceInfo(DevicesVo devicesVo) throws IOException, ParseException{
		
        devicesVo.setSessionId(devicesVo.getDeviceID());		
        
		return devicesMapper.getDeviceInfo(devicesVo);
	}
	
    /*
     * 장비 리스트 정보 조회
     */
	public List<Map<String, Object>> getDeviceInfoList(DevicesVo devicesVo) throws IOException, ParseException{
		
		String ids = String.join(",", devicesVo.getDeviceIDs());
		devicesVo.setDeviceID(ids);	
		
		List<Map<String, Object>> result = new ArrayList<>();
		List<Map<String, Object>> list = devicesMapper.getDeviceInfoList(devicesVo);
		for (Map<String, Object> vo : list) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("id", vo.get("id"));
			map.put("dn", vo.get("dn"));
			map.put("desc", vo.get("desc"));
			map.put("ip", vo.get("id"));
			map.put("product_id", vo.get("product_id"));
			map.put("pGroupID", vo.get("pGroupID"));
			map.put("gn", vo.get("gn"));
			map.put("serial", vo.get("serial"));
			map.put("os", vo.get("os"));
			map.put("agent", vo.get("agent"));
			map.put("hostname", vo.get("hostname"));
			map.put("company", vo.get("company"));
			map.put("customer", vo.get("customer"));
			map.put("email", vo.get("email"));
			map.put("zip", vo.get("zip"));
			map.put("address", vo.get("address"));
			map.put("phone1", vo.get("phone1"));
			map.put("phone2", vo.get("phone2"));
			map.put("fax", vo.get("fax"));
			map.put("mid", vo.get("mid"));
			
			result.add(map);
		}

		return result;
	}
	
    /*
     * DeviceFinder > 개별정보 > 정보 > 인터페이스
     */
	public List<Map<String, Object>> getDeviceInterface(DevicesVo devicesVo) throws IOException, ParseException{
		
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
	public List<Map<String, Object>> getProductList(DevicesVo devicesVo) throws IOException, ParseException{
		
		return devicesMapper.getProductList(devicesVo);
	}
	
	/*
	 * 제품실패 정보
	 */
	public List<Map<String, Object>> getDeviceFailInfo(DevicesVo devicesVo) throws IOException, ParseException{
		
		return devicesMapper.getDeviceFailInfo(devicesVo);
	}
	
	/*
	 * DeviceFinder > 개별정보 > 정보 > 장애내역
	 */  
	public List<Map<String, Object>> getDeviceFailList(DevicesVo devicesVo) throws IOException, ParseException{
		
		int rsStart = (Integer.parseInt(devicesVo.getPage()) - 1) * Integer.parseInt(devicesVo.getLimit());
		devicesVo.setPage(String.valueOf(rsStart));
		
		return devicesMapper.getDeviceFailList(devicesVo);
	}
	
	/*
	 * 메인화면 > SideBar > 자산이력
	 */
	public List<Map<String, Object>> searchDeviceInfoList(DevicesVo devicesVo) throws IOException, ParseException{
		
		int mode = 0;
	    int rsPage 			= Integer.valueOf(devicesVo.getPage());
		int rsViewCount 	= Integer.valueOf(devicesVo.getViewCount());
	    rsPage 				= ((rsPage) - 1) * ((rsViewCount));
	    	    
	   if("".equals(devicesVo.getGroupID())) {
		   
			String ids = String.join(",", devicesVo.getDeviceIDs());
//			log.info("ids : "+ids);
			devicesVo.setDeviceID(ids);		   
		   
	   }else {		
	        SecurityContext context = SecurityContextHolder.getContext();
	        Authentication authentication = context.getAuthentication();
//	        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
	        
			mode = mode_convert.convert_modedata(devicesVo.getMode());
			
			devicesVo.setSessionId(authentication.getName());
			devicesVo.setMode(String.valueOf(mode));
			String rsDeviceIDs = devicesMapper.getGroupToDeviceListByLogin(devicesVo);
			
			devicesVo.setDeviceID(rsDeviceIDs);
		}
		
		devicesVo.setPage(String.valueOf(rsPage));
		
		return devicesMapper.searchDeviceInfoList(devicesVo);
	}
	
	/*
	 * DeviceFinder > 개별정보 > 정보 > 기본정보 > 관리번호 중복체크
	 */
	public Map<String, Object> checkManagedCode(DevicesVo devicesVo) throws IOException, ParseException{
		
		if(devicesVo.getCode2() == null) {
			devicesVo.setCode2("");
		}
	    int cnt = devicesMapper.checkManagedCode(devicesVo);
	    log.info("cnt : "+cnt);
	    
	    String message = "";
	    String success = "";
	    
	    if(cnt == 0) {
	    	success = "false";
	    	if("0".equals(devicesVo.getType())) {
	    		message = "동일한 관리번호 및 일련번호가 존재합니다.";
	    	}else {
	    		message = "동일한 관리번호 및 등급이 존재합니다.";
	    	}
	    }else {
	    	success = "true";
	    	if("0".equals(devicesVo.getType())) {
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
	public Map<String, Object> setDeviceGroupInfo(DevicesVo devicesVo) throws Exception{
		
//	    # 유효성 검사
   	    Validation.groupAdd_name(devicesVo.getName());
	    
		String message = "장비/그룹이 수정되었습니다.";
	    
	    if(devicesVo.getId() == null || "".equals(devicesVo.getId())) {
	    	message = "장비/그룹이 추가되었습니다.";
	    }
	    
	    int gp = 0;
        if (devicesVo.getPGroupID() != null && !"".equals(devicesVo.getId())) {
        	gp = Integer.parseInt(devicesVo.getPGroupID());
        }
        
        String topGroup = "전체";
        int cnt = devicesMapper.deviceGroupCnt(devicesVo);
        if ((cnt > 0 || devicesVo.getName().equals(topGroup)) && devicesVo.getId() == null) {
        	message = "동일한 장비 그룹 이름이 존재합니다.";
        }else {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            
            devicesVo.setSessionId(authentication.getName());
            
        	Map<String, Object> map = devicesMapper.setDeviceGroupInfo(devicesVo);
        	if("0".equals(map.get("select_id"))) {
        		message = "동일한 장비 그룹 이름이 존재합니다.";
        	}
        }
        
        String eMsg = setAuditInfo("setDeviceGroupInfo", "success", devicesVo);
        log.info("eMsg : "+eMsg);
        
        Map<String, Object> map = new HashMap<String,Object>(); 
        map.put("message", message);
        map.put("eMsg", eMsg);
        
		return map;
	}
	
    /*
     * DeviceFinder -> 개별정보 -> 정보 -> 기본정보 -> 저장
     */
	public Map<String, Object> setDeviceInfo(DevicesVo devicesVo) throws Exception{
		
	    int mode = mode_convert.convert_modedata(devicesVo.getMode());

//	    # 유효성 검사
	    int rsGidCnt = devicesMapper.deviceGroupCnt2(devicesVo);
	    Validation.deviceAdd_gid(rsGidCnt);
	    
//	    # 유효성 검사
	    Validation.deviceAdd_name(devicesVo.getDn());
//	    # 유효성 검사
	    Validation.deviceAdd_ip(devicesVo.getIp());
//	    ## 유효성 검사
	    Validation.deviceAdd_serial(devicesVo.getSerial());
	    
	    int rsProductIdCnt = devicesMapper.productCnt(devicesVo);	    
//	    ## 유효성 검사
	    Validation.deviceAdd_product(rsProductIdCnt);
    
		String message = "장비가 추가되었습니다.";
		String success = "true";
		
		 try {
            int overDId = 0;
            String overDName = "";
            int overDGroupId = -1;
            int overDActive = 0;

            if (devicesVo.getOverwriteid() != null && !"".equals(devicesVo.getOverwriteid())) {
                // Fetch overwrite device details
            	
                devicesVo = new DevicesVo();
                
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
            	
                chkGroupMaximumChecker = devicesMapper.groupMaximumChkecker(devicesVo); 
            } else {
            	
            	devicesVo.setType(String.valueOf(mode));
                chkGroupMaximumChecker = devicesMapper.groupMaximumChkecker2(devicesVo); 
            }

            if (chkGroupMaximumChecker >= 150 && (overDGroupId != Integer.parseInt(devicesVo.getPGroupID()))) {
                message = "하나의 그룹에 150개 이상의 장비가 추가 될수 없습니다.";
                success = "false";
            }

            if (devicesVo.getId() != null && !"".equals(devicesVo.getId())) {
            	message = "장비가 수정되었습니다.";
            }

            if (devicesVo.getId() == null && "".equals(devicesVo.getId())) {
                // Check Duplicated device name
            	
                devicesVo = new DevicesVo();
            	Map<String, Object> map = devicesMapper.overwriteDevice(devicesVo);
                if (!map.isEmpty()) {
                    // Duplicated device name found
                    message = "동일한 장비 이름이 존재합니다.";
                    success = "false";                    
                }
            }

            if(devicesVo.getId() != null && overDId > 0) {
            	
            	Map<String, Object> map = devicesMapper.overwriteDeviceInfo(devicesVo);
            	if(map.get("col").equals("0")) {
            		success = "false";       
            		if(map.get("col2").equals("0")) {
            			message = "동일한 라이센스 또는 시리얼을 가진 장비가 존재합니다.";
            		}else {
            			message = "동일한 장비 이름이 존재합니다.";
            		}
            	}else {
            		config1.set_apply_status(true);
            	}
            }else {
        		
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
            		
            		device.add_device(col2);
            		config1.set_apply_status(true);
            		if(devicesVo.getType() != null && devicesVo.getType().equals("Manual")) {
            			devicesVo = new DevicesVo();
            			devicesVo.setId(col2);
            			devicesMapper.updateDeviceInfo(devicesVo);
            		}
            	}
            }
            
        } catch (Exception e) {
            message = "db connection error";
            success = "false";
        }
	
        String eMsg = setAuditInfo("setDeviceInfo", "success", devicesVo);
        log.info("eMsg : "+eMsg);
        
        Map<String, Object> map = new HashMap<String,Object>(); 
        map.put("message", message);
        map.put("eMsg", eMsg);
        
        
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

