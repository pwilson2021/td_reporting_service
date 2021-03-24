package turntabl.io.reporting_service.listener;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import turntabl.io.reporting_service.model.ReportingModel;


import java.io.IOException;

public class ReportingListener implements MessageListener {
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 싱글 쿼테이션 허용
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            Object msg = mapper.readValue(message.getBody(), Object.class);
            System.out.println("report received:  "+ msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
