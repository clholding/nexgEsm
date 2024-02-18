package kr.nexg.esm.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import kr.nexg.esm.common.StatusEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageVo {
	private StatusEnum status;
    private String message;
    private int totalCount;
    private Object data;
    private Object files;
    private Object prevNextData;
    private Object pinupData;

}
