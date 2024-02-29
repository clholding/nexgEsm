package kr.nexg.esm.nexgesm.mariadb;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kr.nexg.esm.nexgesm.mariadb.mapper.UserMapper;

@Component
public class User {
	
	@Autowired
	UserMapper userMapper;
	
	@Component
	public class User1 {
		public List<Map<String, Object>> get_user_info(String uid) {
			return userMapper.get_user_info(uid);
			
		}
	}
	
	@Component
	public class UserGroup {
		public List<Map<String, Object>> get_user_group_info(String ugid) {
			return userMapper.get_user_group_info(ugid);
			
		}
	}
}
