package kr.nexg.esm.configuration.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import kr.nexg.esm.configuration.dto.ConfigurationVo;
import kr.nexg.esm.configuration.mapper.ConfigurationMapper;
import kr.nexg.esm.nexgesm.mariadb.Config;
import kr.nexg.esm.util.config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConfigurationService {
	
	@Autowired
	ConfigurationMapper configurationMapper;
	
	@Autowired
	Config.ConfigBackup configBackup;
	
	@Autowired
	Config.SystemConfigBackup systemConfigBackup;
	
    @Value("${pkg}")
    private String pkg;
    
    @Value("${server.version}")
    private String serverVersion;
    
    @Value("${name}")
    private String name;
    
    @Value("${hash}")
    private String hash;
    
    @Value("${agent.version}")
    private String agentVersion;
	
    /*
     * TopNav > 시스템 설정 > 시스템 > 제품정보
     */
	public Map<String, Object> getSystemInfo() throws IOException, ParseException{
		
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("packageversion", pkg);
        map.put("serverversion", serverVersion);
        map.put("name", name);
        map.put("buildno", hash);
        map.put("agentversion", agentVersion);
        
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 시간 동기화
	 */
	public Map<String, Object> getNtpInfo() throws IOException, ParseException{
		
		Map<String, Object> getNtpInfo = configurationMapper.getNtpInfo();
		
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String ServerTime = now.format(formatter);
        String Time = String.valueOf(ServerTime.substring(0, 10));
        String shour = String.valueOf(now.getHour());
        String smin = String.valueOf(now.getMinute());
        String ssec = String.valueOf(now.getSecond());
        
		Map<String, Object> result = new LinkedHashMap<>();
		result.put("ServerTime", Time);
		result.put("shour", shour);
		result.put("smin", smin);
		result.put("ssec", ssec);
		result.put("useNtp", getNtpInfo.get("ntp_use"));
		result.put("NtpIp", getNtpInfo.get("ntp_server"));
		result.put("Type", getNtpInfo.get("ntp_sync_type"));
		result.put("Period", getNtpInfo.get("ntp_sync_period"));
		result.put("Time", getNtpInfo.get("ntp_sync_time"));
		result.put("syncMonth", getNtpInfo.get("ntp_sync_period_month"));
		result.put("syncDay", getNtpInfo.get("ntp_sync_period_day"));
		result.put("syncWeek", getNtpInfo.get("ntp_sync_period_week"));
		result.put("syncTime", getNtpInfo.get("ntp_sync_period_time"));
		result.put("udate", getNtpInfo.get("ntp_last_updatetime"));
		
		return result;
	}
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 서버 무결성 상태
	 */
	public Map<String, Object> getIntegrityInfo(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		int skip = (Integer.parseInt(configurationVo.getPage()) - 1) * Integer.parseInt(configurationVo.getLimit());
		
		configurationVo.setSkip(String.valueOf(skip));
		
		List<Map<String, Object>> result = new ArrayList<>();
		List<Map<String, Object>> list = configurationMapper.getIntegrityInfoList(configurationVo);
		
		for (Map<String, Object> vo : list) {
			
			Map<String, Object> map = new LinkedHashMap<>();
			map.put("status", vo.get("status"));
			map.put("daemon", vo.get("item"));
			map.put("mdate", String.valueOf(vo.get("mdate")).substring(0, 19));
			map.put("cdate", String.valueOf(vo.get("cdate")).substring(0, 19));
			map.put("id", vo.get("id"));
			map.put("size", vo.get("size"));
			
			result.add(map);
		}
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result", result);
		map.put("total", list.get(0).get("total") != null ? list.get(0).get("total"):"0");
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 에이전트 무결성 상태
	 */
	public List<Map<String, Object>> getDeviceIntegrityInfo() throws IOException, ParseException{
		
		return configurationMapper.getDeviceIntegrityInfo();
	}	
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 장비 설정파일 백업
	 */
	public Map<String, Object> getConfigBackupInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getConfigBackupInfo();
		
		String transfer_type = String.valueOf(result.get("transfer_type"));
		String externalBackupUse = "";
		
		if(transfer_type.equals("0")) {
			externalBackupUse = "0";
		}else {
			externalBackupUse = "1";
		}
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("useBackup", result.get("active"));
		map.put("backupPeriod", result.get("period"));
		map.put("backupTime", result.get("hour"));
		map.put("backupDay", result.get("day"));
		map.put("backupMonth", result.get("month"));
		map.put("backupWeek", result.get("week"));
		map.put("backupDevice", result.get("device_ids"));
		map.put("backupDeviceName", result.get("device_names"));
		map.put("externalBackupUse", externalBackupUse);
		map.put("transferType", result.get("transfer_type"));
		map.put("backupTargetIP",result.get("target_ip"));
		map.put("backupTargetPort", result.get("target_port"));
		map.put("backupTargetID", result.get("target_id"));
		map.put("backupTargetPassword", result.get("target_pass"));
		map.put("backupTargetDir", result.get("target_dir"));
		
		return map;
	}	
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 장비 설정파일 백업 내역
	 */
	public Map<String, Object> getConfigBackupList(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		int skip = (Integer.parseInt(configurationVo.getPage()) - 1) * Integer.parseInt(configurationVo.getLimit());
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result", configBackup.get_configbackup_list(skip, Integer.parseInt(configurationVo.getLimit())));
		map.put("total", configBackup.get_configbackup_count());
		
		return map;
	}		
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 시스템 설정파일 백업
	 */
	public Map<String, Object> getSystemConfigBackupInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getSystemConfigBackupInfo();
		
		String transfer_type = String.valueOf(result.get("transfer_type"));
		String externalBackupUse = "";
		
		if(transfer_type.equals("0")) {
			externalBackupUse = "0";
		}else {
			externalBackupUse = "1";
		}
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("useBackup", result.get("active"));
		map.put("backupPeriod", result.get("period"));
		map.put("backupMonth", result.get("month"));
		map.put("backupWeek", result.get("week"));
		map.put("backupDay", result.get("day"));
		map.put("backupTime", result.get("hour"));
		map.put("externalBackupUse", externalBackupUse);
		map.put("transferType", result.get("transfer_type"));
		map.put("backupTargetIP",result.get("target_ip"));
		map.put("backupTargetPort", result.get("target_port"));
		map.put("backupTargetID", result.get("target_id"));
		map.put("backupTargetPassword", result.get("target_pass"));
		map.put("backupTargetDir", result.get("target_dir"));
		
		return map;
	}	
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 시스템 설정파일 백업 내역
	 */
	public Map<String, Object> getSystemConfigBackupList(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		int skip = (Integer.parseInt(configurationVo.getPage()) - 1) * Integer.parseInt(configurationVo.getLimit());
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("result", systemConfigBackup.get_systemconfigbackup_list(skip, Integer.parseInt(configurationVo.getLimit())));
		map.put("total", systemConfigBackup.get_systemconfigbackup_count());
		
		return map;
	}		
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정
	 */
	public Map<String, Object> getLogDiskInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getLogDiskInfo();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("warning", result.get("@logdisk_warn"));
		map.put("delLog", result.get("@logdisk_delete"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정 > 설정 
	 */
	public Map<String, Object> setLogDiskInfo(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		return configurationMapper.setLogDiskInfo(configurationVo);
	}
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정
	 */
	public Map<String, Object> getAlarmInfo(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		if(configurationVo.getGroupid() == null) {
			configurationVo.setGroupid("0");
		}
		
		Map<String, Object> result = configurationMapper.getAlarmInfo(configurationVo);
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("deviceid", result.get("id"));
		map.put("useAlarm", result.get("use_alarm"));
		map.put("email", 	result.get("email"));
		map.put("action", 	result.get("use_inherit"));
		map.put("cpu", 		result.get("cpu"));
		map.put("memory", 	result.get("mem"));
		map.put("disk0", 	result.get("disk0"));
		map.put("disk1", 	result.get("disk1"));
		map.put("session", 	result.get("session"));
		map.put("host", 	result.get("host"));
		map.put("tunnel",	result.get("tunnel"));
		map.put("cps", 		result.get("cps"));
		map.put("rxb", 		result.get("rbytes"));
		map.put("txb", 		result.get("tbytes"));
		map.put("rxp", 		result.get("rpkts"));
		map.put("txp", 		result.get("tpkts"));
		map.put("rtt", 		result.get("rtt"));
		map.put("groupid", 	configurationVo.getGroupid());
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 장비/그릅 입계치 설정 > 장비/그룹 기본설정 / 기본 설정 
	 */
	public Map<String, Object> setAlarmInfo(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		if(configurationVo.getGroupid() == null) {
			configurationVo.setGroupid("0");
		}
		
		Map<String, Object> result = configurationMapper.getAlarmInfo(configurationVo);
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("deviceid", result.get("id"));
		map.put("useAlarm", result.get("use_alarm"));
		map.put("email", 	result.get("email"));
		map.put("action", 	result.get("use_inherit"));
		map.put("cpu", 		result.get("cpu"));
		map.put("memory", 	result.get("mem"));
		map.put("disk0", 	result.get("disk0"));
		map.put("disk1", 	result.get("disk1"));
		map.put("session", 	result.get("session"));
		map.put("host", 	result.get("host"));
		map.put("tunnel",	result.get("tunnel"));
		map.put("cps", 		result.get("cps"));
		map.put("rxb", 		result.get("rbytes"));
		map.put("txb", 		result.get("tbytes"));
		map.put("rxp", 		result.get("rpkts"));
		map.put("txp", 		result.get("tpkts"));
		map.put("rtt", 		result.get("rtt"));
		map.put("groupid", 	configurationVo.getGroupid());
		
		return map;
	}
	
    /*
     * TopNav > 시스템 설정 > 알람 > SMTP 설정
     */
	public Map<String, Object> getSmtpInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getSmtpInfo();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("enable", 			result.get("@smtp_enable"));
		map.put("enabletype", 		result.get("@smtp_enable"));
		map.put("mailServer", 		result.get("@smtp_server"));
		map.put("port", 			result.get("@smtp_port"));
		map.put("senderMail", 		result.get("@smtp_sender"));
		map.put("secure", 			result.get("@smtp_secure"));
		map.put("secureConnection", result.get("@smtp_secure"));
		map.put("user", 			result.get("@smtp_auth_user"));
		map.put("pwd",			 	result.get("@smtp_auth_pass"));
		map.put("receiverMail", 	result.get("@smtp_to"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 알람 > SMTP 전송 이벤트 
	 */
	public Map<String, Object> getSmtpEventInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getSmtpEventInfo();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("fail", 	result.get("@alarm_fail"));
		map.put("cpu", 		result.get("@alarm_cpu"));
		map.put("memory", 	result.get("@alarm_mem"));
		map.put("disk", 	result.get("@alarm_disk"));
		map.put("session", 	result.get("@alarm_session"));
		map.put("host", 	result.get("@alarm_host"));
		map.put("tunnel", 	result.get("@alarm_tunnel"));
		map.put("cps", 		result.get("@alarm_cps"));
		map.put("rtt",		result.get("@alarm_rtt"));
		map.put("rxb", 		result.get("@alarm_rx_bytes"));
		map.put("txb", 		result.get("@alarm_tx_bytes"));
		map.put("rxp", 		result.get("@alarm_rx_pkts"));
		map.put("txp", 		result.get("@alarm_tx_pkts"));
		map.put("log", 		result.get("@alarm_log_disk"));
		map.put("agent", 	result.get("@alarm_agent_integrity"));
		map.put("server", 	result.get("@alarm_server_integrity"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 기타 > SNMP > SNMP 설정  
	 */
	public Map<String, Object> getSnmpInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getSnmpInfo();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("version", 	 result.get("@snmp_version"));
		map.put("community", result.get("@snmp_community"));
		map.put("user", 	 result.get("@snmp_user"));
		map.put("level", 	 result.get("@snmp_level"));
		map.put("authprot",  result.get("@snmp_auth_prot"));
		map.put("authpass",  result.get("@snmp_auth_pass"));
		map.put("privprot",  result.get("@snmp_priv_prot"));
		map.put("privpass",  result.get("@snmp_priv_pass"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 기타 > SNMP > Trap 설정
	 */
	public Map<String, Object> getSnmpTrapInfo() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getSnmpTrapInfo();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("version", 	 result.get("@trap_version"));
		map.put("engineid",  result.get("@trap_engineid"));
		map.put("user", 	 result.get("@trap_user"));
		map.put("level", 	 result.get("@trap_level"));
		map.put("authprot",  result.get("@trap_auth_prot"));
		map.put("authpass",  result.get("@trap_auth_pass"));
		map.put("privprot",  result.get("@trap_priv_prot"));
		map.put("privpass",  result.get("@trap_priv_pass"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장애 회선 관리
	 */
	public Map<String, Object> getInterfaceConfig() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getInterfaceConfig();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("interfaceDowntime", 	 result.get("@interface_downtime"));
		map.put("interfaceUpdownCount",  result.get("@interface_updown_count"));
		map.put("interfaceUpdownHours",  result.get("@interface_updown_hours"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장애 회선 관리 > 장애 회선 임계치 설정
	 */
	public Map<String, Object> setInterfaceConfig(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		return configurationMapper.setInterfaceConfig(configurationVo);
	}
	
	/*
	 * 시스템 설정 > 기타 > 장비 자동 등록
	 */
	public Map<String, Object> getDeviceRegisterConfig() throws IOException, ParseException{
		
		Map<String, Object> result = configurationMapper.getDeviceRegisterConfig();
		
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("enable", 	result.get("@device_register_enable"));
		map.put("groupid",  result.get("@device_register_group_id"));
		map.put("active",   result.get("@device_register_active"));
		map.put("log",  	result.get("@device_register_log"));
		
		return map;
	}
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장비 자동 등록 > 장비 자동 등록 설정
	 */
	public int setDeviceRegisterConfig(ConfigurationVo configurationVo) throws IOException, ParseException{
		
		return configurationMapper.setDeviceRegisterConfig(configurationVo);
	}
}































