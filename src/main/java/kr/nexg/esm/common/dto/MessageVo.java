package kr.nexg.esm.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import kr.nexg.esm.common.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVo {
	private StatusEnum status;
    private String success;
    private String message;
    private int totalCount;
    private String errMsg;
    private String errTitle;
    private Object entitys;

}
