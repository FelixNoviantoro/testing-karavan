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
        Map<String, Object> reqBody = exchange.getIn().getBody(Map.class);
        exchange.setProperty("pbReq", reqBody);
        System.out.println("palybook req : " + reqBody);
    }
}
