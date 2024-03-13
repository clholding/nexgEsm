package kr.nexg.esm.logs.dto;

import java.util.List;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class LogsVo extends CommonVo {
	private String mode;
	private List<String> deviceIDs;
}