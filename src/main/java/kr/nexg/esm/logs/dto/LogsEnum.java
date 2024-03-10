package kr.nexg.esm.logs.dto;

import lombok.Getter;

@Getter
public class LogsEnum {
	
	public enum protocols {
		_1("1", "ICMP"),
		_6("6", "TCP"),
		_17("17", "UDP"),
		_999("999", "OTHERS");
	    
	    private String code;
	    private String val;
	    
	    protocols(String code, String val) {
	        this.code = code;
	        this.val = val;
	    }

		public String getCode() {
			return code;
		}

		public String getVal() {
			return val;
		}
	}
	
	public enum nexg_act {
		_1("1", "Auth Success"),
		_2("2", "Auth fail"),
		_3("3", "Need to change password"),
		_4("4", "Anonymous Success"),
		_5("5", "Base DN Search Success"),
		_6("6", "Delete User IP"),
		_7("7", "Delete User ID"),
		_8("8", "Flush User"),
		_9("9", "Flush anonymous User"),
		_10("10", "pw chage"),
		_11("11", "pw chage fail"),
		_0("0", "others"),
		_999("999", "others");
	    
	    private String code;
	    private String val;
	    
	    nexg_act(String code, String val) {
	        this.code = code;
	        this.val = val;
	    }

		public String getCode() {
			return code;
		}

		public String getVal() {
			return val;
		}
	}
	
	public enum syslogLevel {
		_0("0", "Emergency"),
		_1("1", "Alert"),
		_2("2", "Critical"),
		_3("3", "Error"),
		_4("4", "Warning"),
		_5("5", "Notice"),
		_6("6", "Informational"),
		_7("7", "Debug");
	    
	    private String code;
	    private String val;
	    
	    syslogLevel(String code, String val) {
	        this.code = code;
	        this.val = val;
	    }

		public String getCode() {
			return code;
		}

		public String getVal() {
			return val;
		}
	}
	
	public enum logTypes {
		esm("0"),
		session("1"),
		ipsec("2"),
		ips("3"),
		l7f("4"),
		http("5"),
		auth("6"),
		system("7"),
		management("8"),
		login("9"),
		config("10"),
		version("11"),
		Reboot("12"),
		Fail("13"),
		Alarm("14"),
		EsmAudit("15"),
		ftp("16"),
		Resource("17"),
		Command("18");
		
	    private String val;
	    
	    logTypes(String val) {
	        this.val = val;
	    }
	    
	    public String getVal() {
			return val;
		} 
	}
	
	public enum logTargetMap {
		esm("0"),
		session("session"),
		ipsec("ipsec"),
		ips("ips"),
		l7f("l7f"),
		http("http"),
		auth("auth_user"),
		system("7"),
		management("mgmt"),
		login("mgmt"),
		config("mgmt"),
		version("11"),
		Reboot("12"),
		Fail("13"),
		Alarm("14"),
		EsmAudit("15"),
		ftp("16"),
		Resource("17"),
		Command("18");
		
	    private String val;
	    
	    logTargetMap(String val) {
	        this.val = val;
	    }
	    
	    public String getVal() {
			return val;
		} 
	}
	
	public enum logDownStatus {
		_1("1", "정상"),
		_2("2", "장애"),
		_3("3", "장애");
	    
	    private String code;
	    private String val;
	    
	    logDownStatus(String code, String val) {
	        this.code = code;
	        this.val = val;
	    }

		public String getCode() {
			return code;
		}

		public String getVal() {
			return val;
		}
	    
	}
	
}
