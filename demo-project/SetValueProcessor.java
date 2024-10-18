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
    String summary = (String) issue.get("summary");
    System.out.println("ISSUE : " + gson.toJson(issue));
    System.out.println("SUMMARY : " + summary);
    System.out.println("==================================================");

    // Print the entire request body in JSON format
    System.out.println(gson.toJson(reqBody));

    // Parse the changelog map from the request body
    Map<String, Object> changelogMap = gson.fromJson(gson.toJson(reqBody.get("changelog")), Map.class);
    System.out.println(gson.toJson(changelogMap.get("items")));

    // Get the items list from the changelog map
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
    boolean isIncrease = summary != null && summary.startsWith("Increase");
    
    // Print the boolean results
    System.out.println("BOOLEAN status : " + statusMatch + " increase : " + isIncrease);

    // Set the headers in the exchange
    exchange.getIn().setHeader("isIncrease", false);
    exchange.getIn().setHeader("isComplete", statusMatch);
    }
    
}
