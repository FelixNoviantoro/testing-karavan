import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Collections;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("SetValueProcessor")
public class SetValueProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
    System.out.println("========================= Set Value");
    Gson gson = new Gson();

    // Get the request body as a Map
    Map<String, Object> reqBody = exchange.getIn().getBody(Map.class);

    // Parse the issue map from the request body
    Map<String, Object> issue = (Map<String, Object>) reqBody.get("issue");

    // Get the 'summary' value as a String
    Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
    Map<String, Object> status = (Map<String, Object>) fields.get("status");
    Map<String, Object> issuetype = (Map<String, Object>) fields.get("issuetype");
    String summary = (String) fields.get("summary");
    String projectName = (String) fields.get("customfield_10080");
    String key = (String) issue.get("key");
    String issuetypeName = (String) issuetype.get("name");

    System.out.println("ISSUE : " + gson.toJson(issue));
    System.out.println("SUMMARY : " + summary);
    System.out.println("issuetypeName : " + issuetypeName);

    // customfileds
    // String username = (String) fields.get("customfield_10085");
    // String password = (String) fields.get("customfield_10084");
    // String vmName = (String) fields.get("customfield_10086");
    // Map<String,Object> vmType = (Map<String, Object>) fields.get("customfield_10087");
    // String vmTypeValue = (String) vmType.get("value");
    // Map<String,Object> bpType = (Map<String, Object>) fields.get("customfield_10079");
    // String bpTypeValue = (String) bpType.get("value");

    // System.out.println("USERNAME : " + username);
    // System.out.println("PASSWORD : " + password);
    // System.out.println("NAME : " + vmName);
    // System.out.println("VM TYPE : " + vmTypeValue);
    // System.out.println("BP TYPE : " + bpTypeValue);
    System.out.println("==================================================");
    // Print the entire request body in JSON format
    System.out.println(gson.toJson(reqBody));

    if (reqBody.containsKey("changelog")){

        // Parse the changelog map from the request body
        Map<String, Object> changelogMap = (Map<String, Object>) reqBody.get("changelog");

        List<Map<String, Object>> itemList = (List<Map<String, Object>>) changelogMap.get("items");
        String fromString = "";
        String toString = "";

        // Iterate through the items to find the status field
        for (Map<String, Object> item : itemList) {
            if ("status".equals(item.get("field"))) {
                // Retrieve fromString and toString values
                fromString = (String) item.get("fromString");
                toString = (String) item.get("toString");

                // Print the status change
                System.out.println("From Status: " + fromString);
                System.out.println("To Status: " + toString);
            }
        }

        // Check if the status matches "On Process Create VM" -> "Done" and if the summary starts with "Increase"
        boolean statusMatch = "On Process Create VM".equals(toString);
        boolean statusToDo = "To Do".equals(status.get("name"));
        boolean isIncrease = summary != null && summary.startsWith("Increase");

        if(statusToDo){
            exchange.setProperty("appName", projectName);
            exchange.setProperty("issueKey", key);
        }
        
        // Print the boolean results
        System.out.println("BOOLEAN status : " + statusMatch + " increase : " + isIncrease + " is todo : " + statusToDo + " status : " + status.get("name"));

        // Set the headers in the exchange
        exchange.getIn().setHeader("isIncrease", isIncrease);
        exchange.getIn().setHeader("isComplete", statusMatch);
        exchange.getIn().setHeader("isToDo", statusToDo);

        exchange.setProperty("issuetypeName", issuetypeName);

    } else {

        exchange.getIn().setHeader("isIncrease", false);
        exchange.getIn().setHeader("isComplete", false);
        exchange.getIn().setHeader("isToDo", false);

    }

    

    }
    
}
