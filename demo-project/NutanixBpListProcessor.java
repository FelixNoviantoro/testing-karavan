import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("NutanixBpListProcessor")
public class NutanixBpListProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> reqBody = new HashMap<>();

        reqBody.put("length", 2);
        reqBody.put("offset", 0);
        reqBody.put("sort_attribute", "ASCENDING");
        reqBody.put("sort_order", "ASCENDING");

        exchange.getIn().setBody(gson.toJson(reqBody));

    }
}
