package kr.nexg.esm.global.dto;

import java.util.List;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class GlobalVo extends CommonVo {
    private String mode;			//모드
    private List<String> deviceIDs;	
}