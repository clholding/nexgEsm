package kr.nexg.esm.administrator.dto;

import lombok.Data;

@Data
public class AdministratorRVo {
	private String id;
	private String name;
	private String login;
	private String groupId;
	private String userGroupName;
	private String dgName;
	private String deviceState;
	private String recentFailDevice;
	private String resourceTop5;
	private String weekLogStats;
	private String weekFailState;
	private String dgId;
	private String desc;
	private String defMode;
	private String sessionTime;
	private String alarm;
	private String active;
	private String email;      
	private String loginExpireDate;      
	private String loginActiveLifetime;
	private String pwdExpireCycle;
	private String deviceId;
	private String deviceSort;
	private String deviceOrder;
	private String popupTime;
	private String allowIp1;
	private String allowIp2;
}