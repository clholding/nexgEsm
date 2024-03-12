package kr.nexg.esm.nexgesm.mariadb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ConfigMapper {
	
	public int set_apply_status(boolean val);
	
	public int get_configbackup_count();
	
	List<Map<String, Object>> get_configbackup_list(int skip, int limit);
	
	public int get_systemconfigbackup_count();
	
	List<Map<String, Object>> get_systemconfigbackup_list(int skip, int limit);
	
}
