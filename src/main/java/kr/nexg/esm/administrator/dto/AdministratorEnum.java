package kr.nexg.esm.administrator.dto;

import lombok.Getter;

@Getter
public class AdministratorEnum {
	
	public enum mode {
		MODE_ADD(0),
		MODE_EDIT(1);
		
	    private int val;
	    
	    mode(int val) {
	        this.val = val;
	    }
	    
	    public int getVal() {
			return val;
		} 
	}
	
	public enum deviceState {
		_0("0", "정상"),
		_1("1", "장애"),
		__("", "");
	    
	    private String code;
	    private String val;
	    
	    deviceState(String code, String val) {
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
	
	public enum activeState {
		_0("0", "0"),
		_1("1", "1"),
		__("", "");
	    
	    private String code;
	    private String val;
	    
	    activeState(String code, String val) {
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
