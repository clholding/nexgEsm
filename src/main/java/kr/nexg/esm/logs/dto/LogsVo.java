package kr.nexg.esm.logs.dto;

import java.util.List;
import java.util.Map;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class LogsVo extends CommonVo {
	private String mode;
	private String target;
	private int viewCount;
	private String startDate;
	private String endDate;
	private String dn;
	private String gn;
	private String fip;
	private Map<String, String> dictionaries;
	private List<String> deviceIDs;
}