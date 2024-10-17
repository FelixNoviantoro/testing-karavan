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

        System.out.println("Testing Processor end");
        System.out.println("==========================");

        // Root Map
        Map<String, Object> rootMap = new HashMap<>();
        
        // Inner map for "spec"
        Map<String, Object> specMap = new HashMap<>();
        specMap.put("app_description", "DECRIPTION TERBARU ");
        specMap.put("app_name", "testing-dulu-aja-kali-ya");

        // Inner map for "app_profile_reference"
        Map<String, Object> appProfileRefMap = new HashMap<>();
        appProfileRefMap.put("kind", "app_profile");
        appProfileRefMap.put("name", "Default");
        appProfileRefMap.put("uuid", "23bbca1d-4141-889a-36c0-68d64ab674be");

        // Put "app_profile_reference" into "spec"
        specMap.put("app_profile_reference", appProfileRefMap);

        // Put "spec" into root
        rootMap.put("spec", specMap);

        // Example of how to access the data
        System.out.println(rootMap);
        String json = mapToJson(rootMap);
        System.out.println(json);

        exchange.getIn().setBody(json);
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
}
