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
	 * 장비 정보 조회
	 */
	public Map<String, Object> getDeviceInfo(DevicesVo devicesVo);
	
	/*
	 * 제품 인터페이스 리스트 조회
	 */
	public List<Map<String, Object>> getDeviceInterface(DevicesVo devicesVo);
	
    /*
     * 제품 상태
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
	 * 장비 리스트 조건 검색
	 */
	public List<Map<String, Object>> searchDeviceInfoList(DevicesVo devicesVo);
	
	/*
	 * 정보 > 기본정보 > 관리번호 중복 체크
	 */	
	public int checkManagedCode(DevicesVo devicesVo);
	
	public int deviceGroupCnt(DevicesVo devicesVo);
	
	/*
	 * 그룹 추가/수정
	 */
	public Map<String, Object> setDeviceGroupInfo(DevicesVo devicesVo);
	
//	public int setFailMemo(DevicesVo devicesVo);
	
}
