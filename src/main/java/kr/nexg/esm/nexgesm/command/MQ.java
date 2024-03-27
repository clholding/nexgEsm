package kr.nexg.esm.nexgesm.command;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQ {

    @Value("${spring.rabbitmq.host}")
    private String host;
    
    @Value("${spring.rabbitmq.username}")
    private String username;
    
    @Value("${spring.rabbitmq.password}")
    private String password;
    
    @Value("${spring.rabbitmq.port}")
    private int port;
    
    @Bean
    Queue queue() {
        return new Queue("esm.esmd", false);
    }
    
    @Bean
    DirectExchange directExchange() {
        return new DirectExchange("esm.command");
    }
    
    @Bean
    Binding binding(DirectExchange directExchange, Queue queue) {
        return BindingBuilder.bind(queue).to(directExchange).with("esm.esmd");
    }
    
    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }
    
    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
    
    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
 
//    private String exchange;
//    private String routingKey;
//    private Connection connection;
//    private Channel channel;
//
//    public MQ(String exchange, String routingKey, String host, int port) throws IOException, TimeoutException {
//        this.exchange = exchange;
//        this.routingKey = routingKey;
//
//        ConnectionFactory factory = new ConnectionFactory();
//        factory.setHost(host);
//        factory.setPort(port);
//        factory.setUsername("esm");
//        factory.setPassword("esm");
//
//        connection = factory.newConnection();
//        channel = connection.createChannel();
//    }
//
//    public void close() throws IOException, TimeoutException {
//        channel.close();
//        connection.close();
//    }
//
//    public void publish(String message) throws IOException {
//        channel.basicPublish(exchange, routingKey, null, message.getBytes());
//    }
//
//    public void setExchange(String exchange) {
//        this.exchange = exchange;
//    }
//
//    public void setRoutingKey(String routingKey) {
//        this.routingKey = routingKey;
//    }
//
//    // Example usage
//    public static void main(String[] args) {
//        String exchange = "esm.command";
//        String routingKey = "esm.esmd";
//        String host = "3.35.233.14"; // Change this to your RabbitMQ host
//        int port = 5672; // Default RabbitMQ port
//
//        try {
//            MQ mq = new MQ(exchange, routingKey, host, port);
//            
//            mq.setRoutingKey("esm.elogd");
//            mq.publish("amq.gen-Ql5kI2NspkwhXh6nCq7kdg");
//            
////            mq.setRoutingKey("esm.ecollectd");
////            mq.publish("amq.gen-dUdL54K8IYNp2_fhyS75HQ");
////            
////            mq.setRoutingKey("esm.etrapd");
////            mq.publish("amq.gen-rtX9d1RUSSiEHvPB1hVRoQ");
//            
//            mq.close();
//            System.out.println("111111111111");
//        } catch (IOException | TimeoutException e) {
//            e.printStackTrace();
//        }
//    }
}
