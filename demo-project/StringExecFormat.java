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
        String description = (String) fields.get("description");

        System.out.println("ISSUE : " + issue);
        System.out.println("==========================");
        System.out.println("Key : " + key);
        System.out.println("==========================");
        System.out.println("description : " + description);
        System.out.println("==========================");

        // Regular expression patterns to match VM Name and Project Name
        String vmNamePattern = "VM Name = ([^,]+)";
        String projectNamePattern = "Project Name = ([^,]+)";
        String bpTemplatePattern = "Bp template = ([^,]+)";
        String sizePattern = "Size = ([^,]+)";
        String usernamePattern = "Username = ([^,]+)";
        String passwordPattern = "Password = ([^,]+)";

        String appProfileReferenceId = "";
        String substrateListId = "";
        
        // Extract VM Name
        String vmName = extractValue(description, vmNamePattern);
        System.out.println("VM Name: " + vmName);
        System.out.println("==========================");
        // // Extract Project Name
        String projectName = extractValue(description, projectNamePattern);
        System.out.println("Project Name: " + projectName);
        System.out.println("==========================");
        // Extract Bp template
        String bpTemplate = extractValue(description, bpTemplatePattern);
        System.out.println("Bp Template: " + bpTemplate);
        System.out.println("==========================");
        // Extract Size
        String size = extractValue(description, sizePattern);
        System.out.println("Size: " + size);
        System.out.println("==========================");
        // Extract Bp template
        String usernameValue = extractValue(description, usernamePattern);
        System.out.println("Username: " + usernameValue);
        System.out.println("==========================");
        // Extract Size
        String passwordValue = extractValue(description, passwordPattern);
        System.out.println("Password: " + passwordValue);
        System.out.println("==========================");

        if("S".equals(size)){
            appProfileReferenceId = "1d6ba9ce-d1de-4047-bed0-d2a2dfcdad7c";
            substrateListId = "5d0f52b7-75ea-4c98-8c7e-459949823c1c";
        } else if("L".equals(size)) {
            appProfileReferenceId = "72471f36-56ad-4cfe-8b5d-b6b7baff99be";
            substrateListId = "ca541a62-871a-4041-89f4-7204f707386b";
        } else {
            appProfileReferenceId = "aa4ef9d9-4851-4108-a7ba-cb99ba512f99";
            substrateListId = "7a2e1756-533e-4962-ae3b-7ef1c1e6ff11";
        }

        // Build the ansible-playbook args string dynamically
        String execArgs = String.format(
            "launch_bluerprint.yml -e {'pc_ip':'%s','bp_id':'%s','basic_auth':'%s','app_description':'%s','app_name':'%s','app_profile_reference_name':'%s','app_profile_reference_uuid':'%s','card_id':'%s','username':'%s','password':'%s','vm_name':'%s','substrate_list_uuid':'%s'}",
            "10.8.130.168",
            "9145e6c8-a25d-482a-a6f4-52cffa7bf744",
            "YWRtaW46bnV0NG5peFBAc3N3MHJk",
            "testing descripttion",
            projectName,
            size,
            appProfileReferenceId,
            key,
            usernameValue,
            passwordValue,
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
}