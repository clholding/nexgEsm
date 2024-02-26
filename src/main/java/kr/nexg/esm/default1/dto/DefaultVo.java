package kr.nexg.esm.default1.dto;

import lombok.Data;

@Data
public class DefaultVo
{
    /** 검색조건 */
    private String searchCondition = "";
    
    /** 검색키워드 */
    private String searchKeyword = "";
    
    /** 현재페이지 */
    private int pageIndex = 1;

    /** 페이지갯수 */
    private int pageUnit = 1;

    /** 페이지사이즈 */
    private int pageSize = 10;

    /** firstIndex */
    private int firstIndex = 1;

    /** lastIndex */
    private int lastIndex = 1;

    /** recordCountPerPage */
    private int recordCountPerPage = 10;
    
    /** row number */
    private int rnum = 0;

    /** total count */
    private int totalCount = 0;
    
}
