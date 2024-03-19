package kr.nexg.esm.default1.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.jwt.dto.AuthVo;



@Repository
@Mapper
public interface DefaultMapper {
	
	public AuthVo selectLogin(String login);
	
	public int updateFailCount(AuthVo authVo);
	
	public int updateLoginTime(AuthVo authVo);
	
	public Map<String, Object> getUserURLs(AuthVo authVo);
}
