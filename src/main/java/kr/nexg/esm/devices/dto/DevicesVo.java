package kr.nexg.esm.devices.dto;


import java.util.List;

import lombok.Data;

@Data
public class DevicesVo
{
    private String sessionId;
    private String id;
    private String isActive;
    private String type;
    private String gp;
    private String code1;
    private String code2;
    private String memo1;
    private String memo2;
    private String auth;
    private List<String> deviceIDs;
    private List<String> groupIDs;
    private String rsDeviceIDs;
    private String hostname;
    private String uptime;
    private String pgroupID;
    private String deviceID;
    private String memo;
    private String failID;
    private String groupID;
    private String productID;
    private String dname;
    private String gname;
    private String name;
    private String ip;
    private String serial;
    private String os;
    private String agent;
    private String desc;
    private String company;
    private String phone1;
    private String fax;
    private String zip;
    private String address;
    private String customer;
    private String phone2;
    private String email;
    private String page;
    private String limit;
    private String viewCount;
    private String overwriteid;
    private String overDId;
    private String active;
    private String log;
    private String alarm;
    private String mid;
    private String mpass;
    private String gid;
    private String snmpUseInherit;
    private String snmpVersion;
    private String snmpCommunity;
    private String snmpLevel;
    private String snmpUser;
    private String snmpAuthprot;
    private String snmpAuthpass;
    private String snmpPrivprot;
    private String snmpPrivpass;
    private String mode;
    private String dn;
    private String manageNumberUses;
}
