package kr.nexg.esm.common.dto;

import lombok.Data;

@Data
public class MongoVo {
	
	/** 몽고DB Host */
	private String mongoHost;
	
	/** 몽고DB Port */
	private int mongoPort;
	
	/** 몽고DB 사용자 */
	private String mongoUsername;
	
	/** 몽고DB 패스워드 */
	private String mongoPassword;

}
