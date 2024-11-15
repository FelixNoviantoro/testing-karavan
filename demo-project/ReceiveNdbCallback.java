import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;

@Configuration
@BindToRegistry("ReceiveNdbCallback")
public class ReceiveNdbCallback implements Processor {

    public void process(Exchange exchange) throws Exception {

        Map<String, Object> headers = exchange.getIn().getHeaders();
        for (Map.Entry<String, Object> entry : headers.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ", Value: " + entry.getValue());
        }

        exchange.setProperty("ndbCallback", headers);
        exchange.setProperty("cardId", headers.get("cardId"));
        exchange.getIn().setHeader("IssueKey", headers.get("cardId"));

        // try {
        //     //  Block of code to try
        //     String respBody = exchange.getIn().getBody(String.class);
        //     System.out.println("String NDB callback body : " + respBody);
        // }
        // catch(Exception e) {
        //     //  Block of code to handle errors
        //     System.out.println("Exception Jenisnya : " + e.getClass());
        //     System.out.println("Exception Messagenya : " + e.getMessage());

        //     Map<String, Object> mainMap = new HashMap<>();
        //     mainMap.put("cardId", "PEG-131");

        //     exchange.getIn().setBody(mainMap);

        // }

        
    }
}