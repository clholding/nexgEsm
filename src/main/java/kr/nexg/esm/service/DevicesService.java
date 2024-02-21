package kr.nexg.esm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.dto.DevicesVo;
import kr.nexg.esm.mapper.DevicesMapper;
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
    	
        List<Object[]> group_list;
        List<Object[]> dev_list;
    	
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
            devicesVo.setId("admin");
            devicesVo.setMode(modeValue);
            
            List<Object[]> temp = devicesMapper.getDeviceGroupByLogin(devicesVo);
            
            group_list.add(new Object[]{0, "전체", 0, 0});

            List<Integer> hashList = new ArrayList<>();
            for (Object[] el : temp) {
                hashList.add((Integer) el[0]);
            }

            int pid = 0;
            for (Object[] el : temp) {
                pid = (Integer) el[2];
                if ((Integer) el[2] > 0 && !hashList.contains((Integer) el[2])) {
                    pid = 0;
                }
                group_list.add(new Object[]{el[0], el[1], pid, el[3], el[4], el[5], el[6], el[7]});
            }

            dev_list = devicesMapper.getDeviceListByLogin(devicesVo);
        } else {
            group_list = devicesMapper.getDeviceGroup();
            dev_list = devicesMapper.getDeviceList();
        }
        
        List<Map<String, Object>> resultList = new ArrayList<>();
        List<Object[]> res = getChildren(0, "group", true, group_list, dev_list);

        int total = res.size();
        int cnt = 0;
        List<Map<String, Object>> list = new ArrayList<>();
        while (cnt < total) {
            Map<String, Object> gt = new HashMap<>();
            gt.put("id", res.get(cnt)[0]);
            gt.put("text", res.get(cnt)[1]);
            gt.put("children", new ArrayList<>());

            List<Map<String, Object>> child = (List<Map<String, Object>>) loadChildGroup((Integer) res.get(cnt)[0], 1, new ArrayList<>(), gt, schType, group_list, dev_list);
            list.add(gt);
            cnt++;
        }

        
        return list;
	};
	
	
    public static List<Object[]> getChildren(int pgid, String type, boolean isRoot, List<Object[]> group_list, List<Object[]> dev_list) {
        List<Object[]> result = new ArrayList<>();

        if ("group".equals(type)) {
            for (Object[] g : group_list) {
                if (isRoot && g[0].equals(pgid)) {
                    result.add(g);
                    break;
                } else if (g[2].equals(pgid) && !g[0].equals(pgid)) {
                    result.add(g);
                }
            }
        } else {
            for (Object[] d : dev_list) {
                if (d[2].equals(pgid)) {
                    result.add(d);
                }
            }
        }

        return result;
    }
    
    public static Map<String, Object> loadChildGroup(int pgid, int depth, List<Map<String, Object>> arr, Map<String, Object> ro, String schType, List<Object[]> group_list, List<Object[]> dev_list) {
        List<Object[]> res = getChildren(pgid, "group", false, group_list, dev_list);
        int total = res.size();
        int cnt = 0;

        if (total > 0) {
            while (cnt < total) {
                Map<String, Object> gt = new HashMap<>();
                gt.put("id", res.get(cnt)[0]);
                gt.put("text", res.get(cnt)[1]);
                gt.put("children", new ArrayList<>());
                gt.put("state", res.get(cnt)[3]);

                if (res.get(cnt).length > 4) {
                    gt.put("total", res.get(cnt)[4]);
                    gt.put("fail", res.get(cnt)[5]);
                    gt.put("code1", String.valueOf((int) res.get(cnt)[6]));
                    gt.put("code2", String.valueOf((int) res.get(cnt)[7]));
                }

                arr.add(gt);
                depth += 1;
                Map<String, Object> child = loadChildGroup((int) res.get(cnt)[0], depth, arr, gt, schType, group_list, dev_list);
                arr.remove(arr.size() - 1);
                depth -= 1;
                ((List<Map<String, Object>>) ro.get("children")).add(child);

                if (!"group".equals(schType)) {
                    if (cnt == (total - 1)) {
                        List<Map<String, Object>> devArr = loadChildDevice(pgid, schType, group_list, dev_list);
                        ((List<Map<String, Object>>) ro.get("children")).addAll(devArr);
                    }
                }

                cnt += 1;
            }
        } else {
            if (!"group".equals(schType)) {
                List<Map<String, Object>> devArr = loadChildDevice(pgid, schType, group_list, dev_list);
                ro.put("children", devArr);
            }
        }

        return ro;
    }    
    
    public static List<Map<String, Object>> loadChildDevice(int pgid, String schType, List<Object[]> group_list, List<Object[]> dev_list) {
        List<Object[]> res = getChildren(pgid, "dev", false, group_list, dev_list);
        int total = res.size();
        List<Map<String, Object>> devArr = new ArrayList<>();
        boolean chkSame;

        if (total > 0) {
            int cnt = 0;
            while (cnt < total) {
                Map<String, Object> devObj = new HashMap<>();
                devObj.put("id", res.get(cnt)[0]);
                devObj.put("leaf", true);
                devObj.put("text", res.get(cnt)[1]);
                devObj.put("ip", res.get(cnt)[4]);
                devObj.put("state", res.get(cnt)[10]);
                devObj.put("serial", res.get(cnt)[7]);
                devObj.put("code1", String.valueOf((int) res.get(cnt)[13]));
                devObj.put("code2", String.valueOf((int) res.get(cnt)[14]));
                devObj.put("active", res.get(cnt)[8]);
                devObj.put("intfs", res.get(cnt)[15]);
                devObj.put("eixs", res.get(cnt)[16]);
                devObj.put("vrrps", res.get(cnt)[17]);
                devObj.put("tracks", res.get(cnt)[18]);
                devObj.put("intlist", res.get(cnt)[19]);

                if ("detail".equals(schType)) {
                    devObj.put("model", res.get(cnt)[5]);
                    devObj.put("version", res.get(cnt)[6]);
                    devObj.put("registerDate", String.valueOf(res.get(cnt)[9]));
                    devObj.put("log", res.get(cnt)[11]);
                    devObj.put("alarm", res.get(cnt)[12]);
                }

                chkSame = false;
                for (Map<String, Object> el : devArr) {
                    if (el.get("id").equals(res.get(cnt)[0])) {
                        chkSame = true;
                        break;
                    }
                }

                if (!chkSame) {
                    devArr.add(devObj);
                }

                cnt += 1;
            }
        }

        return devArr;
    }
    
	public List<Map<String, String>> getProductList(Map<String,String> paramMap) throws IOException, ParseException{
		
    	String datas = paramMap.get("datas");
    	
    	Map<String, Object> rsDatas = new ObjectMapper().readValue(datas, Map.class);
    	String type = (String)config.setValue(rsDatas, "type", "null");
    	
        DevicesVo devicesVo = new DevicesVo();
        devicesVo.setType(type);
        
        // 결과 출력
        log.info("type : "+type);
    	
		return devicesMapper.getProductList(devicesVo);
	}

}
