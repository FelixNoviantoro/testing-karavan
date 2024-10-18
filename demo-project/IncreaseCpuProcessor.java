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
        // exchange.getIn().setBody("Hello World");
        // // Assuming the JSON object is in the body of the exchange
        // String jsonBody = exchange.getIn().getBody(String.class);
        
        // // Parse the JSON body
        // JsonObject jsonObject = JsonParser.parseString(jsonBody).getAsJsonObject();
        
        // // Get the description from the issue fields
        // String description = jsonObject.getAsJsonObject("issue")
        //                                .getAsJsonPrimitive("description").getAsString();

        // // Regular expression pattern to match the required values
        // String regex = "VM Name = (\\S+),.*Playbook ID : (\\S+),.*Allert UUID : (\\S+),.*Allert Name : (.+?) for";
        // Pattern pattern = Pattern.compile(regex);
        // Matcher matcher = pattern.matcher(description);

        // String vmName = matcher.group(1); // vm-0-241016-214032
        // String playbookId = matcher.group(2); // e45037a0-c7ef-4c19-868b-4bab3b7832da
        // String alertUUID = matcher.group(3); // f6e59c2f-bf14-44e5-8392-1050cc9924f4
        // String alertName = matcher.group(4); // bot-calm -VM-CPU-Usage

        // if (matcher.find()) {
        //     vmName = matcher.group(1); // vm-0-241016-214032
        //     playbookId = matcher.group(2); // e45037a0-c7ef-4c19-868b-4bab3b7832da
        //     alertUUID = matcher.group(3); // f6e59c2f-bf14-44e5-8392-1050cc9924f4
        //     alertName = matcher.group(4); // bot-calm -VM-CPU-Usage

        //     // Output the extracted values
        //     System.out.println("VM Name: " + vmName);
        //     System.out.println("Playbook ID: " + playbookId);
        //     System.out.println("Alert UUID: " + alertUUID);
        //     System.out.println("Alert Name: " + alertName);
        // } else {
        //     System.out.println("No matches found.");
        // }

        // Create the main map
        Map<String, Object> mainMap = new HashMap<>();
        mainMap.put("trigger_type", "incoming_webhook_trigger");

        // Create the list for trigger_instance_list
        List<Map<String, String>> triggerInstanceList = new ArrayList<>();

        // Create the first trigger instance map
        Map<String, String> triggerInstance = new HashMap<>();
        triggerInstance.put("webhook_id", "4d0b564d-e831-4f68-a7e6-e2c0d658c745");
        triggerInstance.put("string1", "PEG-45");
        triggerInstance.put("entity1", "{\"type\":\"vm\",\"name\":\"vm-0-241016-214032\",\"uuid\":\"e45037a0-c7ef-4c19-868b-4bab3b7832da\"}");
        triggerInstance.put("entity2", "{\"type\":\"alert\",\"name\":\"bot-calm-VM-CPU-Usage\",\"uuid\":\"851e2f4f-65b1-4507-8033-3853b3c1c89e\"}");

        // Add the trigger instance to the list
        triggerInstanceList.add(triggerInstance);

        // Add the list to the main map
        mainMap.put("trigger_instance_list", triggerInstanceList);

        // Set the result in the exchange
        exchange.getIn().setBody(gson.toJson(mainMap));
    }
}
