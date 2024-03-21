package kr.nexg.esm.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class TokenVo {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private int loginStatus;
	private boolean isFailLogin;
	private String alertWarning;
	
	public String theme;
	public String curTimeFmt;
	public String curTime;
	public String dc;
	public String locale;
	public String confMaxCnt;
	public String defMode;
	public String userAlarm;
	public String userPopupTime;
}
