package kr.nexg.esm.devices.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.devices.dto.DevicesVo;


@Repository
@Mapper
public interface DevicesMapper {
	
	public List<Map<String, Object>> getDeviceGroupByLogin(DevicesVo devicesVo);
	
	public List<Map<String, Object>> getDeviceListByLogin(DevicesVo devicesVo);
	
    /*
     * 장비관리 > 장비추가리스트
     */
	public List<Map<String, Object>> deviceCandidate(DevicesVo devicesVo);
	
    /*
     * 제품정보 리스트
     */
	public List<Map<String, Object>> getDeviceGroup();
	
	public List<Map<String, Object>> getDeviceList();
	
	public List<Map<String, Object>> getAlarmDeviceGroupOfLogin(DevicesVo devicesVo);
	
	public List<Map<String, Object>> getAlarmDeviceListOfLogin(DevicesVo devicesVo);
	
    /*
     * 장비 리스트 정보 조회
     */
	public List<Map<String, Object>> getDeviceInfoList(DevicesVo devicesVo);
	
	public Map<String, Object> getDeviceGroupInfo(DevicesVo devicesVo);
	
    /*
     * DeviceFinder > 개별정보 > 정보 > 기본정보
     * 메인 > SideBar > 토플로지 > 타사 장비 추가 > 기본정보 
     */
	public Map<String, Object> getDeviceInfo(DevicesVo devicesVo);
	
    /*
     * DeviceFinder > 개별정보 > 정보 > 인터페이스
     */
	public List<Map<String, Object>> getDeviceInterface(DevicesVo devicesVo);
	
    /*
     * DeviceFinder > 개별정보 > 장비미리보기
     */  
	public List<Map<String, Object>> getDeviceStatus(DevicesVo devicesVo);
	
	/*
	 * 제품정보 리스트
	 */
	public List<Map<String, Object>> getProductList(DevicesVo devicesVo);
	
	/*
	 * 제품실패 정보
	 */
	public List<Map<String, Object>> getDeviceFailInfo(DevicesVo devicesVo);
	
	/*
	 * 제품실패 정보 리스트
	 */	
	public List<Map<String, Object>> getDeviceFailList(DevicesVo devicesVo);
	
	public String getGroupToDeviceListByLogin(DevicesVo devicesVo);
	
	/*
	 * 메인화면 > SideBar > 자산이력
	 */
	public List<Map<String, Object>> searchDeviceInfoList(DevicesVo devicesVo);
	
	/*
	 * DeviceFinder > 개별정보 > 정보 > 기본정보 > 관리번호 중복체크
	 */
	public int checkManagedCode(DevicesVo devicesVo);
	
	public int deviceGroupCnt(DevicesVo devicesVo);
	
    /*
     * DeviceFinder > 그룹정보 > 정보 > 기본정보 > 저장
     */
	public Map<String, Object> setDeviceGroupInfo(DevicesVo devicesVo);
	
//	public int setFailMemo(DevicesVo devicesVo);
	
}
