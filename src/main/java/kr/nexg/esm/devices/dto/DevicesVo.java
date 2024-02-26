package kr.nexg.esm.devices.dto;


import lombok.Data;

@Data
public class DevicesVo
{
    private String id;
    private String type;
    private String auth;
    private String deviceIDs;
    private int mode;
}
