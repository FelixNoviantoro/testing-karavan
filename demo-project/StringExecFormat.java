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
@BindToRegistry("StringExecFormat")
public class StringExecFormat implements Processor {

    public void process(Exchange exchange) throws Exception {
        // Assuming the body is a Map or a JSON-like structure
        Gson gson = new Gson();

        Map<String, Object> respBody = exchange.getIn().getBody(Map.class);
        Map<String, Object> issue = (Map<String, Object>) respBody.get("issue");
        Map<String, Object> fields = (Map<String, Object>) issue.get("fields");
        String key = (String) issue.get("key");
        // // String description = (String) fields.get("description");

        // System.out.println("ISSUE : " + issue);
        // System.out.println("==========================");
        // System.out.println("Key : " + key);
        // System.out.println("==========================");
        // System.out.println("description : " + description);
        // System.out.println("==========================");

        // // Regular expression patterns to match VM Name and Project Name
        // String vmNamePattern = "VM Name = ([^,]+)";
        // String projectNamePattern = "Project Name = ([^,]+)";
        // String bpTemplatePattern = "Bp template = ([^,]+)";
        // String sizePattern = "Size = ([^,]+)";
        // String usernamePattern = "Username = ([^,]+)";
        // String passwordPattern = "Password = ([^,]+)";

        String appProfileReferenceId = "";
        String substrateListId = "";
        
        // // Extract VM Name
        // String vmName = extractValue(description, vmNamePattern);
        // System.out.println("VM Name: " + vmName);
        // System.out.println("==========================");
        // // // Extract Project Name
        // String projectName = extractValue(description, projectNamePattern);
        // System.out.println("Project Name: " + projectName);
        // System.out.println("==========================");
        // // Extract Bp template
        // String bpTemplate = extractValue(description, bpTemplatePattern);
        // System.out.println("Bp Template: " + bpTemplate);
        // System.out.println("==========================");
        // // Extract Size
        // String size = extractValue(description, sizePattern);
        // System.out.println("Size: " + size);
        // System.out.println("==========================");
        // // Extract Bp template
        // String usernameValue = extractValue(description, usernamePattern);
        // System.out.println("Username: " + usernameValue);
        // System.out.println("==========================");
        // // Extract Size
        // String passwordValue = extractValue(description, passwordPattern);
        // System.out.println("Password: " + passwordValue);
        // System.out.println("==========================");

        String projectName = (String) fields.get("customfield_10080");
        String username = (String) fields.get("customfield_10085");
        String password = (String) fields.get("customfield_10084");
        String vmName = (String) fields.get("customfield_10086");
        Map<String,Object> vmType = (Map<String, Object>) fields.get("customfield_10087");
        String vmTypeValue = (String) vmType.get("value");
        Map<String,Object> bpType = (Map<String, Object>) fields.get("customfield_10079");
        String bpTypeValue = (String) bpType.get("value");

        System.out.println("String EXEC ==================================================");
        System.out.println("USERNAME : " + username);
        System.out.println("PASSWORD : " + password);
        System.out.println("NAME : " + vmName);
        System.out.println("VM TYPE : " + vmTypeValue);
        System.out.println("BP TYPE : " + bpTypeValue);
        System.out.println("Key : " + key);
        System.out.println("==================================================");

        if("S".equals(vmTypeValue)){
            appProfileReferenceId = "9ca75263-8f5c-4123-97b0-1950a9dc5a80";
            substrateListId = "543d66fc-ece0-4493-9bea-1f84b279a48d";
        } else if("L".equals(vmTypeValue)) {
            appProfileReferenceId = "7e3debbd-2e53-4557-ba99-832e2771251f";
            substrateListId = "ec1f15b5-4f5e-4c5c-9ca6-b36ce33fdcd4";
        } else {
            appProfileReferenceId = "f09dbf22-2128-4232-923c-d6de10de58c8";
            substrateListId = "729eba71-92ac-4583-b84f-617b3d26b7ed";
        }

        // Build the ansible-playbook args string dynamically
        String execArgs = String.format(
            "launch_bluerprint.yml -e {'pc_ip':'%s','bp_id':'%s','basic_auth':'%s','app_description':'%s','app_name':'%s','app_profile_reference_name':'%s','app_profile_reference_uuid':'%s','card_id':'%s','username':'%s','password':'%s','vm_name':'%s','substrate_list_uuid':'%s'}",
            "10.8.130.168",
            "2397fd48-ac2d-47ee-a710-e4132cce72d2",
            "YWRtaW46bnV0NG5peFBAc3N3MHJk",
            "testing_descripttion_ansible",
            projectName,
            vmTypeValue,
            appProfileReferenceId,
            key,
            username,
            password,
            vmName,
            substrateListId
        );

        // Set the dynamically built args in the header
        exchange.getIn().setHeader("execArgs", execArgs);
    }

    private static String extractValue(String description, String pattern) {
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(description);
        if (matcher.find()) {
            return matcher.group(1).trim(); // Return the matched value
        }
        return null; // Return null if not found
    }

    public static String escapeForExec(String value) {
    // Wrap the value in double quotes if it contains spaces
    if (value.contains(" ")) {
        return String.format("\"%s\"", value);
    }
    return value;
}
}