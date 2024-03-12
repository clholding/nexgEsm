package kr.nexg.esm.configuration.dto;

import lombok.Data;

@Data
public class ConfigurationVo {

    private String skip;
    private String limit;
    private String page;
    private String devid;
    private String enable;
    private String groupid;
    private String active;
    private String log;
    private String delLog;
    private String warning;
    private String interfaceDowntime;
    private String interfaceUpdownCount;
    private String interfaceUpdownHours;
    private String action;
    private String deviceid;
    private String cpu;
    private String memory;
    private String disk0;
    private String disk1;
    private String session;
    private String host;
    private String tunnel;
    private String cps;
    private String rtt;
    private String rxb;
    private String rxbType;
    private String txb;
    private String txbType;
    private String rxp;
    private String rxpType;
    private String txp;
    private String txpType;

}
