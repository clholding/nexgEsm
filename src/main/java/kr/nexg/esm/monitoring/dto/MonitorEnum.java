package kr.nexg.esm.monitoring.dto;

import lombok.Getter;

@Getter
public class MonitorEnum {
	
	public enum monitorType {
		CPU("CPU", "1"),
		LOAD1("LOAD1", "2"),
		LOAD5("LOAD5", "3"),
		LOAD15("LOAD15", "4"),
		MEM("MEM", "5"),
		SWAP("SWAP", "6"),
		DISK0("DISK0", "7"),
		DISK1("DISK1", "8"),
		SESSION("SESSION", "9"),
		TUNNEL("TUNNEL", "10"),
		VPNRULE("VPNRULE", "11"),
		RTT("RTT", "12"),
		TRAFFIC("TRAFFIC", "13"),
		PACKET("PACKET", "14"),
		RX_BYTES("RX_BYTES", "13"),
		RX_PACKET("RX_PACKET", "14"),
		TX_BYTES("TX_BYTES", "15"),
		TX_PACKET("TX_PACKET", "16"),
		HOST("HOST", "17"),
		LTE("LTE", "18"),
		CPS("CPS", "19");
	    
	    private String code;
	    private String val;
	    
	    monitorType(String code, String val) {
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
