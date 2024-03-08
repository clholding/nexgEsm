package kr.nexg.esm.nexgesm.mariadb;

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
}
