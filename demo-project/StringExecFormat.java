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

        String issuetypeName = exchange.getProperty("issuetypeName", String.class);
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

        String projectName = "";
        String username = "";
        String password = "";
        String vmName = "";

        String execArgs = "";

        if(issuetypeName.equals("Vm Creation")){

            projectName = (String) fields.get("customfield_10080");
            username = (String) fields.get("customfield_10085");
            password = (String) fields.get("customfield_10084");
            vmName = (String) fields.get("customfield_10086");
            Map<String,Object> vmType = (Map<String, Object>) fields.get("customfield_10087");
            String vmTypeValue = (String) vmType.get("value");
            Map<String,Object> bpType = (Map<String, Object>) fields.get("customfield_10088");
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
            execArgs = String.format(
                "main.yml -e {'pc_ip':'%s','bp_id':'%s','basic_auth':'%s','app_description':'%s','app_name':'%s','app_profile_reference_name':'%s','app_profile_reference_uuid':'%s','card_id':'%s','username':'%s','password':'%s','vm_name':'%s','substrate_list_uuid':'%s','card_id_uuid_variable':'%s','username_uuid_variable':'%s','password_uuid_variable':'%s','disk_0_uuid':'%s','subnet_0_uuid':'%s','subnet_1_uuid':'%s'}",
                "10.8.130.168",
                "2397fd48-ac2d-47ee-a710-e4132cce72d2",
                "YWRtaW46SGVsaW9zMTIzIQ==",
                "testing_descripttion_ansible",
                projectName,
                vmTypeValue,
                appProfileReferenceId,
                key,
                username,
                password,
                vmName,
                substrateListId,
                "243063ba-d401-4993-9121-a59fc7a5256a",
                "19ce41a2-e7ac-ae85-bfb3-96a334766569",
                "d1faee84-7de4-0b8f-b9b6-6a0cd676d682",
                "c4600e15-fd9c-4fce-b6a7-1668692457fd",
                "2dc286bb-da6f-4987-8a3f-cb7d0a694fc2",
                "c3852d6a-d439-4b33-84e9-41b483cf7e6b"
            );
            
        } else {

            projectName = (String) fields.get("customfield_10092");
            username = (String) fields.get("customfield_10090");
            password = (String) fields.get("customfield_10091");
            vmName = (String) fields.get("customfield_10093");

            Double cpuDouble = (Double) fields.get("customfield_10094");
            Integer cpu = cpuDouble != null ? cpuDouble.intValue() : null;
            Double memoryDouble = (Double) fields.get("customfield_10095");
            Integer memory = memoryDouble != null ? memoryDouble.intValue() : null;
            Double storageDouble = (Double) fields.get("customfield_10096");
            Integer storage = storageDouble != null ? storageDouble.intValue() : null;

            System.out.println("String EXEC ==================================================");
            System.out.println("USERNAME : " + username);
            System.out.println("PASSWORD : " + password);
            System.out.println("NAME : " + vmName);
            System.out.println("CPU : " + cpu);
            System.out.println("MEMORY : " + memory);
            System.out.println("STORAGE : " + storage);
            System.out.println("Key : " + key);
            System.out.println("==================================================");
            
            execArgs = String.format(
                "main.yml -e {'pc_ip':'%s','bp_id':'%s','basic_auth':'%s','app_description':'%s','app_name':'%s','app_profile_reference_name':'%s','app_profile_reference_uuid':'%s','card_id':'%s','username':'%s','password':'%s','vm_name':'%s','substrate_list_uuid':'%s','username_uuid_variable':'%s','password_uuid_variable':'%s','custom_cpu_num':%d,'custom_memory':%d,'custom_storage':%d,'card_id_uuid_variable':'%s','disk_0_uuid':'%s','subnet_0_uuid':'%s','subnet_1_uuid':'%s'} -vvvv",
                "10.8.130.168",
                "2397fd48-ac2d-47ee-a710-e4132cce72d2",
                "YWRtaW46SGVsaW9zMTIzIQ==",
                "testing_descripttion_ansible",
                projectName, // should match "project-testing-dulu-15"
                "custom",
                "d81a91f4-89df-456c-8693-c2fb8cca4865",
                key,
                username,
                password,
                vmName,
                "866028f7-7b14-ae2d-fbfd-bd0ac8510443",
                "19ce41a2-e7ac-ae85-bfb3-96a334766569",
                "d1faee84-7de4-0b8f-b9b6-6a0cd676d682",
                cpu,               // custom_cpu_num
                memory,            // custom_memory
                storage,          // custom_storage
                "243063ba-d401-4993-9121-a59fc7a5256a",
                "c4600e15-fd9c-4fce-b6a7-1668692457fd", // disk_0_uuid
                "2dc286bb-da6f-4987-8a3f-cb7d0a694fc2", // subnet_0_uuid
                "c3852d6a-d439-4b33-84e9-41b483cf7e6b"  // subnet_1_uuid
            );
        }

        exchange.getIn().setHeader("isCustom", issuetypeName.equals("Custom Vm"));
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