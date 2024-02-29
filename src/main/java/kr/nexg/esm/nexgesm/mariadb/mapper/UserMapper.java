package kr.nexg.esm.nexgesm.mariadb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {
	
	public List<Map<String, Object>> get_user_info(String uid);
	
	public List<Map<String, Object>> get_user_group_info(String ugid);
	
}
