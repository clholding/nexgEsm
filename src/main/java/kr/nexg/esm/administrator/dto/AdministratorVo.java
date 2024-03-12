package kr.nexg.esm.administrator.dto;

import java.util.List;

import kr.nexg.esm.common.dto.CommonVo;
import lombok.Data;

@Data
public class AdministratorVo extends CommonVo {
    private String adminID;			//관리자 ID
    private String adminName;		//관리자 이름
    private String desc;			//설명
    private String adminGroupID; 	//관리그룹ID
    private String deviceGroupIDStr;	//관리장비 그룹 ID 리스트 문자열 ex) "1,2,3"
    private List<String> deviceGroupIDs;	//관리장비 그룹 ID리스트
    private String defMode; 	//기본관리모드
    private String sessionTimeout; 	//관리자 세션 타임아웃 값
    private String alarm; 	//관리자 알람 소리 사용 유무
    private String popupTime; 	//관리자 알람창 자동 닫힘 시간
    private String login; 	//관리자 로그인id
    private String pwd; 	//관리자 로그인pw
    private String newPwd; 	//변경할 관리자 로그인pw
    private String active; 	//활성화여부
    private String allow_ip1; 	//allow_ip1
    private String allow_ip2; 	//allow_ip2
    private String device_state; 	//장비현황
    private String recent_fail_device; 	//최근장애장비
    private String resource_top5; 	//리소스현황 TOP5
    private String week_log_stats; 	//주간 로그 발생 통계
    private String week_fail_state; 	//장애 로그 현황
    private String device_sort; 	//장비 정렬 방식
    private String device_order; 	//장비 정렬 순서
    private String adminExpireDate; 	//계정 유효 기간
    private String adminLifetime; 	//계정 활성화 시간
    private String passwordExpireCycle; 	//비밀번호 갱신 주기
    private String monitorDeviceID; 	//모니터링 장비 ID
    private String pwdInit; 	//비밀번호 초기화 여부
    private String adminEmail; 	//이메일
    private List<String> adminIDs;
    private List<String> adminGroupIDs;
}