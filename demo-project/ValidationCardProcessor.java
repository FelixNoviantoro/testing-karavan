import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("ValidationCardProcessor")
public class ValidationCardProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        String appName = exchange.getProperty("appName", String.class);
        boolean isExist = false;

        List<Map<String, Object>> entities = (List<Map<String, Object>>) respBody.get("entities");

        for (Map<String, Object> entity : entities) {
            Map<String, Object> status = (Map<String, Object>) entity.get("status");
            String name = (String) status.get("name");
            if (name.equals(appName)){
                isExist = true;
                break;
            }
        }

        exchange.getIn().setHeader("isAppnameExist", isExist);
    }
}