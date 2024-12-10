import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("NdbReceiveRequest")
public class NdbReceiveRequest implements Processor {

    public void process(Exchange exchange) throws Exception {
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        System.out.println("========================= NdbReceiveRequest");
        System.out.println(respBody);
        String id = (String) respBody.get("id");

        exchange.setProperty("idCompute", id);
        exchange.getIn().setHeader("status", '3');
        
    }
}