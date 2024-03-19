package kr.nexg.esm.monitoring.dto;

import java.util.List;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class MonitoringVo extends CommonVo {
	private String mode;
	private String groupID;
	private String target;
	private int viewCount = 0;
	private String deviceID;
	private String uid;
	private List<String> deviceIDs;
}