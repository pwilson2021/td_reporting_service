package turntabl.io.reporting_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import turntabl.io.reporting_service.listener.ReportingListener;

@Configuration
public class ReportingServiceConfig {
    @Bean
    public JedisConnectionFactory connectionFactory(){
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(6379);

        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object > template(){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory());
        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return template;
    }

    @Bean
    public ChannelTopic topic() {return new ChannelTopic("reporting-service");}

//    @Bean
//    Publisher redisPublisher(){
//        return new Publisher(template(),topic());
//    }

    @Bean
    public MessageListenerAdapter messageListenerAdapter(){
//        pass the receiver class to the message listener adapter
        return new MessageListenerAdapter(new ReportingListener());
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.addMessageListener(messageListenerAdapter(), topic());

        return container;
    }
}
