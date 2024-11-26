import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("NdbGetProfileId")
public class NdbGetProfileId implements Processor {

    public void process(Exchange exchange) throws Exception {

        Gson gson = new Gson();
        
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        System.out.println("======================================================= NdbGetProfileId");
        System.out.println("respBody NdbGetProfileId : " + gson.toJson(respBody));
        String id = (String) respBody.get("id");

        Map<String, Object> dataReqMap = exchange.getProperty("dataReqMap", Map.class);
        System.out.println("dataReqMap : " + gson.toJson(dataReqMap));

    }
}