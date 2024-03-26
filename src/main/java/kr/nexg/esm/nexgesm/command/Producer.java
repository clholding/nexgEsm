package kr.nexg.esm.nexgesm.command;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Producer {
	
	private final RabbitTemplate rabbitTemplage;
	
	public void sendMessage() {

		rabbitTemplage.convertAndSend("esm.elogd", "hello.key");
//		rabbitTemplage.convertAndSend("esm.ecollectd", "hello.key");
//		rabbitTemplage.convertAndSend("esm.etrapd", "hello.key");
		
//		rabbitTemplage.convertAndSend("hello.exchange", "hello.key");
	}
}
