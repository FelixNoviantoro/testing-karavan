import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("SetPropertyProcessor")
public class SetPropertyProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        System.out.println("================================================================ 1234");
        Map<String, Object> reqBody = exchange.getIn().getBody(Map.class);
        String cardId = (String) reqBody.get("cardId");
        String dataIpAddress = (String) reqBody.get("dataIpAddress");

        System.out.println("Card Id : " + cardId);
        System.out.println("Body Properties set : " + reqBody);

        exchange.setProperty("pbReq", reqBody);
        exchange.setProperty("cardId", cardId);
        exchange.setProperty("dataIpAddress", dataIpAddress);
    }
}
