package kr.nexg.esm.nexgesm.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@Getter
public class EsmConfig {
	
	@Value("${ssl.engine}")
	private String ssl_engine;
	
	@Value("${ssl.cert}")
	private String ssl_cert;
	
	@Value("${ssl.key}")
	private String ssl_key;
	
	@Value("${agent.port}")
	private int agent_port;
	
	@Value("${spring.datasource.username}")
	private String dbuser;
	
	@Value("${spring.datasource.password}")
	private String dbpass;
	
	@Value("${name}")
	private String product_name;
	
	@Value("${version}")
	private String product_version;
	
	@Value("${pkg}")
	private String product_pkg;
	
	@Value("${hash}")
	private String product_hash;
	
	@Value("${server.version}")
	private String product_server;
	
	@Value("${agent.version}")
	private String product_agent;
	
	
}
