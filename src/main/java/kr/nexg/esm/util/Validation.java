package kr.nexg.esm.util;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Validation {
	
	//그룹
	public static boolean deviceAdd_gid(String rs_gid) {
		return false;
	}
	
	//그룹
	public static boolean deviceAdd_product(String rs_product_id) {
		return false;
	}
	
	//장비 이름
	public static boolean deviceAdd_name(String rs_name) {
		return false;
	}
	
	//IP
	public static boolean deviceAdd_ip(String rs_ip) {
		return false;
	}
	
	//Serial
	public static boolean deviceAdd_serial(String rs_serial) {
		return false;
	}
	
	//그룹 이름
	public static boolean groupAdd_name(String rs_name) {
		return false;
	}
	
	//SID
	public static boolean ipsDetectConfig_sid(String rs_sid) {
		return false;
	}
	
	//대상 장비
	public static boolean ipsDetectConfig_groups(String rs_groups) {
		return false;
	}
	
	//기간 (초)
	public static boolean ipsDetectConfig_time(String rs_time) {
		return false;
	}
	
	//횟수
	public static boolean ipsDetectConfig_recovery(String rs_recovery) {
		return false;
	}
	
	//사용
	public static boolean ipsDetectConfig_used(String rs_used) {
		return false;
	}
	
	//관리 장비 그룹
	public static boolean userAdd_deviceGroupIDs(String rs_deviceGroupIDs) {
		return false;
	}
	
	//모니터링 장비
	public static boolean userAdd_device_id(String rs_device_id) {
		return false;
	}
	
	//ID
	public static boolean userAdd_user_id(String rs_login) {
		return false;
	}

	//이름
	public static boolean userAdd_adminName(String rs_adminName) throws Exception {
		if(rs_adminName.isBlank()) {
			throw new Exception("이름을 입력하십시오.");
		}
		if(rs_adminName.length() < 1 || rs_adminName.length() > 33) {
			throw new Exception("이름은 1자 이상 33자 이하여야 합니다");
		}
		Pattern pattern = Pattern.compile("[ !@#$%^&*(),.?\":;{}|<=>\\-\\+`'~]");
		for(char c : rs_adminName.toCharArray()) {
			if(pattern.matcher(Character.toString(c)).find()) {
				throw new Exception("id에 특수문자가 포함되어 있습니다.");
			}
		}
		return true;
	}
	
	//이메일
	public static boolean userAdd_email(String rs_email) {
		return false;
	}
	
	//관리자 유효 기간
	public static boolean userAdd_expire_date(String rs_expire_date) {
		return false;
	}
	
	//비밀번호
	public static boolean userAdd_pwd(String rs_pwd) {
		return false;
	}
	
	//관리자 그룹
	public static boolean userAdd_adminGroupID(List<Map<String, Object>> list) throws Exception {
		if(list.size() == 0) {
			throw new Exception("관리자 그룹이 존재하지 않습니다.");
		}else {
			return true;
		}
		
	}
	
	//기본 관리모드
	public static boolean userAdd_defMode(String rs_defMode) {
		return false;
	}
	
	//세션 타임아웃(min)
	public static boolean userAdd_sessionTime(String rs_sessionTime) {
		return false;
	}
	
	//알람 소리 사용
	public static boolean userAdd_alarm(String rs_alarm) {
		return false;
	}
	
	//활성화
	public static boolean userAdd_active(String rs_active) {
		return false;
	}
	
	//이름
	public static boolean userGroupAdd_name(String rs_name) {
		return false;
	}
	
	//도메인 이름
	public static boolean DomainAdd_name(String rs_name) {
		return false;
	}
	
	//URL1
	public static boolean DomainAdd_url1(String rs_url1) {
		return false;
	}
	
	//메일 서버
	public static boolean DomainAdd_smtpServer(String rs_smtpServer) {
		return false;
	}
	
	//포트
	public static boolean DomainAdd_smtpPort(String rs_smtpPort) {
		return false;
	}
	
	//송신자 메일
	public static boolean DomainAdd_smtpSender(String rs_smtpSender) {
		return false;
	}
	
	//이름
	public static boolean imageFileServerAdd_name(String rs_name) {
		return false;
	}
	
	//주소
	public static boolean imageFileServerAdd_address(String rs_address) {
		return false;
	}
	
	//Syslog 서버 이름
	public static boolean syslogAdd_name(String rs_name) {
		return false;
	}
	
	//IP
	public static boolean syslogAdd_ip(String rs_ip) {
		return false;
	}
	
	//Port
	public static boolean syslogAdd_port(String rs_port) {
		return false;
	}
}
