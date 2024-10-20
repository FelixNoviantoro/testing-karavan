import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("IncreaseCpuProcessor")
public class IncreaseCpuProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        Gson gson = new Gson();

        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        Map<String, Object> issue = (Map<String, Object>) respBody.get("issue");
        Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
        String key = (String) issue.get("key");
        String description = (String) fields.get("description");

        // Regular expressions to extract the values
        String vmNameRegex = "VM Name = ([\\w-]+)";
        String playbookIdRegex = "Playbook ID : ([\\w-]+)";
        String alertUuidRegex = "Allert UUID : ([\\w-]+)";
        String alertNameRegex = "Allert Name : ([\\w-]+)";

        // Compile the patterns
        Pattern vmNamePattern = Pattern.compile(vmNameRegex);
        Pattern playbookIdPattern = Pattern.compile(playbookIdRegex);
        Pattern alertUuidPattern = Pattern.compile(alertUuidRegex);
        Pattern alertNamePattern = Pattern.compile(alertNameRegex);

        // Match the patterns
        Matcher vmNameMatcher = vmNamePattern.matcher(description);
        Matcher playbookIdMatcher = playbookIdPattern.matcher(description);
        Matcher alertUuidMatcher = alertUuidPattern.matcher(description);
        Matcher alertNameMatcher = alertNamePattern.matcher(description);

        // Extract and print the values if found
        if (vmNameMatcher.find()) {
            System.out.println("VM Name: " + vmNameMatcher.group(1));
        }
        if (playbookIdMatcher.find()) {
            System.out.println("Playbook ID: " + playbookIdMatcher.group(1));
        }
        if (alertUuidMatcher.find()) {
            System.out.println("Alert UUID: " + alertUuidMatcher.group(1));
        }
        if (alertNameMatcher.find()) {
            System.out.println("Alert Name: " + alertNameMatcher.group(1));
        }

        // Create the main map
        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put("trigger_type", "incoming_webhook_trigger");

        // Create the list for trigger_instance_list
        List<Map<String, String>> triggerInstanceList = new ArrayList<>();

        // Create the first trigger instance map
        Map<String, String> triggerInstance = new HashMap<>();
        triggerInstance.put("webhook_id", "29b738ff-f048-449b-50c5-c34d1f61119f");
        triggerInstance.put("string1", key);
        triggerInstance.put("entity1", "{\"type\":\"vm\",\"name\":\""+ vmNameMatcher.group(1) +"\",\"uuid\":\""+ playbookIdMatcher.group(1) +"\"}");
        triggerInstance.put("entity2", "{\"type\":\"alert\",\"name\":\"bot-calm-VM-CPU-Usage\",\"uuid\":\""+ alertUuidMatcher.group(1) +"\"}");

        // Add the trigger instance to the list
        triggerInstanceList.add(triggerInstance);

        // Add the list to the main map
        mainMap.put("trigger_instance_list", triggerInstanceList);

        // Set the result in the exchange
        exchange.getIn().setBody(gson.toJson(mainMap));
    }
}
