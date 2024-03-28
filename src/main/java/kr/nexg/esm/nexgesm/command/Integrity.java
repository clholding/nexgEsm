package kr.nexg.esm.nexgesm.command;

import org.springframework.beans.factory.annotation.Value;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import kr.nexg.esm.nexgesm.emsg.Emsg_pb2;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Integrity {

    @Value("${spring.rabbitmq.host}")
    private String host;
    
    @Value("${spring.rabbitmq.port}")
    private int port;
    
	public Integrity() {
//		MQ mq = new MQ("esm.command", "esm.esmd", host, port);
	}
	
    public void check_integrity() throws Exception {
    	
    	int type = Emsg_pb2.CommandType.CT_CHECK.getNumber();
    	int subType = Emsg_pb2.CommandSubType.CST_INTEGRITY.getNumber();
    	
        Emsg_pb2.EsmCommand esmCommand = Emsg_pb2.EsmCommand.newBuilder()
        		.setSender(Emsg_pb2.Sender.S_WEB)
        		.setType(type)
        		.setSubtype(subType)
        		.build();
        
		// 객체 직렬화
		byte[] byteArray = esmCommand.toByteArray();
		
		Emsg_pb2.EsmCommand deserialized = Emsg_pb2.EsmCommand.parseFrom(byteArray);
		
//        mq.setRoutingKey("esm.esmd");
//        mq.publish(deserialized);
    }

    public void check_agent_integrity(int deviceId, String ip) throws Exception {

    	int type = Emsg_pb2.CommandType.CT_CHECK.getNumber();
    	int subType = Emsg_pb2.CommandSubType.CST_AGENTINTEGRITY.getNumber();
    	
        Emsg_pb2.EsmCommand esmCommand = Emsg_pb2.EsmCommand.newBuilder()
        		.setSender(Emsg_pb2.Sender.S_WEB)
        		.setType(type)
        		.setSubtype(subType)
        		.setOpt1(deviceId)
        		.setIp(ip)
        		.build();
        
		// 객체 직렬화
		byte[] byteArray = esmCommand.toByteArray();
		
		Emsg_pb2.EsmCommand deserialized = Emsg_pb2.EsmCommand.parseFrom(byteArray);
		
//        mq.setRoutingKey("esm.esmd");
//        mq.publish(deserialized);
    }
}
