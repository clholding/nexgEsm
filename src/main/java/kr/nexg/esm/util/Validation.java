package kr.nexg.esm.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	
	//그룹
	public static boolean deviceAdd_gid(int rs_gid) throws Exception {
		if(rs_gid == 0) {
			throw new Exception("그룹명이 존재하지 않습니다.");
		}
		
		return true;
	}
	
	//그룹
	public static boolean deviceAdd_product(int rs_product_id) throws Exception {
		if(rs_product_id == 0) {
			throw new Exception("제품명이 존재하지 않습니다.");
		}
		
		return true;
	}
	
	//장비 이름
	public static boolean deviceAdd_name(String rs_name) throws Exception {
        if (rs_name == null || rs_name.isEmpty()) {
            throw new Exception("장비 이름을 입력하십시오.");
        } else if (rs_name.length() < 3 || rs_name.length() > 128) {
            throw new Exception("장비 이름은 최소 3자리 이상 255자리 이하여야 합니다.");
        } else {
            return true;
        }
	}
	
	//IP
	public static boolean deviceAdd_ip(String rs_ip) throws Exception {
        String pattern = "^(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
        if (rs_ip == null || rs_ip.isEmpty()) {
            throw new Exception("장비 IP를 입력하십시오.");
        } else if (!Pattern.matches(pattern, rs_ip)) {
            throw new Exception("유효한 IP 형식이 아닙니다.");
        } else {
            return true;
        }
	}
	
	
    private static boolean containsSpecialCharacter(String input) {
        String specialCharacters = "!@#$%^&*()_-+=<>?/[]{},.:;";
        for (char ch : input.toCharArray()) {
            if (specialCharacters.indexOf(ch) != -1) {
                return true;
            }
        }
        return false;
    }

    private static boolean containsLowerCase(String input) {
        for (char ch : input.toCharArray()) {
            if (Character.isLowerCase(ch)) {
                return true;
            }
        }
        return false;
    }
    
	//Serial
	public static boolean deviceAdd_serial(String rs_serial) throws Exception {
       if (rs_serial == null || rs_serial.isEmpty()) {
            throw new Exception("장비 serial을 입력하십시오.");
        } else if (containsSpecialCharacter(rs_serial)) {
            throw new Exception("Serial 값에 특수문자가 포함되어 있습니다.");
        } else if (containsLowerCase(rs_serial)) {
            throw new Exception("Serial 값에 소문자가 포함되어 있습니다.");
        } else if (rs_serial.length() > 14) {
            throw new Exception("Serial 값의 최대 길이는 14자 입니다.");
        } else {
            return true;
        }
	}
	
	//그룹 이름
	public static boolean groupAdd_name(String rs_name) throws Exception {
        if (rs_name == null || rs_name.isEmpty()) {
            throw new Exception("그룹 이름을 입력하십시오.");
        } else if (rs_name.length() < 3 || rs_name.length() > 128) {
            throw new Exception("그룹 이름은 최소 3자리 이상 128자리 이하여야 합니다.");
        } else {
            return true;
        }
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
	public static boolean userAdd_deviceGroupIDs(List<Map<String, Object>> list) throws Exception {
		if(list.size() == 0) {
			throw new Exception("관리 장비 그룹이 존재하지 않습니다.");
		}
		
		return true;
	}
	
	//모니터링 장비
	public static boolean userAdd_device_id(List<Map<String, Object>> list) throws Exception {
		if(list.size() == 0) {
			throw new Exception("모니터링 장비가 존재하지 않습니다.");
		}
		
		return true;
	}
	
	//ID
	public static boolean userAdd_user_id(String rs_login) throws Exception {
		
		if(rs_login.isBlank()) {
			throw new Exception("id를 입력하십시오.");
		}
		if(rs_login.length() < 3 || rs_login.length() > 31) {
			throw new Exception("id는 3자 이상 31자 이하여야 합니다.");
		}
		String regex = "^[a-zA-Z0-9]*$";
		if(!rs_login.matches(regex)) {
			throw new Exception("id는 영문자와 숫자만 포함해야 합니다.");
		}
		Pattern pattern = Pattern.compile("[ !@#$%^&*(),.?\":;{}|<=>\\-\\+`'~]");
		for(char c : rs_login.toCharArray()) {
			if(pattern.matcher(Character.toString(c)).find()) {
				throw new Exception("id에 특수문자가 포함되어 있습니다.");
			}
		}
		
		return true;
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
	public static boolean userAdd_email(String rs_email) throws Exception {
		
		if(rs_email.isBlank()) {
			throw new Exception("이메일을 입력하십시오.");
		}
		if(rs_email.length() > 255) {
			throw new Exception("이메일은 255자 이하여야 합니다.");
		}
		
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";   
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(rs_email);
		if(!matcher.matches()) {
			throw new Exception("유효한 이메일 주소가 아닙니다.");
		}
		
		return true;
	}
	
	//관리자 유효 기간
	public static boolean userAdd_expire_date(String rs_expire_date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String max_date_str = "2038-01-19 12:14:07";
		Date max_date;
		Date expire_date;
		if(rs_expire_date.isBlank()) {
			throw new Exception("관리자 유효 기간을 입력하십시오.");
		}
		try {
			max_date = sdf.parse(max_date_str);
			expire_date = sdf.parse(rs_expire_date);
		} catch (Exception e) {
			throw new Exception("날짜 및 시간 형식이 올바르지 않습니다.");
		}
		
		if(expire_date.after(max_date)) {
			throw new Exception("입력값이 범위를 초과하였습니다.");
		}
		if(expire_date.before(new Date())) {
			throw new Exception("유효 기간이 현재 시각보다 이전입니다.");
		}
		
		return true;
	}
	
	//비밀번호
	public static boolean userAdd_pwd(String rs_pwd) {
		return false;
	}
	
	//관리자 그룹
	public static boolean userAdd_adminGroupID(List<Map<String, Object>> list) throws Exception {
		if(list.size() == 0) {
			throw new Exception("관리자 그룹이 존재하지 않습니다.");
		}
		
		return true;
		
	}
	
	//기본 관리모드
	public static boolean userAdd_defMode(String rs_defMode) throws Exception {
		if(rs_defMode.isBlank()) {
			throw new Exception("기본 관리모드를 선택하십시오.");
		}
		
		return true;
	}
	
	//세션 타임아웃(min)
	public static boolean userAdd_sessionTime(String rs_sessionTime) throws Exception {
		
		if(rs_sessionTime.isBlank()) {
			throw new Exception("세션 타임아웃(min)을 입력하십시오.");
		}
		int session_time = Integer.parseInt(rs_sessionTime);
		if(session_time < 5 || session_time > 60) {
			throw new Exception("세션 타임아웃 값은 5에서 60 사이여야 합니다.");
		}
		
		return true;
	}
	
	//알람 소리 사용
	public static boolean userAdd_alarm(String rs_alarm) throws Exception {
		
		if(!"0".equals(rs_alarm) && !"1".equals(rs_alarm)) {
			throw new Exception("올바른 알람 소리 사용 여부를 선택해주세요.");
		}
		
		return true;
	}
	
	//활성화
	public static boolean userAdd_active(String rs_active) throws Exception {
		if(!"0".equals(rs_active) && !"1".equals(rs_active)) {
			throw new Exception("올바른 활성화 여부를 선택해주세요.");
		}
		
		return true;
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
