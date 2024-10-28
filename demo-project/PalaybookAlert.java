import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.google.gson.Gson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("PalaybookAlert")
public class PalaybookAlert implements Processor {

    public void process(Exchange exchange) throws Exception {
        // exchange.getIn().setBody("Hello World");
        System.out.println("============================ PLAYBOOK ALERT");

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

        // Build the ansible-playbook args string dynamically
        String execArgs = String.format(
            "webhook-alert.yml -e {'pc_ip':'%s','basic_auth':'%s','webhook_uuid':'%s','card_id':'%s','vm_name':'%s','vm_uuid':'%s','alert_uuid':'%s'}",
            "10.8.130.168",
            "YWRtaW46bnV0NG5peFBAc3N3MHJk",
            "29b738ff-f048-449b-50c5-c34d1f61119f",
            key,
            vmNameMatcher.group(1),
            playbookIdMatcher.group(1),
            alertUuidMatcher.group(1)
        );

        // Set the dynamically built args in the header
        exchange.getIn().setHeader("execArgs", execArgs);
    }
}