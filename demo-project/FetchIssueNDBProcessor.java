import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("FetchIssueNDBProcessor")
public class FetchIssueNDBProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        String respBody = exchange.getIn().getBody(String.class);
        System.out.println("========================= FETCHED");
        // System.out.println(respBody);
        System.out.println("========================= fromjson");

        String regex = "name=VM Name.*?value=([^\\s,}]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(respBody);
        
        if (matcher.find()) {
            String vmName = matcher.group(1); // Extracted value
            System.out.println("NDB Instance Name: " + vmName);

            exchange.setProperty("vmName", vmName);

        } else {
            System.out.println("NDB Instance Name not found.");
        }
    }
}