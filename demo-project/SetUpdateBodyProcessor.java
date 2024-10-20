import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;

@Configuration
@BindToRegistry("SetUpdateBodyProcessor")
public class SetUpdateBodyProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        Map<String, Object> data = exchange.getProperty("dataFetch", Map.class);
        System.out.println("========================= Set update body");
        System.out.println(data);
        System.out.println(data.get("cardId"));
        System.out.println("========================= Set update body End");
        exchange.getIn().setHeader("IssueTransitionId", 5);
        exchange.getIn().setHeader("IssueKey", data.get("cardId"));
        // exchange.getIn().setHeader("tambahan dari processor " + data.get("description"));
    }
}
