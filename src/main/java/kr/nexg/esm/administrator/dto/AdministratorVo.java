package kr.nexg.esm.administrator.dto;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class AdministratorVo extends CommonVo {
	private String sessionId;
    private String adminID;
}