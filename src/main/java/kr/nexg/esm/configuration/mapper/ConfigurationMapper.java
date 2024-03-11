package kr.nexg.esm.configuration.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.configuration.dto.ConfigurationVo;

@Repository
@Mapper
public interface ConfigurationMapper {
	
	
    /*
     * TopNav > 시스템 설정 > 시스템 > 시간 동기화
     */
	public Map<String, Object> getNtpInfo();
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 서버 무결성 상태
	 */
	public List<Map<String, Object>> getIntegrityInfoList(ConfigurationVo configurationVo);
	
	/*
	 * TopNav > 시스템 설정 > 시스템 > 에이전트 무결성 상태
	 */
	public List<Map<String, Object>> getDeviceIntegrityInfo();
	
    /*
     * TopNav > 시스템 설정 > 시스템 > 장비 설정파일 백업
     */
	public Map<String, Object> getConfigBackupInfo();
	
	/*
	 * TopNav > 스템 설정 > 시스템 > 시스템 설정파일 백업
	 */
	public Map<String, Object> getSystemConfigBackupInfo();
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정
	 */
	public Map<String, Object> getLogDiskInfo();
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 디스크 임계치 설정 > 설정 
	 */
	public Map<String, Object> setLogDiskInfo(ConfigurationVo configurationVo);
	
	/*
	 * TopNav > 시스템 설정 > 알람 > 장비/그룹 임계치 설정 > 장비/그룹 기본 설정
	 */
	public Map<String, Object> getAlarmInfo(ConfigurationVo configurationVo);
	
    /*
     * TopNav > 시스템 설정 > 알람 > SMTP 설정
     */
	public Map<String, Object> getSmtpInfo();
	
	/*
	 * TopNav > 시스템 설정 > 알람 > SMTP 전송 이벤트 
	 */
	public Map<String, Object> getSmtpEventInfo();
	
	/*
	 * TopNav > 시스템 설정 > 기타 > SNMP > SNMP 설정  
	 */
	public Map<String, Object> getSnmpInfo();
	
	/*
	 * TopNav > 시스템 설정 > 기타 > SNMP > Trap 설정
	 */
	public Map<String, Object> getSnmpTrapInfo();
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장애 회선 관리
	 */
	public Map<String, Object> getInterfaceConfig();
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장애 회선 관리 > 장애 회선 임계치 설정
	 */
	public Map<String, Object> setInterfaceConfig(ConfigurationVo configurationVo);
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장비 자동 등록
	 */
	public Map<String, Object> getDeviceRegisterConfig();
	
	/*
	 * TopNav > 시스템 설정 > 기타 > 장비 자동 등록 > 장비 자동 등록 설정
	 */
	public int setDeviceRegisterConfig(ConfigurationVo configurationVo);
	
}
