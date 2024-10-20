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
@BindToRegistry("FetchIssueProcessor")
public class FetchIssueProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();
        String respBody = exchange.getIn().getBody(String.class);
        System.out.println("========================= FETCHED");
        System.out.println(respBody);
        System.out.println("========================= fromjson");
        Map<String, Object> pbReq = exchange.getProperty("pbReq", Map.class);

        // Regex pattern for description
        Pattern descriptionPattern = Pattern.compile("description=([^,}]+)");
        Matcher descriptionMatcher = descriptionPattern.matcher(respBody);

        String description = "";
        String cardId = "";

        // Extract description
        if (descriptionMatcher.find()) {
            description = descriptionMatcher.group(1).trim();
            System.out.println("Description: " + description);
        } else {
            System.out.println("Description not found.");
        }

        Pattern keyPattern = Pattern.compile("key=([^,}]+)");
        Matcher keyMatcher = keyPattern.matcher(respBody);

        // Extract description
        if (keyMatcher.find()) {
            cardId = keyMatcher.group(1).trim();
            System.out.println("Key : " + cardId);
        } else {
            System.out.println("Description not found.");
        }

        Map<String, String> reqBody = new HashMap<>();
        reqBody.put("description", description);
        reqBody.put("cardId", cardId);

        // String multiLineString = """
        //     description
        //     VM Created address:
        //     Name : vmName
        //     IP : vmIpAddress
        //     CPU : cpu
        //     memory : memory 
        //     """;

        String multiLineString = "Vm Name : "+ pbReq.get("vmName") +",\n" +
                         "IP : "+ pbReq.get("vmIpAddress") +",\n" +
                         "Username : "+ pbReq.get("username") +",\n" +
                         "Password : "+ pbReq.get("password") +",\n" +
                         "CPU : " + pbReq.get("cpu") +",\n" +
                         "Memory : " + pbReq.get("memory") +",\n";
        exchange.setProperty("dataFetch", reqBody);

        // exchange.getIn().setBody(respBody);

        exchange.getIn().setHeader("IssueKey", cardId);
        // exchange.getIn().setHeader("IssueSummary", data.get("cardId"));
        // exchange.getIn().setBody(description + " IP : " + pbReq.get("vmIpAddress") + " CPU : " + pbReq.get("cpu") + " Memory : " + pbReq.get("memory"));
        exchange.getIn().setBody(multiLineString);
    }
}
