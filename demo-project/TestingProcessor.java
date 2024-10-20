import org.apache.camel.BindToRegistry;
import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import org.apache.camel.component.netty.http.NettyHttpComponent;

import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import org.apache.camel.component.http.HttpComponent;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import com.google.gson.Gson;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
@BindToRegistry("TestingProcessor")
public class TestingProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {

        System.out.println("==========================");
        System.out.println("Testing Processor");

        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        // Trust all client certificates
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        // Trust all server certificates
                    }
                }
        };

        TrustManagersParameters trustManagersParameters = new TrustManagersParameters();
        trustManagersParameters.setTrustManager(trustAllCerts[0]);

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setSecureSocketProtocol("TLS");
        sslContextParameters.setTrustManagers(trustManagersParameters);

        HttpComponent httpComponent = exchange.getContext().getComponent("https", HttpComponent.class);
        httpComponent.setSslContextParameters(sslContextParameters);
        NettyHttpComponent nettyHttpComponent = exchange.getContext().getComponent("netty-http", NettyHttpComponent.class);
        nettyHttpComponent.getConfiguration().setSslContextParameters(sslContextParameters);

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

        // Create the main structure
        Map<String, Object> map = new HashMap<>();

        // Create the "spec" map
        Map<String, Object> specMap = new HashMap<>();
        specMap.put("app_description", "VM description");
        specMap.put("app_name", projectName);

        // Create the "app_profile_reference" map
        Map<String, String> appProfileReference = new HashMap<>();
        appProfileReference.put("kind", "app_profile");
        if ("L".equals(size)){
            appProfileReference.put("name", "L");
            appProfileReference.put("uuid", "7e3debbd-2e53-4557-ba99-832e2771251f");
        } else if("XL".equals(size)){
            appProfileReference.put("name", "XL");
            appProfileReference.put("uuid", "f09dbf22-2128-4232-923c-d6de10de58c8");
        } else{
            appProfileReference.put("name", "S");
            appProfileReference.put("uuid", "9ca75263-8f5c-4123-97b0-1950a9dc5a80");
        }
        specMap.put("app_profile_reference", appProfileReference);

        // Create the "runtime_editables" map
        Map<String, Object> runtimeEditables = new HashMap<>();

        // Create the "variable_list"
        List<Map<String, Object>> variableList = new ArrayList<>();
        Map<String, Object> variable = new HashMap<>();
        variable.put("description", "");
        variable.put("name", "card_id");
        Map<String, String> valueMap = new HashMap<>();
        valueMap.put("value", key);
        variable.put("value", valueMap);
        variable.put("type", "LOCAL");
        variable.put("uuid", "243063ba-d401-4993-9121-a59fc7a5256a");
        variableList.add(variable);

        Map<String, Object> username = new HashMap<>();
        // Map<String, String> valueUsername = new HashMap<>();
        // valueUsername.put("value", )
        username.put("description", "");
        username.put("name", "username");
        username.put("value", Collections.singletonMap("value", usernameValue));
        username.put("type", "LOCAL");
        username.put("uuid", "19ce41a2-e7ac-ae85-bfb3-96a334766569");
        variableList.add(username);

        Map<String, Object> password = new HashMap<>();
        password.put("description", "");
        password.put("name", "password");
        password.put("value", Collections.singletonMap("value", passwordValue));
        password.put("type", "LOCAL");
        password.put("uuid", "d1faee84-7de4-0b8f-b9b6-6a0cd676d682");
        variableList.add(password);

        runtimeEditables.put("variable_list", variableList);

        // Create the "substrate_list"
        List<Map<String, Object>> substrateList = new ArrayList<>();
        Map<String, Object> substrate = new HashMap<>();
        substrate.put("description", "");
        
        if ("L".equals(size)){
            substrate.put("name", "VM1_L");
        } else if("XL".equals(size)){
            substrate.put("name", "VM1_XL");
        } else{
            substrate.put("name", "VM1_S");
        }

        Map<String, Object> substrateValue = new HashMap<>();
        Map<String, String> specValue = new HashMap<>();
        specValue.put("name", vmName);
        substrateValue.put("spec", specValue);
        substrate.put("value", substrateValue);
        substrate.put("type", "AHV_VM");

        if ("L".equals(size)){
            substrate.put("uuid", "ec1f15b5-4f5e-4c5c-9ca6-b36ce33fdcd4");
        } else if("XL".equals(size)){
            substrate.put("uuid", "729eba71-92ac-4583-b84f-617b3d26b7ed");
        } else{
            substrate.put("uuid", "543d66fc-ece0-4493-9bea-1f84b279a48d");
        }
        
        substrateList.add(substrate);
        runtimeEditables.put("substrate_list", substrateList);

        // Add "runtime_editables" to specMap
        specMap.put("runtime_editables", runtimeEditables);

        // Finally, add "spec" to the main map
        map.put("spec", specMap);

        // Example of how to access the data
        System.out.println("======================================================================= Hasil waktu create");
        System.out.println(map);
        System.out.println("=======================================================================");
        System.out.println(gson.toJson(map));
        System.out.println("======================================================================= Hasil waktu create END");

        exchange.getIn().setBody(gson.toJson(map));
        exchange.getIn().setHeader("CamelHttpMethod", "POST");
    }

    public static String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");

        for (Map.Entry<String, Object> entry : map.entrySet()) {
            json.append("\"").append(entry.getKey()).append("\":");

            if (entry.getValue() instanceof Map) {
                json.append(mapToJson((Map<String, Object>) entry.getValue()));
            } else {
                json.append("\"").append(entry.getValue()).append("\"");
            }
            json.append(",");
        }

        // Remove trailing comma and close the JSON
        if (json.length() > 1) {
            json.setLength(json.length() - 1);
        }
        json.append("}");
        
        return json.toString();
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
