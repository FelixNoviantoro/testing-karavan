import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.regex.*;

@Configuration
@BindToRegistry("NdbProfileResponseProcessor")
public class NdbProfileResponseProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        String cardId = exchange.getProperty("issueKey", String.class);

        String respBody = exchange.getIn().getBody(String.class);
        String regex = "ok=(\\d+)";
        
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(respBody);
        
        if (matcher.find()) {
            String okValue = matcher.group(1); // Capturing group 1
            System.out.println("=============================================================================");
            System.out.println("OK Value: " + okValue + " card id: " + cardId);
            System.out.println("=============================================================================");

            exchange.getIn().setHeader("status", okValue);
            exchange.getIn().setHeader("cardId", cardId);
            exchange.getIn().setHeader("IssueKey", cardId);
        } else {
            System.out.println("No match found");
        }
        
    }
}