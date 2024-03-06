package kr.nexg.esm.global.dto;

import lombok.Getter;

@Getter
public class GlobalEnum {
	
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
