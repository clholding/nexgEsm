package kr.nexg.esm.jwt.dto;

import java.util.List;
import java.util.Map;

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
	
	private String theme;
	private String curTimeFmt;
	private String curTime;
	private String dc;
	private String locale;
	private Map<String, Integer> confMaxCnt;
	private String defMode;
	private String userAlarm;
	private String userPopupTime;
	private int fileCount;
}
