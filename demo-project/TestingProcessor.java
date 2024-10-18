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

        // // Root Map
        // Map<String, Object> rootMap = new HashMap<>();
        
        // // Inner map for "spec"
        // Map<String, Object> specMap = new HashMap<>();
        // specMap.put("app_description", "simple dulu");
        // specMap.put("app_name", "testing-dihari-jumat");

        // // Inner map for "app_profile_reference"
        // Map<String, Object> appProfileRefMap = new HashMap<>();
        // appProfileRefMap.put("kind", "app_profile");
        // appProfileRefMap.put("name", "Default");
        // appProfileRefMap.put("uuid", "23bbca1d-4141-889a-36c0-68d64ab674be");

        // // Put "app_profile_reference" into "spec"
        // specMap.put("app_profile_reference", appProfileRefMap);

        // // Put "spec" into root
        // rootMap.put("spec", specMap);






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

        // Extract VM Name
        String vmName = extractValue(description, vmNamePattern);
        System.out.println("VM Name: " + vmName);
        System.out.println("==========================");
        // // Extract Project Name
        String projectName = extractValue(description, projectNamePattern);
        System.out.println("Project Name: " + projectName);
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
        appProfileReference.put("name", "Default");
        appProfileReference.put("uuid", "23bbca1d-4141-889a-36c0-68d64ab674be");
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
        variable.put("uuid", "a0619650-9d3d-ba0c-3bbb-8c76deb5724e");
        variableList.add(variable);
        runtimeEditables.put("variable_list", variableList);

        // Create the "substrate_list"
        List<Map<String, Object>> substrateList = new ArrayList<>();
        Map<String, Object> substrate = new HashMap<>();
        substrate.put("description", "");
        substrate.put("name", "VM1");
        Map<String, Object> substrateValue = new HashMap<>();
        Map<String, String> specValue = new HashMap<>();
        specValue.put("name", vmName);
        substrateValue.put("spec", specValue);
        substrate.put("value", substrateValue);
        substrate.put("type", "AHV_VM");
        substrate.put("uuid", "11e4696c-d3ad-dcba-a279-63792720e6b3");
        substrateList.add(substrate);
        runtimeEditables.put("substrate_list", substrateList);

        // Add "runtime_editables" to specMap
        specMap.put("runtime_editables", runtimeEditables);

        // Finally, add "spec" to the main map
        map.put("spec", specMap);

        // Example of how to access the data
        System.out.println(map);
        String json = mapToJson(map);
        System.out.println(json);

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
