package kr.nexg.esm.default1.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.nexg.esm.jwt.dto.AuthVo;



@Repository
@Mapper
public interface DefaultMapper {
	
	public AuthVo selectLogin(String login);
	
	
}
