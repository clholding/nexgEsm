package kr.nexg.esm.nexgesm.mariadb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.nexg.esm.nexgesm.mariadb.mapper.ConfigMapper;

@Component
public class Config {
	
	@Autowired
	ConfigMapper configMapper;
	
	
	@Component
	public class Config1 {
		public int set_apply_status(boolean val) {
			return configMapper.set_apply_status(val);
			
		}
	}
	
	@Component
	public class ConfigBackup {
		public int get_configbackup_count() {
			return configMapper.get_configbackup_count();
			
		}
		
		public List<Map<String, Object>> get_configbackup_list(int skip, int limit) {
			return configMapper.get_configbackup_list(skip, limit);
			
		}
	}
	
	@Component
	public class SystemConfigBackup {
		public int get_systemconfigbackup_count() {
			return configMapper.get_systemconfigbackup_count();
			
		}
		
		public List<Map<String, Object>> get_systemconfigbackup_list(int skip, int limit) {
			return configMapper.get_systemconfigbackup_list(skip, limit);
			
		}
	}
}
