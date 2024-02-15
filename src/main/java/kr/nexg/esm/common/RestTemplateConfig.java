package kr.nexg.esm.common;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestTemplateConfig {
	
	@Autowired
	private RestTemplateErrorHandler restTemplateErrorHandler;
	
	@Bean
	public RestTemplate restTemplate() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		HttpClient httpClient = HttpClientBuilder.create()
				.setMaxConnTotal(100)
				.setMaxConnPerRoute(5)
				.build();
		factory.setHttpClient(httpClient);
		factory.setReadTimeout(1000 * 50);
		factory.setConnectTimeout(1000 * 30);
		RestTemplate restTemplate = new RestTemplate(factory);
		restTemplate.setErrorHandler(restTemplateErrorHandler);
		return restTemplate;
	}

}
