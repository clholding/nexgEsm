package kr.nexg.esm.nexgesm.mariadb.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface ConfigMapper {
	
	public int set_apply_status(boolean val);
	
	
}
