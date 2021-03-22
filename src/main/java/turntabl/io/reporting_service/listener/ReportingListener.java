package turntabl.io.reporting_service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import turntabl.io.reporting_service.model.ReportingModel;


import java.io.IOException;

public class ReportingListener implements MessageListener{
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ReportingModel msg = mapper.readValue(message.getBody(), ReportingModel.class);
            System.out.println("report received:  "+ msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
