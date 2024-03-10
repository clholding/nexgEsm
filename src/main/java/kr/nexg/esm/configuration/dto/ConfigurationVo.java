package kr.nexg.esm.configuration.dto;

import lombok.Data;

@Data
public class ConfigurationVo {

    private String skip;
    private String limit;
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

}
